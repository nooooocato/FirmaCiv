package com.alekiponi.firmaciv.client;

import com.alekiponi.alekiships.client.render.entity.CannonRenderer;
import com.alekiponi.alekiships.client.render.entity.vehicle.RowboatRenderer;
import com.alekiponi.alekiships.client.render.entity.vehicle.SloopConstructionRenderer;
import com.alekiponi.alekiships.client.render.entity.vehicle.SloopRenderer;
import com.alekiponi.alekiships.client.render.entity.vehicle.vehiclehelper.BlockCompartmentRenderer;
import com.alekiponi.alekiships.util.CommonHelper;
import com.alekiponi.firmaciv.client.model.entity.CanoeEntityModel;
import com.alekiponi.firmaciv.client.model.entity.KayakEntityModel;
import com.alekiponi.firmaciv.client.render.entity.compartment.BarrelCompartmentRenderer;
import com.alekiponi.firmaciv.client.render.entity.compartment.TFCChestCompartmentRenderer;
import com.alekiponi.firmaciv.client.render.entity.vehicle.CanoeRenderer;
import com.alekiponi.firmaciv.client.render.entity.vehicle.KayakRenderer;
import com.alekiponi.firmaciv.common.entity.FirmacivCannonEntity;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.util.TFCWood;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.alekiponi.firmaciv.Firmaciv.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RenderEventHandler {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CanoeEntityModel.LAYER_LOCATION, CanoeEntityModel::createBodyLayer);
        event.registerLayerDefinition(KayakEntityModel.LAYER_LOCATION, KayakEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        for (final TFCWood tfcWood : TFCWood.values()) {
            // Rowboat
            event.registerEntityRenderer(FirmacivEntities.TFC_ROWBOATS.get(tfcWood).get(),
                    context -> new RowboatRenderer(context, new ResourceLocation(MOD_ID,
                            "textures/entity/watercraft/rowboat/" + tfcWood.getSerializedName()),
                            CommonHelper.mapOfKeys(DyeColor.class, dyeColor -> new ResourceLocation(MOD_ID,
                                    "textures/entity/watercraft/rowboat/" + tfcWood.getSerializedName() + "/" + dyeColor.getSerializedName()))));
            // Sloops
            event.registerEntityRenderer(FirmacivEntities.TFC_SLOOPS.get(tfcWood).get(),
                    context -> new SloopRenderer(context, new ResourceLocation(MOD_ID,
                            "textures/entity/watercraft/sloop/" + tfcWood.getSerializedName()),
                            CommonHelper.mapOfKeys(DyeColor.class, dyeColor -> new ResourceLocation(MOD_ID,
                                    "textures/entity/watercraft/sloop/" + tfcWood.getSerializedName() + "/" + dyeColor.getSerializedName()))));
            // Construction sloops
            event.registerEntityRenderer(FirmacivEntities.TFC_SLOOPS_UNDER_CONSTRUCTION.get(tfcWood).get(),
                    context -> new SloopConstructionRenderer(context, new ResourceLocation(MOD_ID,
                            "textures/entity/watercraft/sloop_construction/" + tfcWood.getSerializedName() + ".png")));
            // Canoes
            event.registerEntityRenderer(FirmacivEntities.TFC_CANOES.get(tfcWood).get(),
                    context -> new CanoeRenderer(context, new ResourceLocation(MOD_ID,
                            "textures/entity/watercraft/dugout_canoe/" + tfcWood.getSerializedName() + ".png")));
        }

        event.registerEntityRenderer(FirmacivEntities.KAYAK_ENTITY.get(), KayakRenderer::new);

        event.registerEntityRenderer(FirmacivEntities.FIRMACIV_CANNON_ENTITY.get(), CannonRenderer::new);

        event.registerEntityRenderer(FirmacivEntities.TFC_CHEST_COMPARTMENT_ENTITY.get(),
                TFCChestCompartmentRenderer::new);

        event.registerEntityRenderer(FirmacivEntities.TFC_BARREL_COMPARTMENT_ENTITY.get(),
                BarrelCompartmentRenderer::new);

        event.registerEntityRenderer(FirmacivEntities.LARGE_VESSEL_COMPARTMENT_ENTITY.get(),
                BlockCompartmentRenderer::new);
    }
}