package br.com.cabralrodrigo.minecraft.jarm.client.gui;

import br.com.cabralrodrigo.minecraft.jarm.client.lib.LibGuiTextures;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.misc.ContainerAmuletStamper;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityAmuletStamper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class GuiAmuletStamper extends GuiContainer {
    private TileEntityAmuletStamper stamper;

    public GuiAmuletStamper(EntityPlayer player, TileEntityAmuletStamper stamper) {
        super(new ContainerAmuletStamper(player, stamper));
        this.stamper = stamper;
        this.xSize = 176;
        this.ySize = 213;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, ((this.width - this.xSize) / 2) + 106, ((this.height - this.ySize) / 2) + 99, 64, 20, "Craft"));
        this.buttonList.get(0).enabled = true;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(LibGuiTextures.GUI_AMULET_STAMPER);
        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;
        drawTexturedModalRect(guiX, guiY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        /*



        */

        ContainerAmuletStamper container = (ContainerAmuletStamper) this.inventorySlots;
        this.fontRendererObj.drawString(container.getPlayer().inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
        this.fontRendererObj.drawString(this.stamper.getDisplayName().getUnformattedText(), 8, 6, 4210752);

        this.fontRendererObj.drawString("12 Levels", 30, this.ySize - 105, 4210752);
        //this.fontRendererObj.drawString("Amulet Stamper", 8, 6, 4210752);
        //this.fontRendererObj.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
    }
}
