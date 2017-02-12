package br.com.cabralrodrigo.minecraft.jarm.common.registry;

import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.block.BlockAmuletStamper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModBlocks {

    //public static BlockEnderEnchantmentTable ender_enchantment_table;
    public static BlockAmuletStamper amulet_stamper;

    public static void preInit() {
        //Jarm.proxy.registerBlock(ender_enchantment_table = new BlockEnderEnchantmentTable());
        Jarm.proxy.registerBlock(amulet_stamper = new BlockAmuletStamper());
    }
}
