package cabralrodrigo.mc.mods.jarm.common.item.amulet;

import cabralrodrigo.mc.mods.jarm.common.item.ItemAmuletBase;
import cabralrodrigo.mc.mods.jarm.common.lib.LibItems;
import cabralrodrigo.mc.mods.jarm.common.util.ItemHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            AmuletInfo info = new AmuletInfo(itemStack);

            if (info.isLocationSet()) {
                BlockPos pos = info.getLocation();

                if (player.isRiding())
                    player.mountEntity((Entity) null);

                if (player.dimension != info.getDimensionID()) {
                    ServerConfigurationManager manager = ((EntityPlayerMP) player).mcServer.getConfigurationManager();
                    manager.transferPlayerToDimension((EntityPlayerMP) player, info.getDimensionID(), new TeleporterAmulet((WorldServer) world, info));
                } else
                    player.setPositionAndUpdate(pos.getX() + .5F, pos.getY(), pos.getZ() + .5F);

                // TODO: insert particles and sounds
            } else {
                info.setLocation(player.dimension, new BlockPos(player.posX, player.posY, player.posZ));
                info.save();
            }
        }
        return itemStack;
    }

    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Detonate event) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
            for (Entity entity : event.getAffectedEntities())
                if (entity instanceof EntityItem) {
                    ItemStack stack = ((EntityItem) entity).getEntityItem();
                    if (stack != null && stack.getItem() == this) {
                        AmuletInfo info = new AmuletInfo(stack);
                        if (info.isLocationSet()) {
                            NBTTagCompound compound = (NBTTagCompound) stack.getTagCompound().copy();
                            if (compound.hasKey("amulet_info"))
                                compound.removeTag("amulet_info");

                            ItemStack newStack = new ItemStack(this, 1);
                            newStack.setTagCompound(compound);
                            ItemHelper.spawnItemStack(event.world, new BlockPos(event.explosion.getPosition()), newStack);

                            entity.setDead();
                        }
                    }
                }
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
            info.setString("dimension_name", DimensionManager.createProviderFor(this.dimensionID).getDimensionName());

            NBTTagCompound compound = this.stack.getTagCompound();
            if (compound == null)
                compound = new NBTTagCompound();

            compound.setTag("amulet_info", info);
            stack.setTagCompound(compound);
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
