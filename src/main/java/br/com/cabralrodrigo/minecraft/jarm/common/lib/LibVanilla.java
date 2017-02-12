package br.com.cabralrodrigo.minecraft.jarm.common.lib;

import br.com.cabralrodrigo.minecraft.jarm.common.dimension.WorldProviderUmbral;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.util.EnumHelper;

public final class LibVanilla {
    public static final ArmorMaterial ARMOR_MATERIAL_FLUFFY = EnumHelper.addArmorMaterial("FLUFFY", "", -1, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);

    public static final DimensionType DIMENSION_TYPE_UMBRAL = EnumHelper.addEnum(DimensionType.class, "UMBRAL", new Class[]{
            int.class,
            String.class,
            String.class,
            Class.class
    }, new Object[]{
            LibDimensions.UMBRAL_ID,
            "Umbral",
            "_umbral",
            WorldProviderUmbral.class
    });

    public static final class ArmorSlots {
        public static final int HEMELT = 3;
        public static final int CHEST = 2;
        public static final int LEGS = 1;
        public static final int BOOTS = 0;
    }
}
