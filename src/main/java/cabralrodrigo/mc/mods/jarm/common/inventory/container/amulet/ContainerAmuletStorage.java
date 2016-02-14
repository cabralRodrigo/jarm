package cabralrodrigo.mc.mods.jarm.common.inventory.container.amulet;

import cabralrodrigo.mc.mods.jarm.common.inventory.container.ContainerJarmBase;
import cabralrodrigo.mc.mods.jarm.common.inventory.impl.amulet.InventoryAmuletStorage;
import cabralrodrigo.mc.mods.jarm.common.inventory.slot.SlotBlacklist;
import cabralrodrigo.mc.mods.jarm.common.registry.ModItems;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

@ChestContainer(showButtons = true, rowSize = 9, isLargeChest = false)
public class ContainerAmuletStorage extends ContainerJarmBase {
    private int amuletSlot;

    public ContainerAmuletStorage(EntityPlayer player) {
        super(player, new InventoryAmuletStorage(player.getCurrentEquippedItem()), 140);
        this.amuletSlot = player.inventory.currentItem;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
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
                this.addSlotToContainer(new SlotBlacklist(this, this.inventory, column + row * 9, 8 + column * 18, 18 + row * 18, new Item[]{ModItems.amulet_storage}));
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
