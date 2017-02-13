package br.com.cabralrodrigo.minecraft.jarm.common.item.misc;

import br.com.cabralrodrigo.minecraft.jarm.common.capability.CapabilityProviderItemHandler;
import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemJarmBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibGui;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSeedBag extends ItemJarmBase {
    public ItemSeedBag() {
        super(LibItems.SEED_BAG);
        this.setHasSubtypes(false);
        this.setNoRepair();
        this.setMaxStackSize(1);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilityProviderItemHandler(3 * 9);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);

        if (player.isSneaking()) {
            if (!world.isRemote)
                this.onItemRightClick(world, player, hand);

            return EnumActionResult.SUCCESS;
        }

        IBlockState block = world.getBlockState(pos);
        if (block != null && facing == EnumFacing.UP) {
            if (!world.isRemote) {

                for (int x = -4; x <= 4; x++)
                    for (int z = -4; z <= 4; z++) {
                        BlockPos farmPos = new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z);
                        if (world.isAirBlock(farmPos))
                            continue;

                        IItemHandlerModifiable handler = (IItemHandlerModifiable) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

                        for (int slotIndex = 0; slotIndex < handler.getSlots(); slotIndex++) {
                            ItemStack stackSeed = handler.extractItem(slotIndex, 1, false);

                            if (stackSeed != null) {
                                stackSeed = this.tryToPlant(stackSeed, player, farmPos, world);

                                if (!stackSeed.isEmpty())
                                    handler.insertItem(slotIndex, stackSeed, false);
                            }
                        }
                    }
            }
            return EnumActionResult.SUCCESS;
        } else
            return EnumActionResult.FAIL;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote)
            if (player.isSneaking())
                this.openGui(player, world, LibGui.SEED_BAG, hand);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (GuiScreen.isShiftKeyDown())
            tooltip.add(this.translateForItem("tooltip.easter_egg"));
    }

    private ItemStack tryToPlant(ItemStack stack, EntityPlayer player, BlockPos pos, World world) {
        if (stack != null && stack.getItem() instanceof IPlantable) {
            if (this.canBePlantedAt(player, pos, stack, world)) {
                world.setBlockState(pos.up(), ((IPlantable) stack.getItem()).getPlant(world, pos.up()));
                stack.shrink(1);
            }
        }
        return stack;
    }

    private boolean canBePlantedAt(EntityPlayer player, BlockPos pos, ItemStack stack, World world) {
        if (stack == null)
            return false;

        if (!(stack.getItem() instanceof IPlantable))
            return false;

        else if (!player.canPlayerEdit(pos.offset(EnumFacing.UP), EnumFacing.UP, stack))
            return false;

        IBlockState state = world.getBlockState(pos);
        boolean canBePlanted = state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, (IPlantable) stack.getItem());

        return canBePlanted && world.isAirBlock(pos.up());
    }
}