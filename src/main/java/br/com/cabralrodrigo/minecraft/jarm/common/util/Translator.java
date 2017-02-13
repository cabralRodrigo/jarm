package br.com.cabralrodrigo.minecraft.jarm.common.util;

import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public final class Translator {
    public static String translate(String category, String key, Object... format) {
        String builtKey = String.format("%s.%s:%s", category, LibMod.MOD_ID, key);
        String translated = I18n.format(builtKey, format);
        return translated.replace("$", "\u00a7");
    }

    public static String translate(String category, String key, String key2, Object... format) {
        return translate(category, key + "." + key2, format);
    }

    public static String translateEntityName(String entity) {
        return  I18n.format("entity." + EntityList.getTranslationName(new ResourceLocation(entity)) + ".name");
    }

    public static ITextComponent translateChat(String category, String key, String key2) {
        return new TextComponentString(translate(category, key, key2));
    }
}
