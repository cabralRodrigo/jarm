package br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.InventoryBaseNew;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class InventoryAmuletStorage extends InventoryBaseNew {
    public InventoryAmuletStorage(ItemStack stackContainer) {
        super((IItemHandlerModifiable) stackContainer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        this.setName(stackContainer);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.getItem() == ModItems.amulet_storage)
            return stack;
        else
            return super.insertItem(slot, stack, simulate);
    }
}
