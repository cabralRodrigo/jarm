package br.com.cabralrodrigo.minecraft.jarm.client.gui;

import br.com.cabralrodrigo.minecraft.jarm.client.lib.LibGuiTextures;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.ContainerBase;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.amulet.ContainerAmuletStorage;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet.InventoryAmuletStorage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiAmuletStorage extends GuiContainer {
    public GuiAmuletStorage(EntityPlayer player, EnumHand hand, InventoryAmuletStorage inventoryAmuletStorage) {
        super(new ContainerAmuletStorage(player, hand, inventoryAmuletStorage));
        this.xSize = 176;
        this.ySize = 222;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibGuiTextures.GUI_AMULET_STORAGE);
        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;
        drawTexturedModalRect(guiX, guiY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        ContainerBase container = (ContainerBase) this.inventorySlots;

        this.fontRendererObj.drawString(container.getInventoryName(), 8, 6, 4210752);
        this.fontRendererObj.drawString(container.getPlayer().inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
}
