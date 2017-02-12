package br.com.cabralrodrigo.minecraft.jarm.common.lib;

public final class LibMod {
    public static final String MOD_ID = "jarm";
    public static final String MOD_NAME = "J.A.R.M.";
    public static final String MOD_VERSION = "@@VERSION@@";

    public static final String PROXY_CLIENT = "cabralrodrigo.mc.mods.jarm.client.proxy.ClientProxy";
    public static final String PROXY_SERVER = "cabralrodrigo.mc.mods.jarm.common.proxy.CommonProxy";
    public static final String DEPENDECIES = "after:JEI";

    public static String bindModId(char separator, String sufix) {
        return MOD_ID + separator + sufix;
    }
}
