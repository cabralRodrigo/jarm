package cabralrodrigo.mc.mods.jarm.common.item.amulet;

import cabralrodrigo.mc.mods.jarm.common.item.ItemAmuletVariantBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAmuletMagnetic extends ItemAmuletVariantBase {

    public ItemAmuletMagnetic() {
        super(LibItems.AMULET_MAGNETIC, ImmutableMap.of("disabled", 0, "enabled", 1));
        this.setNoRepair();
        this.setMaxStackSize(1);
        this.setDefaultDisplayDamage(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        if (this.isMagnetEnabled(stack))
            tooltip.add(this.translateForItem("tooltip.enabled"));
        else
            tooltip.add(this.translateForItem("tooltip.disabled"));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (player.isSneaking())
                this.setMagnetEnabled(itemStack, !this.isMagnetEnabled(itemStack));
        }

        return itemStack;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return this.isMagnetEnabled(stack);
    }

    private boolean isMagnetEnabled(ItemStack stack) {
        return stack.getItemDamage() == 1;
    }

    public void setMagnetEnabled(ItemStack stack, boolean enabled) {
        stack.setItemDamage(enabled ? 1 : 0);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // TODO: Make this method better
        if (event.phase == Phase.END && !event.player.worldObj.isRemote) {
            if (this.playerHasMagnetActive(event.player)) {
                EntityPlayer player = event.player;
                int RANGE = 5;
                AxisAlignedBB aabb = AxisAlignedBB.fromBounds(player.posX - RANGE, player.posY - RANGE, player.posZ - RANGE, player.posX + RANGE, player.posY + RANGE, player.posZ + RANGE);
                List<EntityItem> items = event.player.worldObj.getEntitiesWithinAABB(EntityItem.class, aabb);

                for (EntityItem item : items) {
                    double x = player.posX + 0.5D - item.posX;
                    double y = player.posY + 1D - item.posY;
                    double z = player.posZ + 0.5D - item.posZ;

                    double distance = x * x + y * y + z * z;
                    if (distance < 1.25 * 1.25)
                        item.onCollideWithPlayer(player);
                    else {
                        double distancespeed = (.035 * 4) / distance;
                        item.motionX += x * distancespeed;
                        if (y > 0)
                            item.motionY = 0.12;
                        else
                            item.motionY += y * .035;

                        item.motionZ += z * distancespeed;
                    }
                }
            }
        }
    }

    private boolean playerHasMagnetActive(EntityPlayer player) {
        ItemStack[] inventory = player.inventory.mainInventory;

        for (ItemStack stack : inventory)
            if (stack != null && stack.getItem() == this)
                if (this.isMagnetEnabled(stack))
                    return true;

        return false;
    }
}
