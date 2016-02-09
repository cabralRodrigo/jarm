package cabralrodrigo.mc.mods.jarm.common.inventory.slot;

import cabralrodrigo.mc.mods.jarm.common.inventory.util.IContainerSalvable;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotBlacklist extends Slot {

    private Item[] blackList;
    private Container container;

    public SlotBlacklist(Container container, IInventory inventory, int slot, int x, int y, Item[] blackList) {
        super(inventory, slot, x, y);
        this.blackList = blackList;
        this.container = container;
    }

    @Override
    public void onSlotChange(ItemStack stack1, ItemStack stack2) {
        super.onSlotChange(stack1, stack2);
        if (this.container instanceof IContainerSalvable)
            ((IContainerSalvable) this.container).save();
    }

    @Override
    public void onSlotChanged() {
        super.onSlotChanged();
        if (this.container instanceof IContainerSalvable)
            ((IContainerSalvable) this.container).save();
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack != null)
            for (Item item : this.blackList)
                if (stack.getItem() == item)
                    return false;

        return true;
    }
}
