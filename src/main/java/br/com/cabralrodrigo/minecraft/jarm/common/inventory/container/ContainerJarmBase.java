package br.com.cabralrodrigo.minecraft.jarm.common.inventory.container;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.InventoryItemBase;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.slot.SlotDisabled;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.util.IContainerSalvable;
import br.com.cabralrodrigo.minecraft.jarm.common.util.IInventoryNBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerJarmBase extends Container implements IContainerSalvable {

    protected int COLUMNS, ROWS;
    protected int CONTAINER_START, CONTAINER_END;
    protected int PLAYER_MAIN_START, PLAYER_MAIN_END;
    protected int PLAYER_BAR_START, PLAYER_BAR_END;
    protected EntityPlayer player;
    protected IInventoryNBT inventory;

    public ContainerJarmBase(EntityPlayer player, IInventoryNBT inventory, int yInventoryPlayer) {
        this.COLUMNS = inventory instanceof InventoryItemBase ? ((InventoryItemBase) inventory).getColumnCount() : -1;
        this.ROWS = inventory instanceof InventoryItemBase ? ((InventoryItemBase) inventory).getRowCount() : -1;
        this.CONTAINER_START = 0;
        this.CONTAINER_END = (inventory instanceof InventoryItemBase ? this.COLUMNS * this.ROWS : inventory.getSizeInventory()) - 1;
        this.PLAYER_MAIN_START = this.CONTAINER_END + 1;
        this.PLAYER_MAIN_END = this.PLAYER_MAIN_START + ((3 * 9) - 1);
        this.PLAYER_BAR_START = this.PLAYER_MAIN_END + 1;
        this.PLAYER_BAR_END = this.PLAYER_BAR_START + 9 - 1;

        this.player = player;
        this.inventory = inventory;

        this.bindContainerInventory();
        this.bindPlayerInventory(yInventoryPlayer);
    }

    protected static void saveInventoryOnItemStack(IInventoryNBT inventory, ItemStack stackToSaveOn) {
        if (stackToSaveOn != null)
            inventory.serializeIntoItemStack(stackToSaveOn);
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public IInventory getJarmInventory() {
        return this.inventory;
    }

    protected void bindPlayerInventory(int yInventoryPlayer) {
        for (int row = 0; row < 3; ++row)
            for (int column = 0; column < 9; column++)
                this.addSlotToContainer(new Slot(this.player.inventory, column + row * 9 + 9, 8 + column * 18, yInventoryPlayer + (row * 18)));

        for (int column = 0; column < 9; column++)
            if (column == this.getDisabledSlotOnHotBar())
                this.addSlotToContainer(new SlotDisabled(this.player.inventory, column, 8 + column * 18, yInventoryPlayer + 58));
            else
                this.addSlotToContainer(new Slot(this.player.inventory, column, 8 + column * 18, yInventoryPlayer + 58));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(slotIndex);

        if ((slot != null) && (slot.getHasStack())) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slotIndex <= this.CONTAINER_END) {
                if (!mergeItemStack(itemstack1, this.PLAYER_MAIN_START, this.PLAYER_BAR_END + 1, true)) {
                    return null;
                }
            } else if (!mergeItemStack(itemstack1, this.CONTAINER_START, this.CONTAINER_END + 1, false)) {
                return null;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(null);
                return null;
            }

            slot.onSlotChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack slotClick(int targetSlot, int eventID, ClickType clickType, EntityPlayer player) {
        if (targetSlot == this.getDisabledSlotOnHotBar() + this.PLAYER_BAR_START || (clickType == ClickType.SWAP && eventID == this.getDisabledSlotOnHotBar()))
            return null;

        return super.slotClick(targetSlot, eventID, clickType, player);
    }

    protected abstract void bindContainerInventory();

    protected abstract int getDisabledSlotOnHotBar();
}
