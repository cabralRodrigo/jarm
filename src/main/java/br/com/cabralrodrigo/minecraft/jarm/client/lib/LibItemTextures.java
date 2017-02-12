package br.com.cabralrodrigo.minecraft.jarm.client.lib;

import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class LibItemTextures {
    public static final String FLUFFY_ARMOR = buildArmorTexture("fluffy_armor");


    private static String buildArmorTexture(String name) {
        return LibMod.bindModId(':', "textures/models/armor/" + name + ".png");
    }
}
