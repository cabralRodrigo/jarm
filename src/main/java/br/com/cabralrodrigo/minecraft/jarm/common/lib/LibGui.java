package br.com.cabralrodrigo.minecraft.jarm.common.lib;


import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class LibGui {
    public static final int AMULET_STORAGE = 0;
    public static final int ENDER_ENCHANTMENT_TABLE = 1;
    public static final int SEED_BAG = 2;
    public static final int AMULET_POTION = 3;
    public static final int AMULET_STAMPER = 4;

    public static final Set<Integer> BlockPosOnGuiOpen = ImmutableSet.of(
            ENDER_ENCHANTMENT_TABLE,
            AMULET_STAMPER
    );
}
