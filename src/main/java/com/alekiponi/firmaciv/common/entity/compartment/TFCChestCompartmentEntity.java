package com.alekiponi.firmaciv.common.entity.compartment;

import com.alekiponi.alekiships.common.entity.vehiclehelper.CompartmentType;
import com.alekiponi.alekiships.common.entity.vehiclehelper.compartment.vanilla.ChestCompartmentEntity;
import net.dries007.tfc.common.blocks.wood.TFCChestBlock;
import net.dries007.tfc.common.container.RestrictedChestContainer;
import net.dries007.tfc.common.container.TFCContainerTypes;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

/**
 * This compartment models a {@link TFCChestBlock} and stores its contents.
 */
public class TFCChestCompartmentEntity extends ChestCompartmentEntity {

    public static final int SLOT_AMOUNT = 18;
    private static final EntityDataAccessor<BlockState> DATA_ID_DISPLAY_BLOCK = SynchedEntityData.defineId(
            TFCChestCompartmentEntity.class, EntityDataSerializers.BLOCK_STATE);

    public TFCChestCompartmentEntity(final CompartmentType<? extends TFCChestCompartmentEntity> entityType,
            final Level level) {
        super(entityType, level, SLOT_AMOUNT);
    }

    public TFCChestCompartmentEntity(final CompartmentType<? extends TFCChestCompartmentEntity> entityType,
            final Level level, final ItemStack itemStack) {
        super(entityType, level, SLOT_AMOUNT, itemStack);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof TFCChestBlock chestBlock) {
                this.entityData.set(DATA_ID_DISPLAY_BLOCK, chestBlock.defaultBlockState());
            }
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_DISPLAY_BLOCK, Blocks.AIR.defaultBlockState());
    }

    @Override
    protected AbstractContainerMenu createMenu(final int id, final Inventory playerInventory) {
        return new RestrictedChestContainer(TFCContainerTypes.CHEST_9x2.get(), id, playerInventory, this,
                SLOT_AMOUNT / 9);
    }

    @Override
    public ItemStack getDropStack() {
        return new ItemStack(this.getBlockState().getBlock());
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getBlockState().getBlock());
    }

    @Override
    protected void addAdditionalSaveData(final CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("heldBlock", NbtUtils.writeBlockState(this.getBlockState()));
    }

    @Override
    protected void readAdditionalSaveData(final CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(DATA_ID_DISPLAY_BLOCK, NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK),
                compoundTag.getCompound("heldBlock")));
    }

    /**
     * This is stupid but this is pretty much how TFC did this so our hands are sort of tied
     *
     * @return The ResourceLocation the chest texture is found
     */
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getTextureLocation() {
        return Helpers.identifier(
                "entity/chest/normal/" + ((TFCChestBlock) this.getBlockState().getBlock()).getTextureLocation());
    }

    public String descriptionId() {
        return this.getBlockState().getBlock().getDescriptionId();
    }

    /**
     * Silly helper to prevent code duplication
     */
    private BlockState getBlockState() {
        return this.entityData.get(DATA_ID_DISPLAY_BLOCK);
    }
}