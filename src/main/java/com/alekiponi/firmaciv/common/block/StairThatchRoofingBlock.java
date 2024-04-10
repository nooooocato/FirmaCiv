package com.alekiponi.firmaciv.common.block;

import net.dries007.tfc.common.blocks.*;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StairThatchRoofingBlock extends StairBlock implements IForgeBlockExtension, ISlowEntities, IBlockRain  {
    public static final FluidProperty FLUID;
    private final ExtendedProperties properties;
    public StairThatchRoofingBlock(BlockState state, Properties properties) {
        super(state, properties);
        this.properties = ExtendedProperties.of(properties);
    }

    @Override
    public float slowEntityFactor(BlockState state) {
        return ((FluidProperty.FluidKey)state.getValue(this.getFluidProperty())).getFluid() == Fluids.EMPTY ? ((Double)TFCConfig.SERVER.thatchMovementMultiplier.get()).floatValue() : 1.0F;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    public ExtendedProperties getExtendedProperties() {
        return this.properties;
    }

    public FluidState getFluidState(BlockState state) {
        return super.getFluidState(state);
    }

    public FluidProperty getFluidProperty() {
        return FLUID;
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(new Property[]{FLUID}));
    }

    static {
        FLUID = TFCBlockStateProperties.WATER;
    }
}
