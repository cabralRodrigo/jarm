package cabralrodrigo.mc.mods.jarm.common.item.misc;

import cabralrodrigo.mc.mods.jarm.client.lib.LibItemTextures;
import cabralrodrigo.mc.mods.jarm.common.Jarm;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import cabralrodrigo.mc.mods.jarm.common.lib.LibMod;
import cabralrodrigo.mc.mods.jarm.common.lib.LibVanilla;
import cabralrodrigo.mc.mods.jarm.common.registry.util.IRegistrable;
import cabralrodrigo.mc.mods.jarm.common.util.TextHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemSuperFluffyChestplate extends ItemArmor implements IRegistrable {
    private static ArrayList<String> playersWithFlight = new ArrayList<String>();

    public ItemSuperFluffyChestplate() {
        super(LibVanilla.ARMOR_MATERIAL_FLUFFY, 1, 1);
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
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TextHelper.translate("item", this.getName(), "tooltip"));
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
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = ((EntityPlayer) event.entityLiving);

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
        return player.getUniqueID().toString() + ":" + player.worldObj.isRemote;
    }

    private boolean isEntityWearingChestplate(EntityPlayer player) {
        ItemStack chestplate = player.getCurrentArmor(LibVanilla.ArmorSlots.CHEST);
        if (chestplate != null && chestplate.getItem() == this)
            return true;
        else
            return false;
    }
}
