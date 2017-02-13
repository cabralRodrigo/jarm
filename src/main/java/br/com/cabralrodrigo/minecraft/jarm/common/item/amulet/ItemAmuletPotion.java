package br.com.cabralrodrigo.minecraft.jarm.common.item.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.capability.CapabilityProviderItemHandler;
import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemAmuletVariantBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
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
                this.openGui(player, world, LibGui.AMULET_POTION, hand);
            } else
                this.setEnabled(itemStack, !this.isEnabled(itemStack));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilityProviderItemHandler(9 * 3);
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
                IItemHandlerModifiable handler = (IItemHandlerModifiable) stackAmulet.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

                for (int slotAmulet = 0; slotAmulet < handler.getSlots(); slotAmulet++) {
                    ItemStack stackPotionRef = handler.getStackInSlot(slotAmulet);

                    if (stackPotionRef != null && stackPotionRef.getItem() instanceof ItemPotion) {
                        List<PotionEffect> effectsPotion = PotionUtils.getEffectsFromStack(stackPotionRef);

                        if (stackPotionRef != null && this.canApplyEffectsOnPlayer(event.player, effectsPotion)) {
                            for (PotionEffect effect : effectsPotion)
                                event.player.addPotionEffect(new PotionEffect(effect));

                            handler.extractItem(slotAmulet, stackPotionRef.getCount(), false);
                            handler.insertItem(slotAmulet, new ItemStack(Items.GLASS_BOTTLE), false);
                        }
                    }
                }
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
