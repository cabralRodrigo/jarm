package br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.misc;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.ContainerJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.misc.InventorySeedBag;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.slot.SlotWhitelist;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.IPlantable;

public class ContainerSeedBag extends ContainerJarmBase {

    private int seedBagSlot;

    public ContainerSeedBag(EntityPlayer player, EnumHand hand) {
        super(player, new InventorySeedBag(player.getHeldItem(hand)), 86/*86*/);
        this.seedBagSlot = player.inventory.currentItem;
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
                this.addSlotToContainer(new SlotWhitelist(this, this.inventory, column + row * 9, 8 + column * 18, 18 + row * 18, IPlantable.class));
    }

    @Override
    protected int getDisabledSlotOnHotBar() {
        return this.seedBagSlot;
    }

    @Override
    public void save() {
        saveInventoryOnItemStack(this.inventory, player.inventory.mainInventory.get(this.seedBagSlot));
    }
}
