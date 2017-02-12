package br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.InventoryItemBase;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

public class InventoryAmuletStorage extends InventoryItemBase {
    public InventoryAmuletStorage(ItemStack stackContainer) {
        super(ModItems.amulet_storage, stackContainer, 6, 9);
    }
}
