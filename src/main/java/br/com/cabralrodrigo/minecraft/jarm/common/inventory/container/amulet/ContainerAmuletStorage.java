package br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.ContainerJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet.InventoryAmuletStorage;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.slot.SlotBlacklist;
import br.com.cabralrodrigo.minecraft.jarm.common.registry.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;

public class ContainerAmuletStorage extends ContainerJarmBase {
    private int amuletSlot;

    public ContainerAmuletStorage(EntityPlayer player, EnumHand hand) {
        super(player, new InventoryAmuletStorage(player.getHeldItem(hand)), 140);
        this.amuletSlot = player.inventory.currentItem;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
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
                this.addSlotToContainer(new SlotBlacklist(this, this.inventory, column + row * 9, 8 + column * 18, 18 + row * 18, new Item[]{ModItems.amulet_storage}));
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
