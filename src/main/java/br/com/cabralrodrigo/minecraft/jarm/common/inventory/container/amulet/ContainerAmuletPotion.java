package br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.inventory.container.ContainerBase;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet.InventoryAmuletPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerAmuletPotion extends ContainerBase {

    public ContainerAmuletPotion(EntityPlayer player, EnumHand hand, InventoryAmuletPotion inventoryAmuletPotion) {
        super(player, inventoryAmuletPotion, 86, hand == EnumHand.MAIN_HAND ? player.inventory.currentItem : -1);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    protected void bindContainerInventory() {
        for (int row = 0; row < 3; ++row)
            for (int column = 0; column < 9; column++)
                this.addSlotToContainer(new SlotItemHandler(this.inventory, column + row * 9, 8 + column * 18, 18 + row * 18));
    }
}