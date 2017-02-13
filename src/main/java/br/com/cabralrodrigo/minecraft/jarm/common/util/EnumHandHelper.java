package br.com.cabralrodrigo.minecraft.jarm.common.util;

import net.minecraft.util.EnumHand;

public final class EnumHandHelper {
    public static int toInt(EnumHand hand) {
        if (hand == EnumHand.MAIN_HAND)
            return 1;
        else //OFF_HAND
            return 0;
    }

    public static EnumHand toEnum(int hand) {
        if (hand == 0)
            return EnumHand.OFF_HAND;
        else //1
            return EnumHand.MAIN_HAND;
    }
}
