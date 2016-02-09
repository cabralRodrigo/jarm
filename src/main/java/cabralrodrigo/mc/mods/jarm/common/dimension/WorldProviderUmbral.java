package cabralrodrigo.mc.mods.jarm.common.dimension;

import cabralrodrigo.mc.mods.jarm.common.lib.LibDimensions;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderUmbral extends WorldProvider {

    @Override
    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.desertHills, 0.8F);
        this.dimensionId = LibDimensions.UMBRAL_ID;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderUmbral(this.worldObj);
    }

    @Override
    public String getDimensionName() {
        return "Umbral";
    }

    @Override
    public String getInternalNameSuffix() {
        return "UB";
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public BlockPos getSpawnPoint() {
        return new BlockPos(7.5F, 65, 7.5F);
    }

    @Override
    public BlockPos getSpawnCoordinate() {
        return super.getSpawnPoint();
    }
}
