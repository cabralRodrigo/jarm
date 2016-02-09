package cabralrodrigo.mc.mods.jarm.common.proxy;

import cabralrodrigo.mc.mods.jarm.common.registry.util.IRegistrable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

public interface IProxy extends IGuiHandler {
    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    <T extends Item & IRegistrable> void registerItem(T item);

    <T extends Block & IRegistrable> void registerBlock(T block);
}
