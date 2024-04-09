package com.alekiponi.firmaciv.client.render.entity.compartment;

import com.alekiponi.alekiships.client.render.entity.vehicle.vehiclehelper.ChestCompartmentRenderer;
import com.alekiponi.firmaciv.common.entity.compartment.TFCChestCompartmentEntity;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.Material;

public class TFCChestCompartmentRenderer extends ChestCompartmentRenderer<TFCChestCompartmentEntity> {

    public TFCChestCompartmentRenderer(final EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Material getMaterial(final TFCChestCompartmentEntity compartmentEntity) {
        return new Material(Sheets.CHEST_SHEET, compartmentEntity.getTextureLocation());
    }
}