package cabralrodrigo.mc.mods.jarm.common.registry.util;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;

public interface ITileEntityBlock extends ITileEntityProvider {
    Class<? extends TileEntity> getTileEntityClass();
}
