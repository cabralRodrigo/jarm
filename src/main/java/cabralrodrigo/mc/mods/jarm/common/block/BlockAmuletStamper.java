package cabralrodrigo.mc.mods.jarm.common.block;

import cabralrodrigo.mc.mods.jarm.common.Jarm;
import cabralrodrigo.mc.mods.jarm.common.lib.LibBlocks;
import cabralrodrigo.mc.mods.jarm.common.lib.LibGui;
import cabralrodrigo.mc.mods.jarm.common.lib.LibMod;
import cabralrodrigo.mc.mods.jarm.common.registry.util.IRegistrable;
import cabralrodrigo.mc.mods.jarm.common.registry.util.ITileEntityBlock;
import cabralrodrigo.mc.mods.jarm.common.tileentity.TileEntityAmuletStamper;
import cabralrodrigo.mc.mods.jarm.common.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockAmuletStamper extends Block implements ITileEntityBlock, IRegistrable {

    public BlockAmuletStamper() {
        super(Material.rock, MapColor.grayColor);
        this.setUnlocalizedName(LibMod.bindModId(':', this.getName()));
        this.setCreativeTab(Jarm.creativeTab);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
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
