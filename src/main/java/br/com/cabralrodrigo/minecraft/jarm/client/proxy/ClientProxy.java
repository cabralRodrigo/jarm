package br.com.cabralrodrigo.minecraft.jarm.client.proxy;

import br.com.cabralrodrigo.minecraft.jarm.client.gui.*;
import br.com.cabralrodrigo.minecraft.jarm.client.registry.ModRenderers;
import br.com.cabralrodrigo.minecraft.jarm.client.util.InventoryRenderHelper;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import br.com.cabralrodrigo.minecraft.jarm.common.proxy.CommonProxy;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IRegistrable;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IVariantRegistrable;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityAmuletStamper;
import br.com.cabralrodrigo.minecraft.jarm.common.util.EnumHandHelper;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map.Entry;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    private InventoryRenderHelper renderHelper = new InventoryRenderHelper(LibMod.bindModId(':', ""));

    @Override
    public <T extends Item & IRegistrable> void registerItem(T item) {
        super.registerItem(item);
        this.registerRender(item);
    }

    @Override
    public <T extends Block & IRegistrable> void registerBlock(T block) {
        super.registerBlock(block);
        this.registerRender(block);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        ModRenderers.init();
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int p1, int p2, int p3) {
        if (world instanceof WorldClient)
            switch (id) {
                case LibGui.AMULET_STORAGE:
                    return new GuiAmuletStorage(player, EnumHandHelper.ToEnum(p1));
                case LibGui.ENDER_ENCHATMENT_TABLE:
                    return new GuiEnderEnchantmentTable(player);
                case LibGui.SEED_BAG:
                    return new GuiSeedBag(player, EnumHandHelper.ToEnum(p1));
                case LibGui.AMULET_POTION:
                    return new GuiAmuletPotion(player, EnumHandHelper.ToEnum(p1));
                case LibGui.AMULET_STAMPER:
                    return new GuiAmuletStamper(player, (TileEntityAmuletStamper) world.getTileEntity(new BlockPos(p1, p2, p3)));
            }

        return null;
    }

    private void registerRender(Object itemOrBlock) {
        Item item = itemOrBlock instanceof Block ? ItemBlock.getItemFromBlock((Block) itemOrBlock) : (Item) itemOrBlock;

        if (itemOrBlock instanceof IVariantRegistrable) {
            String baseName = ((IVariantRegistrable) itemOrBlock).getName();
            for (Entry<String, Integer> entry : ((IVariantRegistrable) itemOrBlock).getVariants().entrySet()) {
                this.renderHelper.itemRender(item, entry.getValue(), baseName + "." + entry.getKey());
            }

        } else if (itemOrBlock instanceof IRegistrable)
            this.renderHelper.itemRender(item, ((IRegistrable) itemOrBlock).getName());
    }
}
