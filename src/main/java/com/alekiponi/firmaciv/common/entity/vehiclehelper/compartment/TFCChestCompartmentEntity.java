package com.alekiponi.firmaciv.common.entity.vehiclehelper.compartment;

import com.alekiponi.alekiships.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.alekiships.common.entity.vehiclehelper.compartment.vanilla.ChestCompartmentEntity;
import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TFCChestCompartmentEntity extends ChestCompartmentEntity {

    public static final int SLOT_AMOUNT = 18;

    public TFCChestCompartmentEntity(final EntityType<? extends TFCChestCompartmentEntity> entityType,
            final Level level) {
        super(entityType, level, SLOT_AMOUNT);
    }

    public TFCChestCompartmentEntity(final CompartmentType<? extends TFCChestCompartmentEntity> entityType,
            final Level level, final ItemStack itemStack) {
        super(entityType, level, SLOT_AMOUNT, itemStack);
    }

    @Override
    public void remove(final RemovalReason removalReason) {
        if (!this.level().isClientSide && removalReason.shouldDestroy()) {
            this.playSound(SoundEvents.WOOD_BREAK, 1, this.level().getRandom().nextFloat() * 0.1F + 0.9F);
        }
        super.remove(removalReason);
    }

    @Override
    public InteractionResult interact(final Player player, final InteractionHand hand) {
        final InteractionResult interactionResult = super.interact(player, hand);
        if (interactionResult.consumesAction()) {
            PiglinAi.angerNearbyPiglins(player, true);
        }

        return interactionResult;
    }

    @Override
    protected AbstractContainerMenu createMenu(final int id, final Inventory playerInventory) {
        return new RestrictedChestContainer(TFCContainerTypes.CHEST_9x2.get(), id, playerInventory, this, 2);
    }
}