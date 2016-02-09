package cabralrodrigo.mc.mods.jarm.client.lib;

import cabralrodrigo.mc.mods.jarm.common.lib.LibBlocks;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import cabralrodrigo.mc.mods.jarm.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class LibGuiTextures {
    public static final ResourceLocation GUI_AMULET_STORAGE = getResourceLocation(LibItems.AMULET_STORAGE);
    public static final ResourceLocation GUI_SEED_BAG = getResourceLocation(LibItems.SEED_BAG);
    public static final ResourceLocation GUI_ENDER_ENCHANTMENT_TABLE = getResourceLocation(LibBlocks.ENDER_ENCHANTMENT_TABLE);
    public static final ResourceLocation GUI_AMULET_POTION = getResourceLocation(LibItems.AMULET_POTION);

    private static ResourceLocation getResourceLocation(String name) {
        return new ResourceLocation(LibMod.MOD_ID, String.format("textures/gui/%s.png", name));
    }
}
