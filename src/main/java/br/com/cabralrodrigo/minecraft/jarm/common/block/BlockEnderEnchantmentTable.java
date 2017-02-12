package br.com.cabralrodrigo.minecraft.jarm.common.block;

import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibBlocks;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IRegistrable;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityEnderEnchantmentTable;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnderEnchantmentTable extends BlockEnchantmentTable implements IRegistrable {
    public BlockEnderEnchantmentTable() {
        super();
        this.setUnlocalizedName(LibMod.bindModId(':', this.getName()));
        this.setRegistryName(LibMod.MOD_ID, this.getName());
        this.setCreativeTab(Jarm.creativeTab);
    }

    @Override
    public String getName() {
        return LibBlocks.ENDER_ENCHANTMENT_TABLE;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityEnderEnchantmentTable();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityEnderEnchantmentTable)
                player.openGui(LibMod.MOD_ID, LibGui.ENDER_ENCHATMENT_TABLE, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
