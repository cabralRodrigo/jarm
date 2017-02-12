package br.com.cabralrodrigo.minecraft.jarm.common.registry;

import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibDimensions;
import net.minecraftforge.common.DimensionManager;

public final class ModDimensions {
    public static void preInit() {
        DimensionManager.registerProviderType(LibDimensions.UMBRAL_ID, WorldProviderUmbral.class, false);
        DimensionManager.registerDimension(LibDimensions.UMBRAL_ID, LibDimensions.UMBRAL_ID);
    }
}
