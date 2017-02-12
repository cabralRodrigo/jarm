package br.com.cabralrodrigo.minecraft.jarm.common.item.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemAmuletBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import br.com.cabralrodrigo.minecraft.jarm.common.util.EnumHandHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAmuletStorage extends ItemAmuletBase {
    public ItemAmuletStorage() {
        super(LibItems.AMULET_STORAGE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote)
            player.openGui(Jarm.instance, LibGui.AMULET_STORAGE, world, EnumHandHelper.ToInt(hand), 0, 0);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
