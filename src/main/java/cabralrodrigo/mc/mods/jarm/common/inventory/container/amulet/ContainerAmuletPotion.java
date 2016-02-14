package cabralrodrigo.mc.mods.jarm.common.inventory.container.amulet;

import cabralrodrigo.mc.mods.jarm.common.inventory.container.ContainerJarmBase;
import cabralrodrigo.mc.mods.jarm.common.inventory.impl.amulet.InventoryAmuletPotion;
import cabralrodrigo.mc.mods.jarm.common.inventory.slot.SlotPotion;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;

@ChestContainer(showButtons = true, rowSize = 9, isLargeChest = false)
public class ContainerAmuletPotion extends ContainerJarmBase {
    private int amuletSlot;

    public ContainerAmuletPotion(EntityPlayer player) {
        super(player, new InventoryAmuletPotion(player.getCurrentEquippedItem()), 86);
        this.amuletSlot = player.inventory.currentItem;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        if (!player.worldObj.isRemote)
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
        saveInventoryOnItemStack(this.inventory, player.inventory.mainInventory[this.amuletSlot]);
    }
}
