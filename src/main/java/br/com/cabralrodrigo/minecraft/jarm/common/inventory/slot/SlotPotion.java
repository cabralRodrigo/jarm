package br.com.cabralrodrigo.minecraft.jarm.common.inventory.slot;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class SlotPotion extends SlotWhitelist {
    private final boolean alowSplashPotion;

    public SlotPotion(Container container, IInventory inventory, int slot, int x, int y, boolean alowSplashPotion) {
        super(container, inventory, slot, x, y, ItemPotion.class);
        this.alowSplashPotion = alowSplashPotion;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return super.isItemValid(stack) && (ItemPotion.isSplash(stack.getItemDamage()) == this.alowSplashPotion);
    }
}
