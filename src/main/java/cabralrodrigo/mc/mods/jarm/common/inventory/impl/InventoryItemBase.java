package cabralrodrigo.mc.mods.jarm.common.inventory.impl;

import cabralrodrigo.mc.mods.jarm.common.item.ItemJarmBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryItemBase implements IInventory {

    private final ItemStack stack;
    private ItemStack[] inventory;
    private String customName;
    private String originalName;
    private Item itemContainer;
    private int rowCount;
    private int columnCount;

    public InventoryItemBase(ItemJarmBase itemContainer, ItemStack stackContainer, int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.stack = stackContainer;
        this.itemContainer = itemContainer;
        this.originalName = itemContainer.getName();
        this.inventory = new ItemStack[this.getSizeInventory()];
        this.customName = null;

        if (this.stack != null && this.stack.getItem() == itemContainer)
            this.readFromNBT(this.stack.getTagCompound());
    }

    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt != null) {
            if (nbt.hasKey("inventory")) {
                NBTTagList items = nbt.getTagList("inventory", 10);
                for (int i = 0; i < items.tagCount(); i++) {
                    NBTTagCompound nbtItem = items.getCompoundTagAt(i);
                    byte slot = nbtItem.getByte("slot");
                    this.inventory[slot] = ItemStack.loadItemStackFromNBT(nbtItem);
                }
            }

            if (nbt.hasKey("display", 10)) {
                NBTTagCompound nbtDisplay = nbt.getCompoundTag("display");
                if (nbtDisplay.hasKey("Name"))
                    this.customName = nbtDisplay.getString("Name");
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < this.getSizeInventory(); i++)
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound nbtStack = new NBTTagCompound();

                nbtStack.setByte("slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(nbtStack);

                items.appendTag(nbtStack);
            }

        nbt.setTag("inventory", items);
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : String.format("container.%s:%s.name", LibMod.MOD_ID, this.originalName);
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null;
    }

    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
    }

    @Override
    public int getSizeInventory() {
        return this.rowCount * this.columnCount;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {
        if (this.inventory[slot] != null) {
            if (this.inventory[slot].stackSize <= count) {
                ItemStack itemstack1 = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack1;
            } else {
                ItemStack itemstack = this.inventory[slot].splitStack(count);

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                return itemstack;
            }
        } else
            return null;

    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        ItemStack stack = this.inventory[slot];
        this.inventory[slot] = null;

        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.inventory[slot] = stack;

        if (this.inventory[slot] != null && this.inventory[slot].stackSize > this.getInventoryStackLimit())
            this.inventory[slot].stackSize = this.getInventoryStackLimit();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack == null)
            return true;

        return stack.getItem() != this.itemContainer;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.getSizeInventory(); i++)
            this.removeStackFromSlot(i);
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }
}
