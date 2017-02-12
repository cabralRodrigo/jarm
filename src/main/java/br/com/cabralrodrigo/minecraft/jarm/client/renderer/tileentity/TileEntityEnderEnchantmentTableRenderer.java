package br.com.cabralrodrigo.minecraft.jarm.client.renderer.tileentity;

import br.com.cabralrodrigo.minecraft.jarm.client.lib.LibEntityTextures;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityEnderEnchatmentTable;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityEnderEnchantmentTableRenderer extends TileEntitySpecialRenderer<TileEntityEnderEnchatmentTable> {
    private ModelBook bookModel = new ModelBook();

    @Override
    public void renderTileEntityAt(TileEntityEnderEnchatmentTable te, double x, double y, double z, float partialTicks, int destroyStage) {
        TileEntityEnderEnchatmentTable.BookInfo info = te.getBookInfo();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
        float ticks = (float) info.getTickCount() + partialTicks;
        GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(ticks * 0.1F) * 0.01F, 0.0F);
        float f1;

        for (f1 = info.getBookRotation(false) - info.getBookRotation(true); f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {
        }

        while (f1 < -(float) Math.PI)
            f1 += ((float) Math.PI * 2F);

        float f2 = info.getBookRotation(true) + f1 * partialTicks;
        GlStateManager.rotate(-f2 * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
        this.bindTexture(LibEntityTextures.BOOK_ENDER_ENCHANTMENT_TABLE);
        float f3 = info.getPageFlip(true) + (info.getPageFlip(false) - info.getPageFlip(true)) * partialTicks + 0.25F;
        float f4 = info.getPageFlip(true) + (info.getPageFlip(false) - info.getPageFlip(true)) * partialTicks + 0.75F;
        f3 = (f3 - (float) MathHelper.truncateDoubleToInt((double) f3)) * 1.6F - 0.3F;
        f4 = (f4 - (float) MathHelper.truncateDoubleToInt((double) f4)) * 1.6F - 0.3F;

        if (f3 < 0.0F)
            f3 = 0.0F;

        if (f4 < 0.0F)
            f4 = 0.0F;

        if (f3 > 1.0F)
            f3 = 1.0F;

        if (f4 > 1.0F)
            f4 = 1.0F;

        float f5 = info.getBookSpread(true) + (info.getBookSpread(false) - info.getBookSpread(true)) * partialTicks;
        GlStateManager.enableCull();
        this.bookModel.render((Entity) null, ticks, f3, f4, f5, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }
}
