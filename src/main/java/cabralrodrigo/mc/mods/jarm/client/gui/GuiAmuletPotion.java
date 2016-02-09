package cabralrodrigo.mc.mods.jarm.client.gui;

import cabralrodrigo.mc.mods.jarm.client.lib.LibGuiTextures;
import cabralrodrigo.mc.mods.jarm.common.inventory.container.ContainerAmuletPotion;
import cabralrodrigo.mc.mods.jarm.common.inventory.container.ContainerJarmBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiAmuletPotion extends GuiContainer {
    public GuiAmuletPotion(EntityPlayer player) {
        super(new ContainerAmuletPotion(player));
        this.xSize = 176;
        this.ySize = 168;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibGuiTextures.GUI_AMULET_POTION);
        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;
        drawTexturedModalRect(guiX, guiY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        ContainerJarmBase container = (ContainerJarmBase) this.inventorySlots;
        this.fontRendererObj.drawString(container.getJarmInventory().getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(container.getPlayer().inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
}
