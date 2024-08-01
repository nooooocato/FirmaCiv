package com.alekiponi.firmaciv.common.item;

import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.alekiships.common.entity.CannonEntity;
import com.alekiponi.alekiships.common.item.CannonItem;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class FirmacivCannonItem extends CannonItem {

    public FirmacivCannonItem(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    protected CannonEntity getCannon(Level level) {
        return FirmacivEntities.FIRMACIV_CANNON_ENTITY.get().create(level);
    }

}

