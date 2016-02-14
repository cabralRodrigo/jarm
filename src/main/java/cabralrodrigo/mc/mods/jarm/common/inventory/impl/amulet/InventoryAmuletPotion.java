package cabralrodrigo.mc.mods.jarm.common.inventory.impl.amulet;

import cabralrodrigo.mc.mods.jarm.common.inventory.impl.InventoryItemBase;
import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

public class InventoryAmuletPotion extends InventoryItemBase {
    public InventoryAmuletPotion(ItemStack stackContainer) {
        super(ModItems.amulet_potion, stackContainer, 3, 9);
    }
}
