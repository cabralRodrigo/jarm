package cabralrodrigo.mc.mods.jarm.client.proxy;

import cabralrodrigo.mc.mods.jarm.client.gui.GuiAmuletPotion;
import cabralrodrigo.mc.mods.jarm.client.gui.GuiAmuletStorage;
import cabralrodrigo.mc.mods.jarm.client.gui.GuiEnderEnchantmentTable;
import cabralrodrigo.mc.mods.jarm.client.gui.GuiSeedBag;
import cabralrodrigo.mc.mods.jarm.client.registry.ModRenderers;
import cabralrodrigo.mc.mods.jarm.client.util.InventoryRenderHelper;
import cabralrodrigo.mc.mods.jarm.common.lib.LibGui;
import cabralrodrigo.mc.mods.jarm.common.lib.LibMod;
import cabralrodrigo.mc.mods.jarm.common.proxy.CommonProxy;
import cabralrodrigo.mc.mods.jarm.common.registry.util.IRegistrable;
import cabralrodrigo.mc.mods.jarm.common.registry.util.IVariantRegistrable;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (world instanceof WorldClient)
            switch (id) {
                case LibGui.AMULET_STORAGE:
                    return new GuiAmuletStorage(player);
                case LibGui.ENDER_ENCHATMENT_TABLE:
                    return new GuiEnderEnchantmentTable(player);
                case LibGui.SEED_BAG:
                    return new GuiSeedBag(player);
                case LibGui.AMULET_POTION:
                    return new GuiAmuletPotion(player);
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
