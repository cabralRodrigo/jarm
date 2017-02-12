package br.com.cabralrodrigo.minecraft.jarm.api.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public final class ItemHelper {
    private static Random rand = new Random();

    public static void spawnItemStack(World worldIn, BlockPos pos, ItemStack stack) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.8F + 0.1F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;

        while (stack.getCount() > 0) {
            int i = rand.nextInt(21) + 10;

            if (i > stack.getCount()) {
                i = stack.getCount();
            }

            stack.shrink(i);
            EntityItem entityitem = new EntityItem(worldIn, x + (double) f, y + (double) f1, z + (double) f2, new ItemStack(stack.getItem(), i, stack.getMetadata()));

            if (stack.hasTagCompound())
                entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());

            float f3 = 0.05F;
            entityitem.motionX = rand.nextGaussian() * (double) f3;
            entityitem.motionY = rand.nextGaussian() * (double) f3 + 0.20000000298023224D;
            entityitem.motionZ = rand.nextGaussian() * (double) f3;
            worldIn.spawnEntity(entityitem);
        }
    }
}
