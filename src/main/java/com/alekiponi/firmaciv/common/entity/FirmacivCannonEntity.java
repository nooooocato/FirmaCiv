package com.alekiponi.firmaciv.common.entity;

import com.alekiponi.alekiships.common.entity.CannonEntity;
import com.alekiponi.alekiships.common.entity.CannonballEntity;
import com.alekiponi.alekiships.common.item.AlekiShipsItems;
import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FirmacivCannonEntity extends CannonEntity {
    public static final String PAPER_KEY = "paper";
    public static final String GUNPOWDER_KEY = "gunpowder";
    private static final ItemStack CANNONBALL = new ItemStack(AlekiShipsItems.CANNONBALL.get());
    private static final ItemStack GUNPOWDER = new ItemStack(Items.GUNPOWDER);
    private static final ItemStack UNREFINED_PAPER = new ItemStack(TFCItems.UNREFINED_PAPER.get());
    private static final EntityDataAccessor<ItemStack> DATA_ID_PAPER_ITEM = SynchedEntityData.defineId(
            FirmacivCannonEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_GUNPOWDER_ITEM = SynchedEntityData.defineId(
            FirmacivCannonEntity.class, EntityDataSerializers.ITEM_STACK);

    public FirmacivCannonEntity(EntityType<? extends CannonEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_PAPER_ITEM, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_GUNPOWDER_ITEM, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPaper(ItemStack.of(compoundTag.getCompound(PAPER_KEY)));
        this.setGunpowder(ItemStack.of(compoundTag.getCompound(GUNPOWDER_KEY)));
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        compoundTag.put(PAPER_KEY, this.getPaper().save(new CompoundTag()));
        compoundTag.put(GUNPOWDER_KEY, this.getGunpowder().save(new CompoundTag()));
    }

    public ItemStack getPaper() {
        return this.entityData.get(DATA_ID_PAPER_ITEM);
    }

    protected void setPaper(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_PAPER_ITEM, itemStack.copy());
    }

    public ItemStack getGunpowder() {
        return this.entityData.get(DATA_ID_GUNPOWDER_ITEM);
    }

    protected void setGunpowder(final ItemStack itemStack) {
        this.entityData.set(DATA_ID_GUNPOWDER_ITEM, itemStack.copy());
    }

    @Override
    protected InteractionResult insertItem(final ItemStack itemStack) {
        if (itemStack.is(Items.GUNPOWDER)) {
            if (this.getGunpowder().isEmpty()) {
                this.setGunpowder(itemStack.split(1));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        if (getGunpowder().isEmpty()) {
            return InteractionResult.PASS;
        }
        if (itemStack.is(TFCItems.UNREFINED_PAPER.get())) {
            if (this.getPaper().isEmpty()) {
                this.setPaper(itemStack.split(1));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        if (getPaper().isEmpty()) {
            return InteractionResult.PASS;
        }
        if (itemStack.is(AlekiShipsItems.CANNONBALL.get())) {
            if (this.getCannonball().isEmpty()) {
                this.setCannonball(itemStack.split(1));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        }
        if (getCannonball().isEmpty()) {
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isLoaded() {
        return !this.getCannonball().isEmpty() && !this.getPaper().isEmpty() && !this.getGunpowder().isEmpty();
    }

    @Override
    public ItemStack nextRequiredItem() {
        if (getGunpowder().isEmpty()) {
            return GUNPOWDER;
        }
        if (getPaper().isEmpty()) {
            return UNREFINED_PAPER;
        }
        return CANNONBALL;
    }

    @Override
    public void fire() {
        this.setPaper(ItemStack.EMPTY);
        this.setGunpowder(ItemStack.EMPTY);
        super.fire();
    }

}
