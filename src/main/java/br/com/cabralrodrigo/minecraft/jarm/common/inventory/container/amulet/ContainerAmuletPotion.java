package br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.ContainerJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet.InventoryAmuletPotion;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.slot.SlotPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class ContainerAmuletPotion extends ContainerJarmBase {
    private int amuletSlot;

    public ContainerAmuletPotion(EntityPlayer player, EnumHand hand) {
        super(player, new InventoryAmuletPotion(player.getHeldItem(hand)), 86);
        this.amuletSlot = player.inventory.currentItem;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        if (!player.world.isRemote)
            this.save();
    }

    @Override
    protected void bindContainerInventory() {
        for (int row = 0; row < ROWS; ++row)
            for (int column = 0; column < COLUMNS; column++)
                this.addSlotToContainer(new SlotPotion(this, this.inventory, column + row * 9, 8 + column * 18, 18 + row * 18, false));
    }

    @Override
    protected int getDisabledSlotOnHotBar() {
        return this.amuletSlot;
    }

    @Override
    public void save() {
        saveInventoryOnItemStack(this.inventory, player.inventory.mainInventory.get(this.amuletSlot));
    }
}
