package cabralrodrigo.mc.mods.jarm.client.gui;

import cabralrodrigo.mc.mods.jarm.client.lib.LibGuiTextures;
import cabralrodrigo.mc.mods.jarm.common.inventory.container.ContainerEnderEnchantmentTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiEnderEnchantmentTable extends GuiContainer {

    public GuiEnderEnchantmentTable(EntityPlayer player) {
        super(new ContainerEnderEnchantmentTable(player));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibGuiTextures.GUI_ENDER_ENCHANTMENT_TABLE);
        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;
        drawTexturedModalRect(guiX, guiY, 0, 0, this.xSize, this.ySize);
    }
}
