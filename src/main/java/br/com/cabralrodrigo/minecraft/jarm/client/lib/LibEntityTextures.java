package br.com.cabralrodrigo.minecraft.jarm.client.lib;

import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibBlocks;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class LibEntityTextures {
    public static final ResourceLocation BOOK_ENDER_ENCHANTMENT_TABLE = new ResourceLocation(LibMod.MOD_ID, "textures/entity/" + LibBlocks.ENDER_ENCHANTMENT_TABLE + "_book.png");
}
