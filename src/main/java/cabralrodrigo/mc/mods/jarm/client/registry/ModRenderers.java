package cabralrodrigo.mc.mods.jarm.client.registry;

import cabralrodrigo.mc.mods.jarm.client.renderer.tileentity.TileEntityAmuletStamperRenderer;
import cabralrodrigo.mc.mods.jarm.client.renderer.tileentity.TileEntityEnderEnchantmentTableRenderer;
import cabralrodrigo.mc.mods.jarm.common.tileentity.TileEntityAmuletStamper;
import cabralrodrigo.mc.mods.jarm.common.tileentity.TileEntityEnderEnchatmentTable;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public final class ModRenderers {
    public static void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnderEnchatmentTable.class, new TileEntityEnderEnchantmentTableRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmuletStamper.class, new TileEntityAmuletStamperRenderer());
    }
}
