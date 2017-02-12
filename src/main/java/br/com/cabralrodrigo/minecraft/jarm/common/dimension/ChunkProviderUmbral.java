package br.com.cabralrodrigo.minecraft.jarm.common.dimension;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class ChunkProviderUmbral implements IChunkProvider {
    private World world;

    public ChunkProviderUmbral(World world) {
        this.world = world;
    }

    @Nullable
    @Override
    public Chunk getLoadedChunk(int x, int z) {
        return null;
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        return null;
    }

    @Override
    public boolean tick() {
        return false;
    }

    @Override
    public String makeString() {
        return "UmbralLevelSource";
    }

    @Override
    public boolean isChunkGeneratedAt(int p_191062_1_, int p_191062_2_) {
        return false;
    }
}
