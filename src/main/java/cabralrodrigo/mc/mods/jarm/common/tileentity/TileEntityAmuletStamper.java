package cabralrodrigo.mc.mods.jarm.common.tileentity;

import cabralrodrigo.mc.mods.jarm.common.inventory.impl.misc.InventoryAmuletStamper;
import cabralrodrigo.mc.mods.jarm.common.util.IInventoryNBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;

public class TileEntityAmuletStamper extends TileEntity implements IInventoryNBT {


    private InventoryAmuletStamper inventory;

    public TileEntityAmuletStamper() {
        this.inventory = new InventoryAmuletStamper();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        this.inventory.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory.readFromNBT(compound);
    }

    @Override
    public void serializeIntoItemStack(ItemStack stack) {
        this.inventory.serializeIntoItemStack(stack);
    }

    @Override
    public void deserializeFromItemStack(ItemStack stack) {
        this.inventory.deserializeFromItemStack(stack);
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return this.inventory.decrStackSize(index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return this.inventory.removeStackFromSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventory.setInventorySlotContents(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.inventory.isUseableByPlayer(player);
    }

    @Override
    public void openInventory(EntityPlayer player) {
        this.inventory.openInventory(player);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.inventory.closeInventory(player);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return this.inventory.isItemValidForSlot(index, stack);
    }

    @Override
    public int getField(int id) {
        return this.inventory.getField(id);
    }

    @Override
    public void setField(int id, int value) {
        this.inventory.setField(id, value);
    }

    @Override
    public int getFieldCount() {
        return this.inventory.getFieldCount();
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public String getName() {
        return this.inventory.getName();
    }

    @Override
    public boolean hasCustomName() {
        return this.inventory.hasCustomName();
    }

    @Override
    public IChatComponent getDisplayName() {
        return this.inventory.getDisplayName();
    }
}
