package cabralrodrigo.mc.mods.jarm.common.item.amulet;

import cabralrodrigo.mc.mods.jarm.common.item.ItemAmuletBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
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

            String translatedEntityName = StatCollector.translateToLocal("entity." + getEntityString(stack) + ".name");
            tooltip.add(this.translateForItem("tooltip.entity_name", translatedEntityName));
        } else
            tooltip.add(this.translateForItem("tooltip.pickup"));

    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (hasEntity(stack)) {
            if (!world.isRemote) {
                BlockPos entityPos = pos.offset(side);

                Entity entity = EntityList.createEntityByName(getEntityString(stack), world);
                entity.readFromNBT(getEntityInformation(stack));
                entity.setLocationAndAngles(entityPos.getX(), entityPos.getY(), entityPos.getZ(), entity.rotationYaw, entity.rotationPitch);

                world.spawnEntityInWorld(entity);

                if (!player.capabilities.isCreativeMode)
                    removeEntity(stack);
            }
            return true;
        } else
            return false;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return hasEntity(stack);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
        if (!hasEntity(stack) && target instanceof EntityLiving && !(target instanceof IBossDisplayData)) {
            if (!player.worldObj.isRemote) {
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
