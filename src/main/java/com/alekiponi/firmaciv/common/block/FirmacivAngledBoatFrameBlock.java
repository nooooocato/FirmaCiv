package com.alekiponi.firmaciv.common.block;

import com.alekiponi.alekiships.common.block.AngledBoatFrameBlock;
import com.alekiponi.alekiships.common.block.BoatFrame;
import com.alekiponi.firmaciv.events.config.FirmacivConfig;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.minecraft.world.item.Item;

public class FirmacivAngledBoatFrameBlock extends AngledBoatFrameBlock {

    public FirmacivAngledBoatFrameBlock(final Properties properties) {
        super(properties);
    }

    @Override
    protected BoatFrame getFrame(final Item item) {
        if (!FirmacivConfig.SERVER.shipWoodRestriction.get()) {
            return super.getFrame(item);
        }

        //noinspection deprecation
        if (!item.builtInRegistryHolder().is(FirmacivTags.Items.HARD_WOOD)) {
            return null;
        }

        return super.getFrame(item);
    }
}