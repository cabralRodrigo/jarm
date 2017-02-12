package br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.misc;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.ContainerJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.tileentity.TileEntityAmuletStamper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerAmuletStamper extends ContainerJarmBase {
    public ContainerAmuletStamper(EntityPlayer player, TileEntityAmuletStamper stamper) {
        super(player, stamper, 131);

    }

    @Override
    protected void bindContainerInventory() {
        //plate
        this.addSlotToContainer(new Slot(this.inventory, 0, 47, 33));

        //items
        this.addSlotToContainer(new Slot(this.inventory, 1, 29, 57));
        this.addSlotToContainer(new Slot(this.inventory, 2, 47, 57));
        this.addSlotToContainer(new Slot(this.inventory, 3, 65, 57));

        //outputs
        this.addSlotToContainer(new Slot(this.inventory, 4, 142, 72));
    }

    @Override
    protected int getDisabledSlotOnHotBar() {
        return -1;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void save() {

    }
}
