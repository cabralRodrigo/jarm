package br.com.cabralrodrigo.minecraft.jarm.api.explosion;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.oredict.OreDictionary;

public class AffectedExplosionObject {

    private Object value;

    public AffectedExplosionObject(ItemStack stack) {
        this((Object) stack);
    }

    public AffectedExplosionObject(Item item) {
        this((Object) item);
    }

    public AffectedExplosionObject(Block block) {
        this((Object) block);
    }

    public <T extends Entity> AffectedExplosionObject(Class<T> entityClass) {
        this((Object) entityClass);
    }

    private AffectedExplosionObject(Object value) {
        this.value = value;
    }

    public boolean affectExplosionResults(ExplosionEvent.Detonate event, boolean simulate) {
        if (this.isBlock()) {
            for (BlockPos pos : event.getAffectedBlocks()) {
                Block block = event.getWorld().getBlockState(pos).getBlock();

                if (block == this.getValue()) {
                    if (!simulate)
                        event.getWorld().setBlockToAir(pos);
                    return true;
                }
            }

        } else if (this.isEntity()) {
            Class entityClass = (Class) this.getValue();

            for (Entity entity : event.getAffectedEntities())
                if (!entity.isDead)
                    if (entityClass.isInstance(entity)) {
                        if (!simulate)
                            entity.setDead();
                        return true;
                    }

        } else if (this.isItem()) {
            for (Entity entity : event.getAffectedEntities())
                if (!entity.isDead)
                    if (entity instanceof EntityItem) {
                        ItemStack stack = ((EntityItem) entity).getEntityItem();
                        if (stack != null && stack.getItem() != null)
                            if (stack.getItem() == this.getValue())
                                if (stack.getCount() > 0) {
                                    if (!simulate) {
                                        stack.shrink(1);

                                        if (stack.getCount() < 1)
                                            entity.setDead();
                                    }

                                    return true;
                                }
                    }

        } else if (this.isItemStack()) {
            ItemStack stackValue = (ItemStack) this.getValue();
            for (Entity entity : event.getAffectedEntities())
                if (!entity.isDead)
                    if (entity instanceof EntityItem) {
                        ItemStack stack = ((EntityItem) entity).getEntityItem();

                        if (stack.getCount() < stackValue.getCount())
                            continue;

                        if (stack.getItem() != stackValue.getItem())
                            continue;

                        if (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() != stackValue.getItemDamage())
                            continue;

                        if (stack.getTagCompound() != null && stack.getTagCompound() != null)
                            if (!stack.getTagCompound().equals(stackValue.getTagCompound()))
                                continue;

                        if (stack.getTagCompound() == null && stackValue.getTagCompound() != null)
                            continue;

                        if (!simulate) {
                            stack.shrink(stackValue.getCount());

                            if (stack.getCount() <= 0)
                                entity.setDead();
                        }

                        return true;
                    }
        }

        return false;
    }

    public Object getValue() {
        return this.value;
    }

    public boolean isItemStack() {
        return this.getValue() instanceof ItemStack;
    }

    public boolean isItem() {
        return this.getValue() instanceof Item;
    }

    public boolean isBlock() {
        return this.getValue() instanceof Block;
    }

    public boolean isEntity() {
        return this.getValue() instanceof Class ? Entity.class.isAssignableFrom((Class) this.getValue()) : false;
    }
}
