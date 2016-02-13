package cabralrodrigo.mc.mods.jarm.common.item.amulet;

import cabralrodrigo.mc.mods.jarm.common.item.ItemAmuletBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import cabralrodrigo.mc.mods.jarm.common.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAmuletTeleposer extends ItemAmuletBase {

    private static final String NBT_SPAWNER = "spawner";

    public ItemAmuletTeleposer() {
        super(LibItems.AMULET_TELEPOSER);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return this.hasSpawner(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        NBTTagCompound nbtSpawner = this.getSpawnerInfo(stack);
        if (nbtSpawner != null) {
            tooltip.add(this.translateForItem("tooltip.place"));
            tooltip.add(this.translateForItem("tooltip.mob", Translator.translateEntityName(nbtSpawner.getString("EntityId"))));
        } else
            tooltip.add(this.translateForItem("tooltip.pickup"));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (this.hasSpawner(stack)) {
            BlockPos posSpawner = pos.offset(side);
            if (world.isAirBlock(posSpawner)) {
                if (!world.isRemote) {
                    NBTTagCompound nbtSpawner = this.getSpawnerInfo(stack);
                    nbtSpawner.setInteger("x", posSpawner.getX());
                    nbtSpawner.setInteger("y", posSpawner.getY());
                    nbtSpawner.setInteger("z", posSpawner.getZ());

                    world.setBlockState(posSpawner, Blocks.mob_spawner.getDefaultState());
                    world.getTileEntity(posSpawner).readFromNBT(nbtSpawner);
                    world.markBlockForUpdate(posSpawner);

                    if (!player.capabilities.isCreativeMode)
                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                } else
                    return true;
            }
        } else {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null && tile instanceof TileEntityMobSpawner) {
                if (!world.isRemote) {
                    NBTTagCompound nbtSpawner = new NBTTagCompound();
                    tile.writeToNBT(nbtSpawner);

                    this.setSpawnerInfo(stack, nbtSpawner);
                    world.destroyBlock(pos, false);
                } else
                    return true;
            }
        }

        return false;
    }

    private boolean hasSpawner(ItemStack stack) {
        return this.getSpawnerInfo(stack) != null;
    }

    private NBTTagCompound getSpawnerInfo(ItemStack stack) {
        NBTTagCompound nbtItem = stack.getTagCompound();

        if (nbtItem != null) {
            if (nbtItem.hasKey(NBT_SPAWNER))
                return nbtItem.getCompoundTag(NBT_SPAWNER);
            else
                return null;
        } else
            return null;
    }

    private void setSpawnerInfo(ItemStack stack, NBTTagCompound nbtSpawner) {
        NBTTagCompound nbtStack = stack.getTagCompound();
        if (nbtStack == null)
            nbtStack = new NBTTagCompound();

        nbtStack.setTag(NBT_SPAWNER, nbtSpawner);
        stack.setTagCompound(nbtStack);
    }
}
