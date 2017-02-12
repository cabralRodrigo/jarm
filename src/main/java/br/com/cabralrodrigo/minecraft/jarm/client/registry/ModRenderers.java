package br.com.cabralrodrigo.minecraft.jarm.client.registry;

import br.com.cabralrodrigo.minecraft.jarm.client.renderer.tileentity.TileEntityAmuletStamperRenderer;
import br.com.cabralrodrigo.minecraft.jarm.client.renderer.tileentity.TileEntityEnderEnchantmentTableRenderer;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityAmuletStamper;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityEnderEnchantmentTable;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public final class ModRenderers {
    public static void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderEnchantmentTable.class, new TileEntityEnderEnchantmentTableRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmuletStamper.class, new TileEntityAmuletStamperRenderer());
    }
}