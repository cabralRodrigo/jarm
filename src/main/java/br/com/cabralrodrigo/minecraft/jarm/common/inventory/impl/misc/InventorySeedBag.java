package br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.misc;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.InventoryBaseNew;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class InventorySeedBag extends InventoryBaseNew {
    public InventorySeedBag(ItemStack stackContainer) {
        super((IItemHandlerModifiable) stackContainer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        this.setName(stackContainer);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.getItem() instanceof IPlantable)
            return super.insertItem(slot, stack, simulate);
        else
            return stack;
    }
}