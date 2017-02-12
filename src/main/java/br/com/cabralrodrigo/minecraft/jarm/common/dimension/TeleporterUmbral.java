package br.com.cabralrodrigo.minecraft.jarm.common.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterUmbral extends Teleporter {
    public TeleporterUmbral(WorldServer world) {
        super(world);
    }

    @Override
    public void removeStalePortalLocations(long worldTime) {
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw) {
        placeInExistingPortal(entity, rotationYaw);
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        entity.setLocationAndAngles(7.5F, 65, 7.5F, rotationYaw, 0F);
        return true;
    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }

}
