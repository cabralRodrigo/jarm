package cabralrodrigo.mc.mods.jarm.common.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IInventoryNBT extends IInventory {
    void serializeIntoItemStack(ItemStack stack);

    void deserializeFromItemStack(ItemStack stack);

    void writeToNBT(NBTTagCompound nbt);

    void readFromNBT(NBTTagCompound nbt);
}
