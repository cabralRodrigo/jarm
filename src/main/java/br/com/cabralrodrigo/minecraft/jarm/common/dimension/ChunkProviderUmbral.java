package br.com.cabralrodrigo.minecraft.jarm.common.dimension;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Arrays;
import java.util.List;

public class ChunkProviderUmbral implements IChunkProvider {
    private World world;

    public ChunkProviderUmbral(World world) {
        this.world = world;
    }

    @Override
    public Chunk provideChunk(BlockPos pos) {
        return provideChunk(pos.getX() >> 4, pos.getZ() >> 4);
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        Chunk chunk = new Chunk(this.world, x, z);
        Arrays.fill(chunk.getBiomeArray(), (byte) BiomeGenBase.desert.biomeID);
        chunk.generateSkylightMap();
        chunk.setTerrainPopulated(true);
        chunk.setLightPopulated(true);
        chunk.setModified(true);

        if (x == 0 && z == 0) {
            for (int bX = 6; bX < 9; bX++)
                for (int bZ = 6; bZ < 9; bZ++)
                    chunk.setBlockState(new BlockPos(bX, 64, bZ), Blocks.stone.getDefaultState());
        }

        return chunk;
    }

    @Override
    public boolean chunkExists(int x, int z) {
        return true;
    }

    @Override
    public void populate(IChunkProvider chunkProvider, int x, int y) {
    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate progressCallback) {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "UmbralLevelSource";
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public boolean func_177460_a(IChunkProvider chunkProvider, Chunk chunk, int x, int y) {
        return false;
    }

    public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiomeGenForCoords(pos).getSpawnableList(creatureType);
    }

    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
        return null;
    }

    public void recreateStructures(Chunk chunk, int x, int y) {
    }
}
