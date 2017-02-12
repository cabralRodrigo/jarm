package br.com.cabralrodrigo.minecraft.jarm.common.inventory.slot;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;

public class SlotPotion extends SlotWhitelist {
    private final boolean allowSplashPotion;

    public SlotPotion(Container container, IInventory inventory, int slot, int x, int y, boolean allowSplashPotion) {
        super(container, inventory, slot, x, y, ItemPotion.class);
        this.allowSplashPotion = allowSplashPotion;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        Item item = stack.getItem();
        boolean isSplash = item instanceof ItemSplashPotion || stack.getItem() instanceof ItemLingeringPotion;

        return super.isItemValid(stack) && isSplash == this.allowSplashPotion;
    }
}
