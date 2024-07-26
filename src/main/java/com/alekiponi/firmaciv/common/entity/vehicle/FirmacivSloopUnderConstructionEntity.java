package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.alekiships.common.entity.vehicle.SloopUnderConstructionEntity;
import com.alekiponi.alekiships.util.BoatMaterial;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import oshi.util.tuples.Pair;

public class FirmacivSloopUnderConstructionEntity extends SloopUnderConstructionEntity {
    public FirmacivSloopUnderConstructionEntity(
            final EntityType<? extends FirmacivSloopUnderConstructionEntity> entityType, final Level level,
            final BoatMaterial boatMaterial) {
        super(entityType, level, boatMaterial);
    }

    public Pair<Item, Integer> getMainsailItem() {
        return new Pair(FirmacivItems.MEDIUM_TRIANGULAR_SAIL.get(),1);
    }

    @Override
    public Pair<Item, Integer> getJibsailItem() {
        return new Pair<>(FirmacivItems.SMALL_TRIANGULAR_SAIL.get(),1);
    }

    @Override
    public Pair<Item, Integer> getRiggingItem() {
        return new Pair<>(FirmacivItems.ROPE_COIL.get(), 8);
    }
}