package cabralrodrigo.mc.mods.jarm.common.inventory.container.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerEnderEnchantmentTable extends Container {
    public ContainerEnderEnchantmentTable(EntityPlayer player) {
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
