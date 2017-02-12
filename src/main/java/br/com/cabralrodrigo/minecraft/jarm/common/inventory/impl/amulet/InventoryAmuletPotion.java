package br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.InventoryItemBase;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

public class InventoryAmuletPotion extends InventoryItemBase {
    public InventoryAmuletPotion(ItemStack stackContainer) {
        super(ModItems.amulet_potion, stackContainer, 3, 9);
    }
}
