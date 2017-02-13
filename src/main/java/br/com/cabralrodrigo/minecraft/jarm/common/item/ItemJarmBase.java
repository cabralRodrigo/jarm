package br.com.cabralrodrigo.minecraft.jarm.common.item;

import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IRegistrable;
import br.com.cabralrodrigo.minecraft.jarm.common.util.EnumHandHelper;
import br.com.cabralrodrigo.minecraft.jarm.common.util.Translator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class ItemJarmBase extends Item implements IRegistrable {

    private String name;
    private boolean hasDefaultTooltip;
    private int defaultDisplayDamage;

    protected ItemJarmBase(String name) {
        this.name = name;
        this.setHasDefaultTooltip(true);
        this.setDefaultDisplayDamage(0);
        this.setUnlocalizedName(LibMod.bindModId(':', this.getName()));
        this.setRegistryName(LibMod.MOD_ID, this.getName());
        this.setCreativeTab(Jarm.creativeTab);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        if (this.getHasDefaultTooltip())
            tooltip.add(this.translateForItem("tooltip"));
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        subItems.add(new ItemStack(this, 1, this.getDefaultDisplayDamage()));
    }

    protected boolean getHasDefaultTooltip() {
        return this.hasDefaultTooltip;
    }

    protected void setHasDefaultTooltip(boolean hasTooltip) {
        this.hasDefaultTooltip = hasTooltip;
    }

    protected int getDefaultDisplayDamage() {
        return defaultDisplayDamage;
    }

    protected void setDefaultDisplayDamage(int defaultDisplayDamage) {
        this.defaultDisplayDamage = defaultDisplayDamage;
    }

    @SideOnly(Side.CLIENT)
    protected String translateForItem(String suffix, Object... format) {
        return Translator.translate("item", this.getName(), suffix, format);
    }

    protected void openGui(EntityPlayer player, World world, int guidId, EnumHand hand) {
        if (!world.isRemote)
            player.openGui(Jarm.instance, guidId, world, EnumHandHelper.toInt(hand), 0, 0);
    }
}