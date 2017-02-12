package br.com.cabralrodrigo.minecraft.jarm.common.item.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemAmuletBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAmuletStorage extends ItemAmuletBase {
    public ItemAmuletStorage() {
        super(LibItems.AMULET_STORAGE);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote)
            player.openGui(Jarm.instance, LibGui.AMULET_STORAGE, world, (int) player.posX, (int) player.posY, (int) player.posZ);

        return itemStack;
    }
}
