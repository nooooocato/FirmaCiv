package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.alekiships.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.alekiships.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.*;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.*;
import com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment.TFCChestCompartmentEntity;
import com.alekiponi.firmaciv.util.FirmacivWoodHelper;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.Map;

import static com.alekiponi.firmaciv.Firmaciv.MOD_ID;

public final class FirmacivEntities {

    private static final int LARGE_VEHICLE_TRACKING = 20;
    private static final int VEHICLE_HELPER_TRACKING = LARGE_VEHICLE_TRACKING + 1;
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            MOD_ID);

    public static final Map<RegistryWood, RegistryObject<EntityType<CanoeEntity>>> CANOES = FirmacivWoodHelper.TFCWoodMap(
            wood -> register("dugout_canoe/" + wood.getSerializedName(),
                    EntityType.Builder.of(CanoeEntity::new, MobCategory.MISC).sized(1.125F, 0.625F)));

    public static final Map<RegistryWood, RegistryObject<EntityType<RowboatEntity>>> ROWBOATS = FirmacivWoodHelper.TFCWoodMap(
            wood -> register("rowboat/" + wood.getSerializedName(),
                    EntityType.Builder.of(RowboatEntity::new, MobCategory.MISC).sized(1.875F, 0.625F)));

    public static final RegistryObject<EntityType<KayakEntity>> KAYAK_ENTITY = register("kayak",
            EntityType.Builder.of(KayakEntity::new, MobCategory.MISC).sized(0.79F, 0.625F));

    public static final Map<RegistryWood, RegistryObject<EntityType<SloopEntity>>> SLOOPS = FirmacivWoodHelper.TFCWoodMap(
            wood -> register("sloop/" + wood.getSerializedName(),
                    EntityType.Builder.of(SloopEntity::new, MobCategory.MISC).sized(3F, 0.75F)
                            .setTrackingRange(LARGE_VEHICLE_TRACKING).fireImmune()));

    /*
    public static final Map<BoatVariant, RegistryObject<EntityType<SloopConstructionEntity>>> SLOOPS_UNDER_CONSTRUCTION = Helpers.mapOfKeys(
            BoatVariant.class, variant -> register("sloop_construction/" + variant.getName(),
                    EntityType.Builder.of(SloopConstructionEntity::new, MobCategory.MISC).sized(3F, 0.75F)
                            .setTrackingRange(LARGE_VEHICLE_TRACKING).fireImmune()));
     */

    public static final Map<RegistryWood, RegistryObject<EntityType<SloopUnderConstructionEntity>>> SLOOPS_UNDER_CONSTRUCTION = FirmacivWoodHelper.TFCWoodMap(
            wood -> register("sloop_construction/" + wood.getSerializedName(),
                    EntityType.Builder.<SloopUnderConstructionEntity>of((type, level) -> new SloopUnderConstructionEntity(type,level, wood), MobCategory.MISC).sized(4F, 0.75F)
                            .setTrackingRange(LARGE_VEHICLE_TRACKING).fireImmune().noSummon()));

    public static final RegistryObject<CompartmentType<TFCChestCompartmentEntity>> TFC_CHEST_COMPARTMENT_ENTITY = registerCompartment(
            "compartment_tfcchest",
            CompartmentType.Builder.of(TFCChestCompartmentEntity::new, TFCChestCompartmentEntity::new,
                    MobCategory.MISC));

    public static final RegistryObject<EntityType<BoatVehiclePart>> BOAT_VEHICLE_PART = register("vehicle_part_boat",
            EntityType.Builder.of(BoatVehiclePart::new, MobCategory.MISC).sized(0, 0)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon());

    public static final RegistryObject<EntityType<ConstructionVehiclePart>> CONSTRUCTION_VEHICLE_PART = register("vehicle_part_construction",
            EntityType.Builder.of(ConstructionVehiclePart::new, MobCategory.MISC).sized(0, 0)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon());

    public static final RegistryObject<EntityType<VehicleCleatEntity>> VEHICLE_CLEAT_ENTITY = register("vehicle_cleat",
            EntityType.Builder.of(VehicleCleatEntity::new, MobCategory.MISC).sized(0.4F, 0.2F)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon());

    public static final RegistryObject<EntityType<VehicleCollisionEntity>> VEHICLE_COLLISION_ENTITY = register(
            "vehicle_collider",
            EntityType.Builder.of(VehicleCollisionEntity::new, MobCategory.MISC).sized(1, 1).noSummon());

    public static final RegistryObject<EntityType<SailSwitchEntity>> SAIL_SWITCH_ENTITY = register(
            "vehicle_switch_sail",
            EntityType.Builder.of(SailSwitchEntity::new, MobCategory.MISC).sized(0.8F, 0.8F).noSummon());

    public static final RegistryObject<EntityType<AnchorEntity>> ANCHOR_ENTITY = register("vehicle_anchor",
            EntityType.Builder.of(AnchorEntity::new, MobCategory.MISC).sized(1, 1)
                    .clientTrackingRange(VEHICLE_HELPER_TRACKING).noSummon());

    public static final RegistryObject<EntityType<ConstructionEntity>> CONSTRUCTION_ENTITY = register("vehicle_construction",
            EntityType.Builder.of(ConstructionEntity::new, MobCategory.MISC).sized(1, 1)
                    .clientTrackingRange(VEHICLE_HELPER_TRACKING).noSummon());

    public static final RegistryObject<EntityType<WindlassSwitchEntity>> WINDLASS_SWITCH_ENTITY = register(
            "vehicle_switch_windlass",
            EntityType.Builder.of(WindlassSwitchEntity::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon());

    public static final RegistryObject<EntityType<CannonballEntity>> CANNONBALL_ENTITY = register("cannonball",
            EntityType.Builder.of(CannonballEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).setTrackingRange(32)
                    .clientTrackingRange(32).noSummon());

    public static final RegistryObject<EntityType<CannonEntity>> CANNON_ENTITY = register("cannon",
            EntityType.Builder.of(CannonEntity::new, MobCategory.MISC).sized(0.8F, 0.8F));

    public static final RegistryObject<EntityType<MastEntity>> MAST_ENTITY = register("vehicle_mast",
            EntityType.Builder.of(MastEntity::new, MobCategory.MISC).sized(0.3F, 8)
                    .setTrackingRange(VEHICLE_HELPER_TRACKING).noSummon());

    /**
     * Registers a compartment entity
     */
    @SuppressWarnings("SameParameterValue")
    private static <E extends AbstractCompartmentEntity> RegistryObject<CompartmentType<E>> registerCompartment(
            final String name, final CompartmentType.Builder<E> builder) {
        return register(name, builder.sized(0.6F, 0.7F).fireImmune().noSummon(), true);
    }

    /**
     * Base method for registering a compartment entity
     */
    @SuppressWarnings("SameParameterValue")
    private static <E extends AbstractCompartmentEntity> RegistryObject<CompartmentType<E>> register(final String name,
            final CompartmentType.Builder<E> builder, final boolean serialize) {
        final String id = name.toLowerCase(Locale.ROOT);
        return ENTITY_TYPES.register(id, () -> {
            if (!serialize) builder.noSave();
            return builder.build(MOD_ID + ":" + id);
        });
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> register(final String name,
            final EntityType.Builder<E> builder) {
        return register(name, builder, true);
    }

    @SuppressWarnings("SameParameterValue")
    private static <E extends Entity> RegistryObject<EntityType<E>> register(final String name,
            final EntityType.Builder<E> builder, final boolean serialize) {
        final String id = name.toLowerCase(Locale.ROOT);
        return ENTITY_TYPES.register(id, () -> {
            if (!serialize) builder.noSave();
            return builder.build(MOD_ID + ":" + id);
        });
    }
}