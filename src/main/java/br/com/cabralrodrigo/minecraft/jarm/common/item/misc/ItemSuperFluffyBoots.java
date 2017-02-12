package br.com.cabralrodrigo.minecraft.jarm.common.item.misc;

import br.com.cabralrodrigo.minecraft.jarm.client.lib.LibItemTextures;
import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibVanilla;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IRegistrable;
import br.com.cabralrodrigo.minecraft.jarm.common.util.Translator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSuperFluffyBoots extends ItemArmor implements IRegistrable {

    public ItemSuperFluffyBoots() {
        super(LibVanilla.ARMOR_MATERIAL_FLUFFY, 1, 3);
        this.setUnlocalizedName(LibMod.bindModId(':', this.getName()));
        this.setCreativeTab(Jarm.creativeTab);
        this.setMaxDamage(-1);
        this.setNoRepair();
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return LibItemTextures.FLUFFY_ARMOR;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(Translator.translate("item", this.getName(), "tooltip"));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            if (this.isEntityWearingBoots(event.entityLiving))
                event.distance = 0F;
    }

    private boolean isEntityWearingBoots(EntityLivingBase entity) {
        if (entity != null && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack boots = player.getCurrentArmor(LibVanilla.ArmorSlots.BOOTS);
            if (boots != null && boots.getItem() == this)
                return true;
        }

        return false;
    }

    @Override
    public String getName() {
        return LibItems.SUPER_FLUFFY_BOOTS;
    }
}
