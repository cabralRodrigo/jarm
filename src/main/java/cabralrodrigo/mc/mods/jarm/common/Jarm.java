package cabralrodrigo.mc.mods.jarm.common;

import cabralrodrigo.mc.mods.jarm.common.lib.LibMod;
import cabralrodrigo.mc.mods.jarm.common.proxy.IProxy;
import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = LibMod.MOD_ID, name = LibMod.MOD_NAME, version = LibMod.MOD_VERSION, dependencies = LibMod.DEPENDECIES)
public class Jarm {

    @Instance(LibMod.MOD_ID)
    public static Jarm instance;

    @SidedProxy(clientSide = LibMod.PROXY_CLIENT, serverSide = LibMod.PROXY_SERVER)
    public static IProxy proxy;

    public static CreativeTabs creativeTab = new CreativeTabs(LibMod.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return ModItems.amulet_storage;
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
