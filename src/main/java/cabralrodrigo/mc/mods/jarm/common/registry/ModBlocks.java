package cabralrodrigo.mc.mods.jarm.common.registry;

import cabralrodrigo.mc.mods.jarm.common.Jarm;
import cabralrodrigo.mc.mods.jarm.common.block.BlockEnderEnchantmentTable;

public final class ModBlocks {

    public static BlockEnderEnchantmentTable ender_enchantment_table;

    public static void preInit() {
        Jarm.proxy.registerBlock(ender_enchantment_table = new BlockEnderEnchantmentTable());
    }
}
