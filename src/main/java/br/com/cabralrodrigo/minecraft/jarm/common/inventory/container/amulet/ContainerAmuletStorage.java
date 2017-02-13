package br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.ContainerBase;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet.InventoryAmuletStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAmuletStorage extends ContainerBase {
    public ContainerAmuletStorage(EntityPlayer player, EnumHand hand, InventoryAmuletStorage inventoryAmuletStorage) {
        super(player, inventoryAmuletStorage, 140, hand == EnumHand.MAIN_HAND ? player.inventory.currentItem : -1);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    protected void bindContainerInventory() {
        for (int row = 0; row < 6; ++row)
            for (int column = 0; column < 9; column++)
                this.addSlotToContainer(new SlotItemHandler(this.inventory, column + row * 9, 8 + column * 18, 18 + row * 18));
    }
}