package cabralrodrigo.mc.mods.jarm.common.proxy;

import cabralrodrigo.mc.mods.jarm.common.Jarm;
import cabralrodrigo.mc.mods.jarm.common.inventory.container.amulet.ContainerAmuletPotion;
import cabralrodrigo.mc.mods.jarm.common.inventory.container.amulet.ContainerAmuletStorage;
import cabralrodrigo.mc.mods.jarm.common.inventory.container.misc.ContainerEnderEnchantmentTable;
import cabralrodrigo.mc.mods.jarm.common.inventory.container.misc.ContainerSeedBag;
import cabralrodrigo.mc.mods.jarm.common.lib.LibGui;
import cabralrodrigo.mc.mods.jarm.common.registry.*;
import cabralrodrigo.mc.mods.jarm.common.registry.util.IRegistrable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ModDimensions.preInit();
        ModBlocks.preInit();
        ModItems.preInit();
        ModEventHandlers.preInit();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Jarm.instance, Jarm.proxy);
        ModRecipes.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Override
    public <T extends Item & IRegistrable> void registerItem(T item) {
        GameRegistry.registerItem(item, item.getName());
    }

    @Override
    public <T extends Block & IRegistrable> void registerBlock(T block) {
        GameRegistry.registerBlock(block, block.getName());
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case LibGui.AMULET_STORAGE:
                return new ContainerAmuletStorage(player);
            case LibGui.ENDER_ENCHATMENT_TABLE:
                return new ContainerEnderEnchantmentTable(player);
            case LibGui.SEED_BAG:
                return new ContainerSeedBag(player);
            case LibGui.AMULET_POTION:
                return new ContainerAmuletPotion(player);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
