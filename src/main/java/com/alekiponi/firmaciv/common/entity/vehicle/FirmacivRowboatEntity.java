package com.alekiponi.firmaciv.common.entity.vehicle;

import com.alekiponi.alekiships.common.entity.vehicle.RowboatEntity;
import com.alekiponi.alekiships.util.BoatMaterial;
import net.dries007.tfc.util.climate.Climate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

public class FirmacivRowboatEntity extends RowboatEntity {

    public FirmacivRowboatEntity(final EntityType<? extends FirmacivRowboatEntity> entityType, final Level level,
            final BoatMaterial boatMaterial) {
        super(entityType, level, boatMaterial);
    }
}