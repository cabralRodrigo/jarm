package br.com.cabralrodrigo.minecraft.jarm.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class TileEntityEnderEnchatmentTable extends TileEntity implements ITickable {

    private BookInfo bookInfo = new BookInfo();

    @Override
    public void update() {
        this.bookInfo.update(this.worldObj, this.pos);
    }

    public BookInfo getBookInfo() {
        return this.bookInfo;
    }

    public static class BookInfo {
        private float bookSpread;
        private float bookSpreadPrev;
        private float bookRotation;
        private float bookRotationPrev;
        private int tickCount;
        private float pageFlip;
        private float pageFlipPrev;
        private float field_145924_q;
        private float field_145932_k;
        private float field_145929_l;
        private static final Random rand = new Random();

        public void update(World world, BlockPos pos) {
            this.bookSpreadPrev = this.bookSpread;
            this.bookRotationPrev = this.bookRotation;
            EntityPlayer entityplayer = world.getClosestPlayer((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), 3.0D);

            if (entityplayer != null) {
                double d0 = entityplayer.posX - (double) ((float) pos.getX() + 0.5F);
                double d1 = entityplayer.posZ - (double) ((float) pos.getZ() + 0.5F);
                this.field_145924_q = (float) MathHelper.atan2(d1, d0);
                this.bookSpread += 0.1F;

                if (this.bookSpread < 0.5F || rand.nextInt(40) == 0) {
                    float f1 = this.field_145932_k;

                    while (true) {
                        this.field_145932_k += (float) (rand.nextInt(4) - rand.nextInt(4));

                        if (f1 != this.field_145932_k) {
                            break;
                        }
                    }
                }
            } else {
                this.field_145924_q += 0.02F;
                this.bookSpread -= 0.1F;
            }

            while (this.bookRotation >= (float) Math.PI) {
                this.bookRotation -= ((float) Math.PI * 2F);
            }

            while (this.bookRotation < -(float) Math.PI) {
                this.bookRotation += ((float) Math.PI * 2F);
            }

            while (this.field_145924_q >= (float) Math.PI) {
                this.field_145924_q -= ((float) Math.PI * 2F);
            }

            while (this.field_145924_q < -(float) Math.PI) {
                this.field_145924_q += ((float) Math.PI * 2F);
            }

            float f2;

            for (f2 = this.field_145924_q - this.bookRotation; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
                ;
            }

            while (f2 < -(float) Math.PI) {
                f2 += ((float) Math.PI * 2F);
            }

            this.bookRotation += f2 * 0.4F;
            this.bookSpread = MathHelper.clamp_float(this.bookSpread, 0.0F, 1.0F);
            ++this.tickCount;
            this.pageFlipPrev = this.pageFlip;
            float f = (this.field_145932_k - this.pageFlip) * 0.4F;
            float f3 = 0.2F;
            f = MathHelper.clamp_float(f, -f3, f3);
            this.field_145929_l += (f - this.field_145929_l) * 0.9F;
            this.pageFlip += this.field_145929_l;
        }

        public float getTickCount() {
            return this.tickCount;
        }

        public float getBookRotation(boolean previous) {
            return previous ? this.bookRotationPrev : this.bookRotation;
        }

        public float getPageFlip(boolean previous) {
            return previous ? this.pageFlipPrev : this.pageFlip;
        }

        public float getBookSpread(boolean previous) {
            return previous ? this.bookSpreadPrev : this.bookSpread;
        }
    }
}
