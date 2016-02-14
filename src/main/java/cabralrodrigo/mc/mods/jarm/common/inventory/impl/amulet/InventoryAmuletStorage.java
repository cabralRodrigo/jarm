package cabralrodrigo.mc.mods.jarm.common.inventory.impl.amulet;

import cabralrodrigo.mc.mods.jarm.common.inventory.impl.InventoryItemBase;
import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

public class InventoryAmuletStorage extends InventoryItemBase {
    public InventoryAmuletStorage(ItemStack stackContainer) {
        super(ModItems.amulet_storage, stackContainer, 6, 9);
    }
}
