package cabralrodrigo.mc.mods.jarm.common.inventory;

import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

public class InventoryAmuletStorage extends InventoryJarmBase {
    public InventoryAmuletStorage(ItemStack stackContainer) {
        super(ModItems.amulet_storage, stackContainer, 6, 9);
    }
}
