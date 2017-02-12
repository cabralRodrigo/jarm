package br.com.cabralrodrigo.minecraft.jarm.common.lib;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public final class LibVanilla {
    public static final ArmorMaterial ARMOR_MATERIAL_FLUFFY = EnumHelper.addArmorMaterial("FLUFFY", "", -1, new int[]{0, 0, 0, 0}, 0);

    public static final class ArmorSlots {
        public static final int HEMELT = 3;
        public static final int CHEST = 2;
        public static final int LEGS = 1;
        public static final int BOOTS = 0;
    }
}
