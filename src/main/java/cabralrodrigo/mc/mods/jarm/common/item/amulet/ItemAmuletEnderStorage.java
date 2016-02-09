package cabralrodrigo.mc.mods.jarm.common.item.amulet;

import cabralrodrigo.mc.mods.jarm.common.item.ItemAmuletBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemAmuletEnderStorage extends ItemAmuletBase {
    public ItemAmuletEnderStorage() {
        super(LibItems.AMULET_ENDER_STORAGE);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.displayGUIChest(player.getInventoryEnderChest());
            player.triggerAchievement(StatList.field_181738_V);
        }

        return itemStack;
    }
}
