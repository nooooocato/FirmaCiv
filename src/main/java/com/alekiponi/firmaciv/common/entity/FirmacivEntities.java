package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.alekiships.common.entity.vehicle.RowboatEntity;
import com.alekiponi.alekiships.common.entity.vehicle.SloopEntity;
import com.alekiponi.alekiships.common.entity.vehicle.SloopUnderConstructionEntity;
import com.alekiponi.alekiships.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.alekiships.common.entity.vehiclehelper.compartment.AbstractCompartmentEntity;
import com.alekiponi.alekiships.util.AlekiShipsHelper;
import com.alekiponi.firmaciv.common.entity.compartment.TFCChestCompartmentEntity;
import com.alekiponi.firmaciv.common.entity.vehicle.*;
import com.alekiponi.firmaciv.util.FirmacivTags;
import com.alekiponi.firmaciv.util.TFCWood;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.Locale;

import static com.alekiponi.firmaciv.Firmaciv.MOD_ID;

public final class FirmacivEntities {

    private static final int LARGE_VEHICLE_TRACKING = 20;
    private static final int VEHICLE_HELPER_TRACKING = LARGE_VEHICLE_TRACKING + 1;
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            MOD_ID);

    public static final EnumMap<TFCWood, RegistryObject<EntityType<FirmacivRowboatEntity>>> TFC_ROWBOATS = AlekiShipsHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerRowboat(tfcWood,
                    EntityType.Builder.of((entityType, level) -> new FirmacivRowboatEntity(entityType, level, tfcWood),
                            MobCategory.MISC)));

    public static final EnumMap<TFCWood, RegistryObject<EntityType<FirmacivSloopEntity>>> TFC_SLOOPS = AlekiShipsHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerSloop(tfcWood,
                    EntityType.Builder.of((entityType, level) -> new FirmacivSloopEntity(entityType, level, tfcWood),
                            MobCategory.MISC)));

    public static final EnumMap<TFCWood, RegistryObject<EntityType<FirmacivSloopUnderConstructionEntity>>> TFC_SLOOPS_UNDER_CONSTRUCTION = AlekiShipsHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerSloopConstruction(tfcWood, EntityType.Builder.of(
                    (entityType, level) -> new FirmacivSloopUnderConstructionEntity(entityType, level, tfcWood),
                    MobCategory.MISC)));

    public static final EnumMap<TFCWood, RegistryObject<EntityType<CanoeEntity>>> TFC_CANOES = AlekiShipsHelper.mapOfKeys(
            TFCWood.class, tfcWood -> register("dugout_canoe/" + tfcWood.getSerializedName(),
                    EntityType.Builder.of(CanoeEntity::new, MobCategory.MISC).sized(1.125F, 0.625F)));

    public static final RegistryObject<EntityType<KayakEntity>> KAYAK_ENTITY = register("kayak",
            EntityType.Builder.of(KayakEntity::new, MobCategory.MISC).sized(0.79F, 0.625F));

    public static final RegistryObject<CompartmentType<TFCChestCompartmentEntity>> TFC_CHEST_COMPARTMENT_ENTITY = CompartmentType.register(
            registerCompartment("compartment_tfcchest",
                    CompartmentType.Builder.of(TFCChestCompartmentEntity::new, TFCChestCompartmentEntity::new,
                            MobCategory.MISC)), itemStack -> itemStack.is(FirmacivTags.Items.CHESTS));

    private static <E extends RowboatEntity> RegistryObject<EntityType<E>> registerRowboat(final TFCWood vanillaWood,
            final EntityType.Builder<E> builder) {
        return register("rowboat/" + vanillaWood.getSerializedName(), builder.sized(1.875F, 0.625F));
    }

    private static <E extends SloopEntity> RegistryObject<EntityType<E>> registerSloop(final TFCWood vanillaWood,
            final EntityType.Builder<E> builder) {
        return register("sloop/" + vanillaWood.getSerializedName(),
                builder.sized(3F, 0.75F).setTrackingRange(LARGE_VEHICLE_TRACKING).fireImmune());
    }

    private static <E extends SloopUnderConstructionEntity> RegistryObject<EntityType<E>> registerSloopConstruction(
            final TFCWood vanillaWood, final EntityType.Builder<E> builder) {
        return register("sloop_construction/" + vanillaWood.getSerializedName(),
                builder.sized(4F, 0.75F).setTrackingRange(LARGE_VEHICLE_TRACKING).fireImmune().noSummon());
    }

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