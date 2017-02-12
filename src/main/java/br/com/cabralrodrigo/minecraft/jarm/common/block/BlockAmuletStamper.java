package br.com.cabralrodrigo.minecraft.jarm.common.block;

import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibBlocks;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IRegistrable;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.ITileEntityBlock;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityAmuletStamper;
import br.com.cabralrodrigo.minecraft.jarm.api.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAmuletStamper extends Block implements ITileEntityBlock, IRegistrable {

    public BlockAmuletStamper() {
        super(Material.ROCK, MapColor.GRAY);
        this.setUnlocalizedName(LibMod.bindModId(':', this.getName()));
        this.setCreativeTab(Jarm.creativeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAmuletStamper();
    }

    @Override
    public String getName() {
        return LibBlocks.AMULET_STAMPER;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null && tile instanceof TileEntityAmuletStamper) {
            if (!world.isRemote) {
                TileEntityAmuletStamper stamper = (TileEntityAmuletStamper) tile;
                player.openGui(Jarm.instance, LibGui.AMULET_STAMPER, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        } else
            return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityAmuletStamper stamper = (TileEntityAmuletStamper) world.getTileEntity(pos);

        if (stamper != null)
            for (int i = 0; i < stamper.getSizeInventory(); i++)
                if (stamper.getStackInSlot(i) != null)
                    ItemHelper.spawnItemStack(world, pos, stamper.getStackInSlot(i));

        super.breakBlock(world, pos, state);
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityAmuletStamper.class;
    }
}
