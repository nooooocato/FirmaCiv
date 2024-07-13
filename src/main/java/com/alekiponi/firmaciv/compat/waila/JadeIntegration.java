package com.alekiponi.firmaciv.compat.waila;

import com.alekiponi.alekiships.compat.waila.compartment.BlockCompartmentProvider;
import com.alekiponi.firmaciv.common.entity.compartment.TFCBarrelCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.compartment.TFCChestCompartmentEntity;
import com.alekiponi.firmaciv.compat.waila.compartment.BarrelCompartmentProvider;
import com.alekiponi.firmaciv.compat.waila.compartment.TFCChestCompartmentProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class JadeIntegration implements IWailaPlugin {

    @Override
    public void registerClient(final IWailaClientRegistration registry) {
        registry.registerEntityComponent(BarrelCompartmentProvider.INSTANCE, TFCBarrelCompartmentEntity.class);

        registry.registerEntityComponent(TFCChestCompartmentProvider.INSTANCE, TFCChestCompartmentEntity.class);

        // Block compartments
        registry.registerEntityComponent(BlockCompartmentProvider.INSTANCE, TFCBarrelCompartmentEntity.class);
    }
}