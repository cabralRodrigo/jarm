package br.com.cabralrodrigo.minecraft.jarm.common.item.misc;

import br.com.cabralrodrigo.minecraft.jarm.common.dimension.TeleporterUmbral;
import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibDimensions;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemOrbOfSin extends ItemJarmBase {
    public ItemOrbOfSin() {
        super(LibItems.ORB_OF_SIN);
        this.setHasSubtypes(false);
        this.setNoRepair();
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 3 * 20;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (player.dimension != LibDimensions.UMBRAL_ID) {
            if (!world.isRemote) {
                world.playSoundAtEntity(player, "ambient.weather.thunder", 10000F, .8F);
                world.playSoundAtEntity(player, "random.explode", 10000F, .5F);
                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, LibDimensions.UMBRAL_ID, new TeleporterUmbral((WorldServer) world));
            }
            return null;
        } else {
            if (!world.isRemote)
                player.addChatMessage(new ChatComponentText(this.translateForItem("cannot_teleport")));
        }

        return stack;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));

        return itemStack;
    }

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if (event.entity instanceof EntityWither)
            event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, new ItemStack(ModItems.orb_of_sin, 1)));
    }
}
