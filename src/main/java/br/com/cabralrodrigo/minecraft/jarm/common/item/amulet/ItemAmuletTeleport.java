package br.com.cabralrodrigo.minecraft.jarm.common.item.amulet;

import br.com.cabralrodrigo.minecraft.jarm.common.item.ItemAmuletBase;
import br.com.cabralrodrigo.minecraft.jarm.common.lib.LibItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemAmuletTeleport extends ItemAmuletBase {
    public ItemAmuletTeleport() {
        super(LibItems.AMULET_TELEPORT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);

        AmuletInfo info = new AmuletInfo(stack);
        if (info.isLocationSet()) {
            if (GuiScreen.isShiftKeyDown()) {
                BlockPos pos = info.getLocation();

                tooltip.add(this.translateForItem("tooltip.dimension", info.getDimensionName()));
                tooltip.add(this.translateForItem("tooltip.location", pos.getX(), pos.getX(), pos.getZ()));
            } else
                tooltip.add(this.translateForItem("tooltip.hold_shift"));
        } else
            tooltip.add(this.translateForItem("tooltip.no_location"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return new AmuletInfo(stack).isLocationSet();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            AmuletInfo info = new AmuletInfo(player.getHeldItem(hand));

            if (info.isLocationSet()) {
                BlockPos pos = info.getLocation();

                if (player.isRiding())
                    player.dismountRidingEntity();

                if (player.dimension != info.getDimensionID())
                    player.changeDimension(info.getDimensionID());

                player.setPositionAndUpdate(pos.getX() + .5F, pos.getY(), pos.getZ() + .5F);
            } else {
                info.setLocation(player.dimension, new BlockPos(player.posX, player.posY, player.posZ));
                info.save();
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    private class AmuletInfo {
        private BlockPos pos;
        private int dimensionID;
        private String dimensionName;
        private ItemStack stack;

        public AmuletInfo(ItemStack stack) {
            this.stack = stack;
            this.load();
        }

        public void save() {
            NBTTagCompound info = new NBTTagCompound();
            info.setInteger("x", this.pos.getX());
            info.setInteger("y", this.pos.getY());
            info.setInteger("z", this.pos.getZ());
            info.setInteger("dimension_id", this.dimensionID);
            info.setString("dimension_name", DimensionManager.createProviderFor(this.dimensionID).getDimensionType().getName());

            NBTTagCompound compound = this.stack.getTagCompound();
            if (compound == null)
                compound = new NBTTagCompound();

            compound.setTag("amulet_info", info);
            this.stack.setTagCompound(compound);
        }

        public boolean isLocationSet() {
            return this.pos != null;
        }

        public void setLocation(int dimensionID, BlockPos pos) {
            this.dimensionID = dimensionID;
            this.pos = pos;
        }

        public int getDimensionID() {
            return this.dimensionID;
        }

        public String getDimensionName() {
            return this.dimensionName;
        }

        public BlockPos getLocation() {
            return this.pos;
        }

        private void load() {
            NBTTagCompound compound = this.stack.getTagCompound();
            if (compound == null)
                compound = new NBTTagCompound();

            if (compound.hasKey("amulet_info", 10)) {
                NBTTagCompound info = compound.getCompoundTag("amulet_info");
                if (info != null) {
                    int x = info.getInteger("x");
                    int y = info.getInteger("y");
                    int z = info.getInteger("z");
                    this.pos = new BlockPos(x, y, z);
                    this.dimensionID = info.getInteger("dimension_id");
                    this.dimensionName = info.getString("dimension_name");
                }
            }
        }
    }

    private class TeleporterAmulet extends Teleporter {
        private final AmuletInfo info;

        public TeleporterAmulet(WorldServer world, AmuletInfo info) {
            super(world);
            this.info = info;
        }

        @Override
        public void placeInPortal(Entity entity, float rotationYaw) {
            this.placeInExistingPortal(entity, rotationYaw);
        }

        @Override
        public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
            BlockPos pos = this.info.getLocation();
            entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), rotationYaw, entity.rotationPitch);
            return true;
        }
    }
}
