package br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl;

import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import br.com.cabralrodrigo.minecraft.jarm.common.util.IInventoryNBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryBase implements IInventoryNBT {
    protected int size;
    protected String containerName;
    protected String customName;
    protected ItemStack[] inventory;

    public InventoryBase(String containerName, int size) {
        this.containerName = containerName;
        this.size = size;
        this.inventory = new ItemStack[this.getSizeInventory()];
    }

    @Override
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

        if (this.hasCustomName()) {
            NBTTagCompound nbtDisplay = new NBTTagCompound();
            nbtDisplay.setString("Name", this.customName);

            nbt.setTag("display", nbtDisplay);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList items = nbt.getTagList("inventory", 10);
        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound nbtItem = items.getCompoundTagAt(i);
            byte slot = nbtItem.getByte("slot");
            this.inventory[slot] = ItemStack.loadItemStackFromNBT(nbtItem);
        }

        if (nbt.hasKey("display", 10)) {
            NBTTagCompound nbtDisplay = nbt.getCompoundTag("display");
            if (nbtDisplay.hasKey("Name"))
                this.customName = nbtDisplay.getString("Name");
        }
    }

    @Override
    public void serializeIntoItemStack(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null)
            nbt = new NBTTagCompound();

        this.writeToNBT(nbt);
        stack.setTagCompound(nbt);
    }

    @Override
    public void deserializeFromItemStack(ItemStack stack) {
        NBTTagCompound nbtStack = stack.getTagCompound();
        if (nbtStack != null)
            this.readFromNBT(nbtStack);
    }

    @Override
    public int getSizeInventory() {
        return this.size;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.inventory[index] != null) {
            if (this.inventory[index].stackSize <= count) {
                ItemStack itemstack1 = this.inventory[index];
                this.inventory[index] = null;
                return itemstack1;
            } else {
                ItemStack itemstack = this.inventory[index].splitStack(count);

                if (this.inventory[index].stackSize == 0) {
                    this.inventory[index] = null;
                }

                return itemstack;
            }
        } else
            return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = this.inventory[index];
        this.inventory[index] = null;

        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventory[index] = stack;

        if (this.inventory[index] != null && this.inventory[index].stackSize > this.getInventoryStackLimit())
            this.inventory[index].stackSize = this.getInventoryStackLimit();
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
        return true;
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

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : String.format("container.%s:%s.name", LibMod.MOD_ID, this.containerName);
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null;
    }

    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
    }
}
