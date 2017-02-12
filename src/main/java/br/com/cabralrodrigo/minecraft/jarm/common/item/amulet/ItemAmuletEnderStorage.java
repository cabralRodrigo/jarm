package br.com.cabralrodrigo.minecraft.jarm.common.item.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemAmuletBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAmuletEnderStorage extends ItemAmuletBase {
    public ItemAmuletEnderStorage() {
        super(LibItems.AMULET_ENDER_STORAGE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            player.displayGUIChest(player.getInventoryEnderChest());
            player.addStat(StatList.ENDERCHEST_OPENED);
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
