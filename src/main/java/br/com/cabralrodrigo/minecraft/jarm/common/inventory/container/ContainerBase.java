package br.com.cabralrodrigo.minecraft.jarm.common.inventory.container;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.InventoryBaseNew;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.slot.SlotDisabled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerBase extends Container {

    protected final EntityPlayer player;
    protected final InventoryBaseNew inventory;
    protected final int disabledSlot;
    private final int CONTAINER_END, CONTAINER_START;
    private final int PLAYER_BAR_START, PLAYER_BAR_END;
    private final int PLAYER_MAIN_START, PLAYER_MAIN_END;

    public ContainerBase(EntityPlayer player, InventoryBaseNew inventory, int yInventoryPlayer, int disabledSlot) {
        this.player = player;
        this.inventory = inventory;
        this.disabledSlot = disabledSlot;

        this.CONTAINER_START = 0;
        this.CONTAINER_END = inventory.getSlots() - 1;
        this.PLAYER_MAIN_START = this.CONTAINER_END + 1;
        this.PLAYER_MAIN_END = this.PLAYER_MAIN_START + ((3 * 9) - 1);
        this.PLAYER_BAR_START = this.PLAYER_MAIN_END + 1;
        this.PLAYER_BAR_END = this.PLAYER_BAR_START + 9 - 1;

        this.bindContainerInventory();
        this.bindPlayerInventory(yInventoryPlayer);
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public String getInventoryName() {
        return this.inventory.getName();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex) {
        Slot slot = this.getSlot(slotIndex);

        if (slot == null || !slot.getHasStack())
            return ItemStack.EMPTY;

        ItemStack stack = slot.getStack();
        ItemStack newStack = stack.copy();

        boolean slotInContainer = slotIndex >= this.CONTAINER_START && slotIndex <= this.CONTAINER_END;
        boolean slotInPlayerMain = slotIndex >= this.PLAYER_MAIN_START && slotIndex <= this.PLAYER_MAIN_END;
        boolean slotInHotbar = slotIndex >= this.PLAYER_BAR_START && slotIndex <= this.PLAYER_BAR_END;

        if (slotInContainer) { // Container -> Player

            //Container -> Player Bar
            if (!this.mergeItemStack(stack, this.PLAYER_BAR_START, this.PLAYER_BAR_END + 1, false))

                //Container -> Player Main
                if (!this.mergeItemStack(stack, this.PLAYER_MAIN_START, this.PLAYER_MAIN_END + 1, false))
                    return ItemStack.EMPTY;

        } else { // Player -> Container

            //Player -> Container
            if (!this.mergeItemStack(stack, this.CONTAINER_START, this.CONTAINER_END + 1, false)) {

                if (slotInPlayerMain) { //Slot in Player Main

                    //Player Min -> Player Hotbar
                    if (!this.mergeItemStack(stack, this.PLAYER_BAR_START, this.PLAYER_BAR_END + 1, false))
                        return ItemStack.EMPTY;

                } else if (slotInHotbar) { //Slot in Player Horbar

                    //Player hotbar -> Player Main
                    if (!this.mergeItemStack(stack, this.PLAYER_MAIN_START, this.PLAYER_MAIN_END + 1, false))
                        return ItemStack.EMPTY;
                }
            }
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
            return ItemStack.EMPTY;
        }

        slot.onSlotChanged();

        return newStack;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
        if (this.disabledSlot >= 0) {
            boolean clickOnDisabled = slotId == this.disabledSlot + this.PLAYER_BAR_START;
            boolean tryCloning = clickType == ClickType.CLONE;
            boolean swapOnDisabled = clickType == ClickType.SWAP && dragType == this.disabledSlot;

            if ((clickOnDisabled && !tryCloning) || swapOnDisabled)
                return ItemStack.EMPTY;
        }

        return super.slotClick(slotId, dragType, clickType, player);
    }

    private void bindPlayerInventory(int yInventoryPlayer) {
        for (int row = 0; row < 3; ++row)
            for (int column = 0; column < 9; column++)
                this.addSlotToContainer(new Slot(this.player.inventory, column + row * 9 + 9, 8 + column * 18, yInventoryPlayer + (row * 18)));

        for (int column = 0; column < 9; column++)
            if (column == this.disabledSlot)
                this.addSlotToContainer(new SlotDisabled(this.player.inventory, column, 8 + column * 18, yInventoryPlayer + 58));
            else
                this.addSlotToContainer(new Slot(this.player.inventory, column, 8 + column * 18, yInventoryPlayer + 58));
    }

    protected abstract void bindContainerInventory();
}
