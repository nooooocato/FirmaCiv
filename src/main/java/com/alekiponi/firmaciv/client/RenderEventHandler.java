package com.alekiponi.firmaciv.client;

import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.client.model.entity.CanoeEntityModel;
import com.alekiponi.firmaciv.client.model.entity.KayakEntityModel;
import com.alekiponi.firmaciv.client.render.entity.vehicle.KayakRenderer;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Firmaciv.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RenderEventHandler {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CanoeEntityModel.LAYER_LOCATION, CanoeEntityModel::createBodyLayer);
        event.registerLayerDefinition(KayakEntityModel.LAYER_LOCATION, KayakEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // TODO migrate to the new renderers. This will cause a crash!
//        FirmacivWoodHelper.forAllTFCWoods(wood -> {
//            event.registerEntityRenderer(FirmacivEntities.CANOES.get(wood).get(),
//                    context -> new CanoeRenderer(context, wood.getSerializedName()));
//            event.registerEntityRenderer(FirmacivEntities.ROWBOATS.get(wood).get(),
//                    context -> new RowboatRenderer(context, wood.getSerializedName()));
//            event.registerEntityRenderer(FirmacivEntities.SLOOPS.get(wood).get(),
//                    context -> new SloopRenderer(context, wood.getSerializedName()));
//            event.registerEntityRenderer(FirmacivEntities.SLOOPS_UNDER_CONSTRUCTION.get(wood).get(),
//                    context -> new SloopConstructionRenderer(context, wood.getSerializedName()));
//        });

        event.registerEntityRenderer(FirmacivEntities.KAYAK_ENTITY.get(), KayakRenderer::new);

        // TODO make a renderer for this
        event.registerEntityRenderer(FirmacivEntities.TFC_CHEST_COMPARTMENT_ENTITY.get(), NoopRenderer::new);
    }
}