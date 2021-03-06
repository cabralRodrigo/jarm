package br.com.cabralrodrigo.minecraft.jarm.common.item.misc;

import br.com.cabralrodrigo.minecraft.jarm.client.lib.LibItemTextures;
import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibMod;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibVanilla;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.util.IRegistrable;
import br.com.cabralrodrigo.minecraft.jarm.common.util.Translator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemSuperFluffyChestplate extends ItemArmor implements IRegistrable {
    private static ArrayList<String> playersWithFlight = new ArrayList<String>();

    public ItemSuperFluffyChestplate() {
        super(LibVanilla.ARMOR_MATERIAL_FLUFFY, 1, EntityEquipmentSlot.CHEST);
        this.setUnlocalizedName(LibMod.bindModId(':', this.getName()));
        this.setRegistryName(LibMod.MOD_ID, this.getName());
        this.setCreativeTab(Jarm.creativeTab);
        this.setMaxDamage(-1);
        this.setNoRepair();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return LibItemTextures.FLUFFY_ARMOR;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(Translator.translate("item", this.getName(), "tooltip"));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public String getName() {
        return LibItems.SUPER_FLUFFY_CHESTPLATE;
    }

    @SubscribeEvent
    public void omLivingUpdate(LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = ((EntityPlayer) event.getEntityLiving());

            if (playersWithFlight.contains(playerString(player))) {
                if (isEntityWearingChestplate(player)) {
                    player.capabilities.allowFlying = true;
                    player.capabilities.setFlySpeed(0.15F);
                } else {
                    if (!player.capabilities.isCreativeMode) {
                        player.capabilities.allowFlying = false;
                        player.capabilities.isFlying = false;
                        player.capabilities.disableDamage = false;
                        player.capabilities.setFlySpeed(0.05F);
                    }
                    playersWithFlight.remove(playerString(player));
                }
            } else {
                if (isEntityWearingChestplate(player)) {
                    playersWithFlight.add(playerString(player));
                    player.capabilities.allowFlying = true;
                    player.capabilities.setFlySpeed(0.15F);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        playersWithFlight.remove(event.player.getUniqueID() + ":true");
        playersWithFlight.remove(event.player.getUniqueID() + ":false");
    }

    private String playerString(EntityPlayer player) {
        return player.getUniqueID().toString() + ":" + player.world.isRemote;
    }

    private boolean isEntityWearingChestplate(EntityPlayer player) {
        for (ItemStack stack : player.getArmorInventoryList())
            if (stack != null && !stack.isEmpty() && stack.getItem() == this)
                return true;

        return false;
    }
}