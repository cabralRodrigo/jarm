package cabralrodrigo.mc.mods.jarm.common.inventory.impl.misc;

import cabralrodrigo.mc.mods.jarm.common.inventory.impl.InventoryItemBase;
import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

public class InventorySeedBag extends InventoryItemBase {

    public InventorySeedBag(ItemStack stackContainer) {
        super(ModItems.seed_bag, stackContainer, 3, 9);
    }
}
