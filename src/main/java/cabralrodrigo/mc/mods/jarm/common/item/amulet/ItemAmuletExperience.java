package cabralrodrigo.mc.mods.jarm.common.item.amulet;

import cabralrodrigo.mc.mods.jarm.common.Jarm;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import cabralrodrigo.mc.mods.jarm.common.lib.LibMod;
import cabralrodrigo.mc.mods.jarm.common.registry.util.IVariantRegistrable;
import cabralrodrigo.mc.mods.jarm.common.util.Translator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemAmuletExperience extends Item implements IVariantRegistrable {
    private static final String[] MODES = new String[]{"disabled", "absorb", "retrieve"};

    public ItemAmuletExperience() {
        this.setUnlocalizedName(LibMod.bindModId(':', this.getName()));
        this.setCreativeTab(Jarm.creativeTab);
        this.setHasSubtypes(false);
        this.setNoRepair();
        this.setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        tooltip.add(Translator.translate("item", this.getName(), "tooltip"));
        tooltip.add(Translator.translate("item", this.getName(), "tooltip.cycle_mode"));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String name = super.getItemStackDisplayName(stack);

        int modeIndex = stack.getItemDamage();
        if (modeIndex > MODES.length - 1)
            modeIndex = 0;

        return name + ": " + Translator.translate("item", this.getName(), "tooltip.mode." + MODES[modeIndex]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return stack.getItemDamage() == 1 /* Absorb */;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (player.isSneaking())
                this.cycleMode(itemStack);

            player.addChatComponentMessage(new ChatComponentText("Total: " + player.experienceTotal));
            player.addChatComponentMessage(new ChatComponentText("Amount: " + player.experience));
            player.addChatComponentMessage(new ChatComponentText("Amount to level: " + xpBarCap(player.experienceTotal)));
        }

        return itemStack;
    }

    private int xpBarCap(int level) {
        if (level < 1)
            return 0;

        if (level >= 30)
            return 112 + (level - 30) * 9;
        else {
            if (level >= 15)
                return 37 + (level - 15) * 5;
            else
                return 7 + level * 2;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        subItems.add(new ItemStack(itemIn, 1, 1/* Absorb */));
    }

    @Override
    public Map<String, Integer> getVariants() {
        Map<String, Integer> variants = new HashMap<String, Integer>();

        for (int i = 0; i < MODES.length; i++)
            variants.put(MODES[i], i);

        return variants;
    }

    @Override
    public String getName() {
        return LibItems.AMULET_EXPERIENCE;
    }

    private void cycleMode(ItemStack stack) {
        if (stack != null & stack.getItem() == this) {
            int newModeIndex = stack.getItemDamage() + 1;
            stack.setItemDamage(newModeIndex > MODES.length - 1 ? 0 : newModeIndex);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == Phase.END && !event.player.worldObj.isRemote) {

        }
    }
}
