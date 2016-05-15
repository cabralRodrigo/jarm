package cabralrodrigo.mc.mods.jarm.common.inventory.impl;

import cabralrodrigo.mc.mods.jarm.common.item.ItemJarmBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryItemBase extends InventoryBase {

    private final ItemStack stack;
    private Item itemContainer;
    private int rowCount;
    private int columnCount;

    public InventoryItemBase(ItemJarmBase itemContainer, ItemStack stackContainer, int rowCount, int columnCount) {
        super(itemContainer.getName(), rowCount * columnCount);
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.stack = stackContainer;
        this.itemContainer = itemContainer;

        if (this.stack != null && this.stack.getItem() == itemContainer)
            this.deserializeFromItemStack(this.stack);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (this.stack == null)
            return true;

        return this.stack.getItem() != this.itemContainer;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public int getRowCount() {
        return this.rowCount;
    }
}
