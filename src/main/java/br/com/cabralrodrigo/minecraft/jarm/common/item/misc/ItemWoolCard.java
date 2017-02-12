package br.com.cabralrodrigo.minecraft.jarm.common.item.misc;

import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import br.com.cabralrodrigo.minecraft.jarm.common.util.ItemHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class ItemWoolCard extends ItemJarmBase {
    private static Random rand = new Random();

    public ItemWoolCard() {
        super(LibItems.WOOL_CARD);
        this.setHasSubtypes(false);
        this.setNoRepair();
        this.setFull3D();
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);

        if (GuiScreen.isShiftKeyDown())
            tooltip.add(this.translateForItem("tooltip.easter_egg"));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        if (state != null && state.getBlock() == Blocks.wool) {
            if (!world.isRemote) {
                world.setBlockToAir(pos);
                ItemHelper.spawnItemStack(world, pos, new ItemStack(Items.string, 4));
            } else
                player.playSound("dig.cloth", 1F, rand.nextFloat() * (1F - .2F) + .2F);

            return true;
        } else
            return false;
    }
}
