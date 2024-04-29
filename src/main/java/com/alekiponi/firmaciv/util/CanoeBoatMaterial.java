package com.alekiponi.firmaciv.util;

import com.alekiponi.alekiships.util.BoatMaterial;
import com.alekiponi.firmaciv.common.entity.vehicle.CanoeEntity;
import net.minecraft.world.entity.EntityType;

import java.util.Optional;

public interface CanoeBoatMaterial extends BoatMaterial {

    /**
     * A simple getter for the canoe entity type associated with this {@link BoatMaterial}
     *
     * @return An optional canoe entity type
     */
    Optional<EntityType<? extends CanoeEntity>> getCanoeType();
}