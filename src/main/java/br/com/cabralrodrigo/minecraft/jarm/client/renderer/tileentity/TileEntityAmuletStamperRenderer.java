package br.com.cabralrodrigo.minecraft.jarm.client.renderer.tileentity;

import br.com.cabralrodrigo.minecraft.jarm.common.registry.ModItems;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityAmuletStamper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityAmuletStamperRenderer extends TileEntitySpecialRenderer<TileEntityAmuletStamper> {

    private static final ItemStack stackTest = new ItemStack(ModItems.amulet_potion, 0, 1);

    @Override
    public void renderTileEntityAt(TileEntityAmuletStamper te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        renderItem(te.getWorld(), te.getStackInSlot(4), partialTicks);

        GlStateManager.popMatrix();
    }


    private void renderItem(World world, ItemStack stack, float partialTicks) {
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        if (stack != null) {
            GlStateManager.translate(0.5, 0.35, 0.5);
            GlStateManager.rotate(90, 1, 0, 0);

            EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
            entityitem.getEntityItem().stackSize = 1;

            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.pushAttrib();

            RenderHelper.enableStandardItemLighting();
            itemRenderer.renderItem(entityitem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();

            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}
