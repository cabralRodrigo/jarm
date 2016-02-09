package cabralrodrigo.mc.mods.jarm.common.inventory.slot;

import cabralrodrigo.mc.mods.jarm.common.inventory.util.IContainerSalvable;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWhitelist extends Slot {

    private Container container;
    private Class[] whiteList;

    public SlotWhitelist(Container container, IInventory inventory, int slot, int x, int y, Class... whiteList) {
        super(inventory, slot, x, y);
        this.whiteList = whiteList;
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
            for (Class item : this.whiteList)
                if (item.isInstance(stack.getItem()))
                    return true;

        return false;
    }
}
