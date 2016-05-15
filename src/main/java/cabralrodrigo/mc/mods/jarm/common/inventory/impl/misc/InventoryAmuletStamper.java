package cabralrodrigo.mc.mods.jarm.common.inventory.impl.misc;

import cabralrodrigo.mc.mods.jarm.common.inventory.impl.InventoryBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibBlocks;
import net.minecraft.item.ItemStack;

public class InventoryAmuletStamper extends InventoryBase {
    private static final int SLOT_INDEX_PLATE = 0;
    private static final int[] SLOT_INDEX_ITEMS = new int[]{1, 2, 3};
    private static final int SLOT_INDEX_OUTPUT = 4;

    public InventoryAmuletStamper() {
        super(LibBlocks.AMULET_STAMPER, 5);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == SLOT_INDEX_OUTPUT)
            return false;
        else
            return super.isItemValidForSlot(index, stack);
    }

    public void setOutputItemStack(ItemStack stack) {
        this.setInventorySlotContents(SLOT_INDEX_OUTPUT, stack);
    }

    public ItemStack getPlateItemStack() {
        return this.getStackInSlot(SLOT_INDEX_PLATE);
    }

    public ItemStack[] getInputItems() {
        ItemStack[] inputs = new ItemStack[SLOT_INDEX_ITEMS.length];
        for (int i = 0; i < SLOT_INDEX_ITEMS.length; i++)
            inputs[i] = this.getStackInSlot(SLOT_INDEX_ITEMS[i]);

        return inputs;
    }
}
