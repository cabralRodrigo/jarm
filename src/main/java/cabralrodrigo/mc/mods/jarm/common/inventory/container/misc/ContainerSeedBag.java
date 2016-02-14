package cabralrodrigo.mc.mods.jarm.common.inventory.container.misc;

import cabralrodrigo.mc.mods.jarm.common.inventory.container.ContainerJarmBase;
import cabralrodrigo.mc.mods.jarm.common.inventory.impl.misc.InventorySeedBag;
import cabralrodrigo.mc.mods.jarm.common.inventory.slot.SlotWhitelist;
import invtweaks.api.container.ChestContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IPlantable;

@ChestContainer(showButtons = true, rowSize = 9, isLargeChest = false)
public class ContainerSeedBag extends ContainerJarmBase {

    private int seedBagSlot;

    public ContainerSeedBag(EntityPlayer player) {
        super(player, new InventorySeedBag(player.getCurrentEquippedItem()), 86/*86*/);
        this.seedBagSlot = player.inventory.currentItem;
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
                this.addSlotToContainer(new SlotWhitelist(this, this.inventory, column + row * 9, 8 + column * 18, 18 + row * 18, IPlantable.class));
    }

    @Override
    protected int getDisabledSlotOnHotBar() {
        return this.seedBagSlot;
    }

    @Override
    public void save() {
        saveInventoryOnItemStack(this.inventory, player.inventory.mainInventory[this.seedBagSlot]);
    }
}
