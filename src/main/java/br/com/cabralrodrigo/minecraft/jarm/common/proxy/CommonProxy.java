package br.com.cabralrodrigo.minecraft.jarm.common.proxy;

import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.amulet.ContainerAmuletPotion;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.amulet.ContainerAmuletStorage;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.misc.ContainerAmuletStamper;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.misc.ContainerEnderEnchantmentTable;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.misc.ContainerSeedBag;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet.InventoryAmuletPotion;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet.InventoryAmuletStorage;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.misc.InventorySeedBag;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.*;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IRegistrable;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.ITileEntityBlock;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityAmuletStamper;
import br.com.cabralrodrigo.minecraft.jarm.common.util.EnumHandHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
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
        ForgeRegistries.ITEMS.register(item);
    }

    @Override
    public <T extends Block & IRegistrable> void registerBlock(T block) {
        ForgeRegistries.BLOCKS.register(block);
        if (block instanceof ITileEntityBlock)
            GameRegistry.registerTileEntity(((ITileEntityBlock) block).getTileEntityClass(), block.getName());
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int p1, int p2, int p3) {
        EnumHand hand = null;
        BlockPos pos = null;

        if (LibGui.BlockPosOnGuiOpen.contains(id))
            pos = new BlockPos(p1, p2, p3);
        else
            hand = EnumHandHelper.toEnum(p1);

        switch (id) {
            case LibGui.AMULET_STORAGE:
                return new ContainerAmuletStorage(player, hand, new InventoryAmuletStorage(player.getHeldItem(hand)));
            case LibGui.ENDER_ENCHANTMENT_TABLE:
                return new ContainerEnderEnchantmentTable(player);
            case LibGui.SEED_BAG:
                return new ContainerSeedBag(player, hand, new InventorySeedBag(player.getHeldItem(hand)));
            case LibGui.AMULET_POTION:
                return new ContainerAmuletPotion(player, hand, new InventoryAmuletPotion(player.getHeldItem(hand)));
            case LibGui.AMULET_STAMPER:
                return new ContainerAmuletStamper(player, (TileEntityAmuletStamper) world.getTileEntity(pos));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
