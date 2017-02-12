package br.com.cabralrodrigo.minecraft.jarm.common.item.misc;

import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import br.com.cabralrodrigo.minecraft.jarm.api.util.ItemHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState state = world.getBlockState(pos);
        if (state != null) {
            if (state.getBlock() == Blocks.WOOL) {
                if (!world.isRemote) {
                    world.setBlockToAir(pos);
                    ItemHelper.spawnItemStack(world, pos, new ItemStack(Items.STRING, 4));
                } else

                    player.playSound(SoundEvents.BLOCK_CLOTH_BREAK, 1F, rand.nextFloat() * (1F - .2F) + .2F);

                return EnumActionResult.SUCCESS;
            } else
                return EnumActionResult.FAIL;
        } else
            return EnumActionResult.PASS;
    }
}
