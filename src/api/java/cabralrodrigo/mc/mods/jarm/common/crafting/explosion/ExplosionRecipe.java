package cabralrodrigo.mc.mods.jarm.common.crafting.explosion;

import cabralrodrigo.mc.mods.jarm.common.util.ItemHelper;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;

import java.util.ArrayList;
import java.util.List;

public class ExplosionRecipe {

    protected List<AffectedExplosionObject> inputs = new ArrayList<AffectedExplosionObject>();
    protected List<AffectedExplosionObject> outputs = new ArrayList<AffectedExplosionObject>();

    public boolean canCraftOnExplosion() {
        return true;
    }

    public ExplosionRecipe addInputItemStack(ItemStack... itemStacks) {
        if (itemStacks != null)
            for (ItemStack stack : itemStacks)
                if (stack != null)
                    this.inputs.add(new AffectedExplosionObject(stack));

        return this;
    }

    public ExplosionRecipe addInputItems(Item... items) {
        if (items != null)
            for (Item item : items)
                if (item != null)
                    this.inputs.add(new AffectedExplosionObject(item));

        return this;
    }

    public ExplosionRecipe addInputBlocks(Block... blocks) {
        if (blocks != null)
            for (Block block : blocks)
                if (block != null)
                    this.inputs.add(new AffectedExplosionObject(block));

        return this;
    }

    public <T extends Entity> ExplosionRecipe addInputEntities(Class<T>... entities) {
        if (entities != null)
            for (Class entity : entities)
                if (entity != null)
                    this.inputs.add(new AffectedExplosionObject(entity));

        return this;
    }

    public ExplosionRecipe addOutputItemStack(ItemStack... itemStacks) {
        if (itemStacks != null)
            for (ItemStack stack : itemStacks)
                if (stack != null)
                    this.outputs.add(new AffectedExplosionObject(stack));

        return this;
    }

    public ExplosionRecipe addOutputItems(Item... items) {
        if (items != null)
            for (Item item : items)
                if (item != null)
                    this.outputs.add(new AffectedExplosionObject(item));

        return this;
    }

    public <T extends Entity> ExplosionRecipe addOutputEntities(Class<T>... entities) {
        if (entities != null)
            for (Class entity : entities)
                if (entity != null)
                    this.outputs.add(new AffectedExplosionObject(entity));

        return this;
    }

    public ImmutableList<AffectedExplosionObject> getInputs() {
        return ImmutableList.copyOf(this.inputs);
    }

    public ImmutableList<AffectedExplosionObject> getOutputs() {
        return ImmutableList.copyOf(this.outputs);
    }

    public boolean tryCraft(ExplosionEvent.Detonate event) {
        if (this.canCraft(event)) {
            for (AffectedExplosionObject object : this.inputs)
                object.affectExplosionResults(event, false);

            for (AffectedExplosionObject object : this.outputs) {
                if (object.isEntity()) {
                    try {
                        Entity entity = (Entity) ((Class) object.getValue()).getConstructor(new Class[]{World.class}).newInstance(new Object[]{event.world});
                        entity.setLocationAndAngles(event.explosion.getPosition().xCoord, event.explosion.getPosition().yCoord, event.explosion.getPosition().zCoord, 0, 0);
                        event.world.spawnEntityInWorld(entity);

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else if (object.isItemStack()) {
                    ItemHelper.spawnItemStack(event.world, new BlockPos(event.explosion.getPosition()), ((ItemStack) object.getValue()).copy());
                } else {
                    Item itemToSpawn = object.isItem() ? (Item) object.getValue() : ItemBlock.getItemFromBlock((Block) object.getValue());
                    ItemStack stackToSpawn = new ItemStack(itemToSpawn);

                    ItemHelper.spawnItemStack(event.world, new BlockPos(event.explosion.getPosition()), stackToSpawn);
                }
            }
            return true;
        } else
            return false;
    }

    private boolean canCraft(ExplosionEvent.Detonate event) {
        for (AffectedExplosionObject object : this.inputs)
            if (!object.affectExplosionResults(event, true))
                return false;

        return true;
    }
}
