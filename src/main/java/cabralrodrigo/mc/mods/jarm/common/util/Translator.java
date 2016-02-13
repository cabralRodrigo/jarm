package cabralrodrigo.mc.mods.jarm.common.util;

import cabralrodrigo.mc.mods.jarm.common.lib.LibMod;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public final class Translator {
    public static String translate(String category, String key, Object... format) {
        String builtKey = String.format("%s.%s:%s", category, LibMod.MOD_ID, key);
        String translated = StatCollector.translateToLocalFormatted(builtKey, format);

        return translated.replace("$", "\u00a7");
    }

    public static String translate(String category, String key, String key2, Object... format) {
        return translate(category, key + "." + key2, format);
    }

    public static String translateEntityName(String entity) {
        return StatCollector.translateToLocal("entity." + entity + ".name");
    }

    public static IChatComponent translateChat(String category, String key, String key2) {
        return new ChatComponentText(translate(category, key, key2));
    }
}
