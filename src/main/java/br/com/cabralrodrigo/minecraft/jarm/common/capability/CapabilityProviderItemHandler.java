package br.com.cabralrodrigo.minecraft.jarm.common.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderItemHandler implements ICapabilitySerializable<NBTTagCompound> {

    private final IItemHandler inventory;

    public CapabilityProviderItemHandler(int size) {
        this.inventory = new ItemStackHandler(size);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("items", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null));

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, nbt.getTagList("items", 10));
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (hasCapability(capability, facing))
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        else
            return null;
    }
}
