package cabralrodrigo.mc.mods.jarm.common.item.misc;

import cabralrodrigo.mc.mods.jarm.common.Jarm;
import cabralrodrigo.mc.mods.jarm.common.inventory.InventorySeedBag;
import cabralrodrigo.mc.mods.jarm.common.item.ItemJarmBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibGui;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSeedBag extends ItemJarmBase {
    public ItemSeedBag() {
        super(LibItems.SEED_BAG);
        this.setHasSubtypes(false);
        this.setNoRepair();
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            if (!world.isRemote)
                this.onItemRightClick(stack, world, player);

            return false;
        }

        IBlockState block = world.getBlockState(pos);
        if (block != null && side == EnumFacing.UP) {
            if (!world.isRemote) {
                InventorySeedBag inventory = new InventorySeedBag(stack);

                for (int x = -4; x <= 4; x++)
                    for (int z = -4; z <= 4; z++) {
                        BlockPos farmPos = new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z);
                        if (world.isAirBlock(farmPos))
                            continue;

                        for (int slotIndex = 0; slotIndex < inventory.getSizeInventory(); slotIndex++) {
                            ItemStack stackSeed = inventory.removeStackFromSlot(slotIndex);
                            if (stackSeed != null) {
                                stackSeed = this.tryToPlant(stackSeed, player, farmPos, world, hitX, hitY, hitZ);
                                if (slotIndex >= 0 && stackSeed != null && stackSeed.stackSize > 0)
                                    inventory.setInventorySlotContents(slotIndex, stackSeed);
                            }
                        }
                    }

                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt == null)
                    nbt = new NBTTagCompound();

                inventory.writeToNBT(nbt);
                stack.setTagCompound(nbt);
            }
            return true;
        } else
            return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote)
            if (player.isSneaking())
                player.openGui(Jarm.instance, LibGui.SEED_BAG, world, (int) player.posX, (int) player.posY, (int) player.posZ);

        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        if (GuiScreen.isShiftKeyDown())
            tooltip.add(this.translateForItem("tooltip.easter_egg"));
    }

    private ItemStack tryToPlant(ItemStack stack, EntityPlayer player, BlockPos pos, World world, float hitX, float hitY, float hitZ) {
        if (stack != null && stack.getItem() instanceof IPlantable) {
            if (this.canBePlantedAt(player, pos, stack, world)) {
                world.setBlockState(pos.up(), ((IPlantable) stack.getItem()).getPlant(world, pos.up()));
                stack.stackSize--;
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

        boolean canBePlanted = world.getBlockState(pos).getBlock().canSustainPlant(world, pos, EnumFacing.UP, (IPlantable) stack.getItem());

        return canBePlanted && world.isAirBlock(pos.up());
    }
}
