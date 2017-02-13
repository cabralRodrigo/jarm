package br.com.cabralrodrigo.minecraft.jarm.common.item.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.Jarm;
import br.com.cabralrodrigo.minecraft.jarm.common.inventory.impl.amulet.InventoryAmuletPotion;
import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemAmuletVariantBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import br.com.cabralrodrigo.minecraft.jarm.common.util.EnumHandHelper;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemAmuletPotion extends ItemAmuletVariantBase {
    public ItemAmuletPotion() {
        super(LibItems.AMULET_POTION, ImmutableMap.of("disabled", 0, "enabled", 1));
        this.setNoRepair();
        this.setMaxStackSize(1);
        this.setDefaultDisplayDamage(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (!world.isRemote) {
            if (player.isSneaking()) {
                this.setEnabled(itemStack, false);
                player.openGui(Jarm.instance, LibGui.AMULET_POTION, world, EnumHandHelper.toInt(hand), 0, 0);
            } else
                this.setEnabled(itemStack, !this.isEnabled(itemStack));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        if (this.isEnabled(stack))
            tooltip.add(this.translateForItem("tooltip.enable"));
        else
            tooltip.add(this.translateForItem("tooltip.disable"));

        tooltip.add(this.translateForItem("tooltip.use"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return this.isEnabled(stack);
    }

    private void setEnabled(ItemStack itemStack, boolean enabled) {
        itemStack.setItemDamage(enabled ? 1 : 0);
    }

    private boolean isEnabled(ItemStack itemStack) {
        return itemStack.getItemDamage() == 1;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote) {
            List<Integer> slots = this.getAllAmulets(event.player.inventory);

            for (int slot : slots) {
                ItemStack stackAmulet = event.player.inventory.getStackInSlot(slot);
                InventoryAmuletPotion inventoryAmulet = new InventoryAmuletPotion(stackAmulet);

                for (int slotAmulet = 0; slotAmulet < inventoryAmulet.getSizeInventory(); slotAmulet++) {
                    ItemStack stackPotion = inventoryAmulet.getStackInSlot(slotAmulet);

                    if (stackPotion != null && stackPotion.getItem() instanceof ItemPotion) {
                        List<PotionEffect> effectsPotion = PotionUtils.getEffectsFromStack(stackPotion);

                        if (stackPotion != null && this.canApplyEffectsOnPlayer(event.player, effectsPotion)) {
                            for (PotionEffect effect : effectsPotion)
                                event.player.addPotionEffect(new PotionEffect(effect));

                            inventoryAmulet.setInventorySlotContents(slotAmulet, new ItemStack(Items.GLASS_BOTTLE));
                        }
                    }
                }
                inventoryAmulet.serializeIntoItemStack(stackAmulet);
                event.player.inventory.setInventorySlotContents(slot, stackAmulet);
            }
        }
    }

    private List<Integer> getAllAmulets(IInventory inventory) {
        List<Integer> slots = new ArrayList<Integer>();

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() == this && this.isEnabled(stack))
                slots.add(i);
        }

        return slots;
    }

    private boolean canApplyEffectsOnPlayer(EntityPlayer player, List<PotionEffect> effects) {
        Collection<PotionEffect> effectsPlayer = player.getActivePotionEffects();

        for (PotionEffect effect : effects)
            for (PotionEffect effectPlayer : effectsPlayer)
                if (Potion.getIdFromPotion(effect.getPotion()) == Potion.getIdFromPotion(effectPlayer.getPotion()))
                    return false;

        return true;
    }
}
