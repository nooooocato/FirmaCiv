package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.alekiships.common.entity.vehicle.SloopUnderConstructionEntity;
import com.alekiponi.alekiships.util.BoatMaterial;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class FirmacivSloopUnderConstructionEntity extends SloopUnderConstructionEntity {
    public FirmacivSloopUnderConstructionEntity(
            final EntityType<? extends FirmacivSloopUnderConstructionEntity> entityType, final Level level,
            final BoatMaterial boatMaterial) {
        super(entityType, level, boatMaterial);
    }

    @Override
    public Item getMainsailItem() {
        return FirmacivItems.MEDIUM_TRIANGULAR_SAIL.get();
    }

    @Override
    public Item getJibsailItem() {
        return FirmacivItems.SMALL_TRIANGULAR_SAIL.get();
    }
}