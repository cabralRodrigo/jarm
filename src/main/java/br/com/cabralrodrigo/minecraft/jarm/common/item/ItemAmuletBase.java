package br.com.cabralrodrigo.minecraft.jarm.common.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemAmuletBase extends ItemJarmBase {
    protected ItemAmuletBase(String name) {
        super(name);
        this.setHasSubtypes(false);
        this.setNoRepair();
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }
}
