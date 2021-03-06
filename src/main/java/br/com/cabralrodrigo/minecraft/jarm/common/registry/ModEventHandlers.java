package br.com.cabralrodrigo.minecraft.jarm.common.registry;

import br.com.cabralrodrigo.minecraft.jarm.common.handler.EventHandlerItemFood;
import br.com.cabralrodrigo.minecraft.jarm.api.explosion.ExplosionCraftingManager;
import net.minecraftforge.common.MinecraftForge;

public final class ModEventHandlers {
    public static void preInit() {
        MinecraftForge.EVENT_BUS.register(ModItems.orb_of_sin);
        MinecraftForge.EVENT_BUS.register(ModItems.super_fluffy_boots);
        MinecraftForge.EVENT_BUS.register(ModItems.super_fluffy_chestplate);
        MinecraftForge.EVENT_BUS.register(ModItems.amulet_magnetic);
        MinecraftForge.EVENT_BUS.register(ModItems.amulet_potion);
        MinecraftForge.EVENT_BUS.register(new ModRecipes());
        MinecraftForge.EVENT_BUS.register(new EventHandlerItemFood());
        MinecraftForge.EVENT_BUS.register(ExplosionCraftingManager.instance());
    }
}
