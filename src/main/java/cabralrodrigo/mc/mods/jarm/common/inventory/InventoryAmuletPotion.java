package cabralrodrigo.mc.mods.jarm.common.inventory;

import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

public class InventoryAmuletPotion extends InventoryJarmBase {
    public InventoryAmuletPotion(ItemStack stackContainer) {
        super(ModItems.amulet_potion, stackContainer, 3, 9);
    }
}
