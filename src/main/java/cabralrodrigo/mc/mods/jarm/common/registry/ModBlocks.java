package cabralrodrigo.mc.mods.jarm.common.registry;

import cabralrodrigo.mc.mods.jarm.common.Jarm;
import cabralrodrigo.mc.mods.jarm.common.block.BlockAmuletStamper;
import cabralrodrigo.mc.mods.jarm.common.tileentity.TileEntityAmuletStamper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModBlocks {

    //public static BlockEnderEnchantmentTable ender_enchantment_table;
    public static BlockAmuletStamper amulet_stamper;

    public static void preInit() {
        //Jarm.proxy.registerBlock(ender_enchantment_table = new BlockEnderEnchantmentTable());
        Jarm.proxy.registerBlock(amulet_stamper = new BlockAmuletStamper());
    }
}
