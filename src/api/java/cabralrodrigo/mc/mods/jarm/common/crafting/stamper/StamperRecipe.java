package cabralrodrigo.mc.mods.jarm.common.crafting.stamper;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class StamperRecipe {

    private final int requiredLevel;
    private final ItemStack output;
    private final Object base;
    private final Object[] inputs = new Object[3];

    public StamperRecipe(ItemStack output, int requiredLevel, Object base, Object... inputs) {
        this.output = output;
        this.requiredLevel = requiredLevel;
        this.base = base;

        for (int i = 0; i < this.inputs.length; i++)
            this.inputs[i] = inputs[i];
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public boolean canCraft(EntityPlayer player, ItemStack base, ItemStack[] inputs) {
        if (player.experienceLevel < this.requiredLevel)
            return false;

        if (base == null || inputs == null)
            return false;

        if (inputs.length != this.inputs.length)
            return false;

        if (!this.isEqualToItemStack(base, this.base))
            return false;

        for (int i = 0; i < this.inputs.length; i++)
            if (!this.isEqualToItemStack(inputs[i], this.inputs[i]))
                return false;

        return true;
    }

    private boolean isEqualToItemStack(ItemStack stack, Object value) {
        if (value instanceof Item || value instanceof ItemStack) {
            if (stack.getItem() != value)
                return false;
        } else if (this.base instanceof Block) {
            if (stack.getItem() != ItemBlock.getItemFromBlock((Block) value))
                return false;
        } else
            return false;

        return true;
    }
}
