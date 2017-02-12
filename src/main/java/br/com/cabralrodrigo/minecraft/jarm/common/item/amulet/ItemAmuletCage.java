package br.com.cabralrodrigo.minecraft.jarm.common.item.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemAmuletBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import br.com.cabralrodrigo.minecraft.jarm.common.util.Translator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemAmuletCage extends ItemAmuletBase {
    public ItemAmuletCage() {
        super(LibItems.AMULET_CAGE);
    }

    private static String getEntityString(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null)
            if (nbt.hasKey("entity_string"))
                return nbt.getString("entity_string");

        return null;
    }

    private static NBTTagCompound getEntityInformation(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null)
            if (nbt.hasKey("entity_info", 10))
                return nbt.getCompoundTag("entity_info");

        return null;
    }

    private static void setEntity(ItemStack stack, EntityLiving entity) {
        NBTTagCompound entityNbt = new NBTTagCompound();
        entity.writeToNBT(entityNbt);

        NBTTagCompound stackNbt = stack.getTagCompound();
        if (stackNbt == null)
            stackNbt = new NBTTagCompound();

        stackNbt.setTag("entity_info", entityNbt);
        stackNbt.setString("entity_string", EntityList.getEntityString(entity));

        stack.setTagCompound(stackNbt);
    }

    private static void removeEntity(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null)
            nbt = new NBTTagCompound();

        nbt.removeTag("entity_info");
        nbt.removeTag("entity_string");
        stack.setTagCompound(nbt);
    }

    private static boolean hasEntity(ItemStack stack) {
        return getEntityInformation(stack) != null && getEntityString(stack) != null;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (hasEntity(stack)) {
            tooltip.add(this.translateForItem("tooltip.release"));
            tooltip.add(this.translateForItem("tooltip.entity_name", Translator.translateEntityName(getEntityString(stack))));
        } else
            tooltip.add(this.translateForItem("tooltip.pickup"));

    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        if (hasEntity(stack)) {
            if (!world.isRemote) {
                BlockPos entityPos = pos.offset(facing);

                Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(getEntityString(stack)), world);

                entity.readFromNBT(getEntityInformation(stack));
                entity.setLocationAndAngles(entityPos.getX(), entityPos.getY(), entityPos.getZ(), entity.rotationYaw, entity.rotationPitch);

                world.spawnEntity(entity);

                if (!player.capabilities.isCreativeMode)
                    removeEntity(stack);
            }
            return EnumActionResult.SUCCESS;
        } else
            return EnumActionResult.FAIL;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return hasEntity(stack);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        if (!hasEntity(stack) && target instanceof EntityLiving && target.isNonBoss()) {
            if (!player.world.isRemote) {
                if (!((EntityLiving) target).isDead) {
                    setEntity(stack, (EntityLiving) target);
                    target.setDead();
                    return true;
                } else
                    return false;
            } else
                return true;
        } else
            return false;
    }
}
