package br.com.cabralrodrigo.minecraft.jarm.common.item.misc;

import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibDimensions;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
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
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            if (player.dimension != LibDimensions.UMBRAL_ID) {
                if (!world.isRemote) {
                    world.playSound(player, player.getPosition(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.PLAYERS, 10000F, .8F);
                    world.playSound(player, player.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 10000F, .8F);

                    player.changeDimension(LibDimensions.UMBRAL_ID);
                }
                return null;
            } else {
                if (!world.isRemote)
                    player.sendStatusMessage(new TextComponentString(this.translateForItem("cannot_teleport")), true);
            }

        }
        return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }


    @SubscribeEvent
    public void onLivingDrops2(LivingDropsEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof EntityWither) {
            BlockPos pos = entity.getPosition();
            ItemStack result = new ItemStack(ModItems.orb_of_sin, 1);
            EntityItem entityItem = new EntityItem(entity.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ(), result);

            event.getDrops().add(entityItem);
        }
    }
}
