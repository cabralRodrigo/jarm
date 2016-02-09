package cabralrodrigo.mc.mods.jarm.common.inventory;

import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import net.minecraft.item.ItemStack;

public class InventorySeedBag extends InventoryJarmBase {

    public InventorySeedBag(ItemStack stackContainer) {
        super(ModItems.seed_bag, stackContainer, 3, 9);
    }
}
