package br.com.cabralrodrigo.minecraft.jarm.common.registry;

import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibDimensions;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public final class ModDimensions {
    public static void preInit() {
        DimensionManager.registerDimension(LibDimensions.UMBRAL_ID, DimensionType.NETHER);
    }
}
