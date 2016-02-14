package cabralrodrigo.mc.mods.jarm.common.inventory.container;

import cabralrodrigo.mc.mods.jarm.common.inventory.impl.InventoryItemBase;
import cabralrodrigo.mc.mods.jarm.common.inventory.slot.SlotDisabled;
import cabralrodrigo.mc.mods.jarm.common.inventory.util.IContainerSalvable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ContainerJarmBase extends Container implements IContainerSalvable {

    protected int COLUMNS, ROWS;
    protected int CONTAINER_START, CONTAINER_END;
    protected int PLAYER_MAIN_START, PLAYER_MAIN_END;
    protected int PLAYER_BAR_START, PLAYER_BAR_END;
    protected EntityPlayer player;
    protected InventoryItemBase inventory;

    public ContainerJarmBase(EntityPlayer player, InventoryItemBase inventory, int yInventoryPlayer) {
        this.COLUMNS = inventory.getColumnCount();
        this.ROWS = inventory.getRowCount();
        this.CONTAINER_START = 0;
        this.CONTAINER_END = (COLUMNS * ROWS) - 1;
        this.PLAYER_MAIN_START = CONTAINER_END + 1;
        this.PLAYER_MAIN_END = PLAYER_MAIN_START + ((3 * 9) - 1);
        this.PLAYER_BAR_START = PLAYER_MAIN_END + 1;
        this.PLAYER_BAR_END = PLAYER_BAR_START + 9 - 1;

        this.player = player;
        this.inventory = inventory;

        this.bindContainerInventory();
        this.bindPlayerInventory(yInventoryPlayer);
    }

    protected static void saveInventoryOnItemStack(InventoryItemBase inventory, ItemStack stackToSaveOn) {
        if (stackToSaveOn != null) {
            NBTTagCompound nbtStack = stackToSaveOn.getTagCompound();
            if (nbtStack == null)
                nbtStack = new NBTTagCompound();

            inventory.writeToNBT(nbtStack);
            stackToSaveOn.setTagCompound(nbtStack);
        }
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public InventoryItemBase getJarmInventory() {
        return inventory;
    }

    protected void bindPlayerInventory(int yInventoryPlayer) {
        for (int row = 0; row < 3; ++row)
            for (int column = 0; column < 9; column++)
                this.addSlotToContainer(new Slot(player.inventory, column + row * 9 + 9, 8 + column * 18, yInventoryPlayer + (row * 18)));

        for (int column = 0; column < 9; column++)
            if (column == this.getDisabledSlotOnHotBar())
                this.addSlotToContainer(new SlotDisabled(player.inventory, column, 8 + column * 18, yInventoryPlayer + 58));
            else
                this.addSlotToContainer(new Slot(player.inventory, column, 8 + column * 18, yInventoryPlayer + 58));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(slotIndex);

        if ((slot != null) && (slot.getHasStack())) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotIndex <= CONTAINER_END) {
                if (!mergeItemStack(itemstack1, PLAYER_MAIN_START, PLAYER_BAR_END + 1, true)) {
                    return null;
                }
            } else if (!mergeItemStack(itemstack1, CONTAINER_START, CONTAINER_END + 1, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
                return null;
            }

            slot.onSlotChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack slotClick(int targetSlot, int eventID, int flag, EntityPlayer player) {
        if (targetSlot == this.getDisabledSlotOnHotBar() + PLAYER_BAR_START || (flag == 2 && eventID == this.getDisabledSlotOnHotBar()))
            return null;

        return super.slotClick(targetSlot, eventID, flag, player);
    }

    protected abstract void bindContainerInventory();

    protected abstract int getDisabledSlotOnHotBar();
}
