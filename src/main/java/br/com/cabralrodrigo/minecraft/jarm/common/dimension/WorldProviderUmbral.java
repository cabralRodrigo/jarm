package br.com.cabralrodrigo.minecraft.jarm.common.dimension;

import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibDimensions;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibVanilla;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderUmbral extends WorldProvider {

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public BlockPos getSpawnPoint() {
        return new BlockPos(7.5F, 65, 7.5F);
    }

    @Override
    public DimensionType getDimensionType() {
        return LibVanilla.DIMENSION_TYPE_UMBRAL;
    }

    @Override
    public BlockPos getSpawnCoordinate() {
        return super.getSpawnPoint();
    }
}
