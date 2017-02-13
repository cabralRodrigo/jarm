
package br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.Set;

public class InventoryBaseNew implements IItemHandlerModifiable {
    private final IItemHandlerModifiable handler;
    private String inventoryName;

    public InventoryBaseNew(IItemHandlerModifiable handler) {
        this.handler = handler;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        this.handler.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots() {
        return this.handler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.handler.getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return this.handler.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.handler.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.handler.getSlotLimit(slot);
    }

    public String getName() {
        return this.inventoryName;
    }

    protected void setName(ItemStack itemStack) {
        this.inventoryName = itemStack.getItem().getItemStackDisplayName(itemStack);

        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt != null && nbt.hasKey("display", 10)) {
            NBTTagCompound nbtDisplay = nbt.getCompoundTag("display");
            if (nbtDisplay != null && nbtDisplay.hasKey("Name"))
                this.inventoryName = nbtDisplay.getString("Name");
        }
    }
}