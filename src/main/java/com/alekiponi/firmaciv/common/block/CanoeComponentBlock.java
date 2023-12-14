package com.alekiponi.firmaciv.common.block;

import com.alekiponi.firmaciv.common.blockentity.CanoeComponentBlockEntity;
import com.alekiponi.firmaciv.common.blockentity.FirmacivBlockEntities;
import com.alekiponi.firmaciv.common.entity.CanoeEntity;
import com.alekiponi.firmaciv.common.entity.FirmacivEntities;
import com.alekiponi.firmaciv.util.FirmacivTags;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.registry.RegistryWood;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static com.alekiponi.firmaciv.common.block.FirmacivBlocks.CANOE_COMPONENT_BLOCKS;

public class CanoeComponentBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final IntegerProperty CANOE_CARVED = FirmacivBlockStateProperties.CANOE_CARVED;
    public static final EnumProperty<Shape> SHAPE = FirmacivBlockStateProperties.CANOE_SHAPE;
    private static final VoxelShape HALF_SHAPE = Block.box(0, 0, 0, 16, 9, 16);
    public final Supplier<? extends Block> strippedBlock;
    public final Supplier<? extends Item> lumberItem;
    public final RegistryWood wood;

    public CanoeComponentBlock(final Properties properties, final Supplier<? extends Item> lumberItem,
            final RegistryWood wood) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Z).setValue(CANOE_CARVED, 0)
                .setValue(SHAPE, Shape.STRAIGHT));
        this.strippedBlock = wood.getBlock(Wood.BlockType.STRIPPED_LOG);
        this.lumberItem = lumberItem;
        this.wood = wood;
    }

    public static Block getByStripped(final Block strippedLogBlock) {
        // TODO make a map of stripped logs -> canoe components?
        //noinspection OptionalGetWithoutIsPresent
        return CANOE_COMPONENT_BLOCKS.values().stream()
                .filter(registryObject -> registryObject.get().strippedBlock.get() == strippedLogBlock)
                .map(RegistryObject::get).findFirst().get();
    }

    /**
     * Checks if there is a valid canoe shape
     *
     * @param levelAccessor The level accessor
     * @param startBlockPos The starting block position
     * @return If there is a valid canoe shape
     */
    public static boolean isValidShape(final LevelReader levelAccessor, final BlockPos startBlockPos) {
        final BlockPattern canoePattern = BlockPatternBuilder.start().aisle("#", "#", "#").where('#',
                BlockInWorld.hasState(blockState -> blockState.is(FirmacivTags.Blocks.CAN_MAKE_CANOE) || blockState.is(
                        FirmacivTags.Blocks.CANOE_COMPONENT_BLOCKS))).build();
        return canoePattern.find(levelAccessor, startBlockPos) != null;
    }

    private static BlockPos getMiddleBlockPos(final Level pLevel, final BlockPos pPos,
            final Block canoeComponentBlock) {

        final Direction.Axis axis = pLevel.getBlockState(pPos).getValue(AXIS);

        final BlockPos blockPos0 = pPos.relative(axis, 2);
        final BlockPos blockPos1 = pPos.relative(axis, 1);
        final BlockPos blockPos2 = pPos.relative(axis, -1);
        final BlockPos blockPos3 = pPos.relative(axis, -2);

        if (pLevel.getBlockState(blockPos0).is(canoeComponentBlock) && pLevel.getBlockState(blockPos1)
                .is(canoeComponentBlock)) {
            return pPos.relative(axis, 1);
        }
        if (pLevel.getBlockState(blockPos1).is(canoeComponentBlock) && pLevel.getBlockState(blockPos2)
                .is(canoeComponentBlock)) {
            return pPos;
        }
        if (pLevel.getBlockState(blockPos2).is(canoeComponentBlock) && pLevel.getBlockState(blockPos3)
                .is(canoeComponentBlock)) {
            return pPos.relative(axis, -1);
        }

        return pPos;
    }

    private static boolean areValidBlockStates(final Level level, final BlockPos blockPos,
            final Block canoeComponentBlock) {

        final Direction.Axis axis = level.getBlockState(blockPos).getValue(AXIS);
        final BlockPos blockPos0 = blockPos.relative(axis, 2);
        final BlockPos blockPos1 = blockPos.relative(axis, 1);
        final BlockPos blockPos2 = blockPos.relative(axis, -1);
        final BlockPos blockPos3 = blockPos.relative(axis, -2);

        if (level.getBlockState(blockPos0).is(canoeComponentBlock) && level.getBlockState(blockPos1)
                .is(canoeComponentBlock)) {

            if (level.getBlockState(blockPos0).getValue(CANOE_CARVED) == 12 && level.getBlockState(blockPos1)
                    .getValue(CANOE_CARVED) == 12) {
                return true;
            }
        }
        if (level.getBlockState(blockPos1).is(canoeComponentBlock) && level.getBlockState(blockPos2)
                .is(canoeComponentBlock)) {

            if (level.getBlockState(blockPos1).getValue(CANOE_CARVED) == 12 && level.getBlockState(blockPos2)
                    .getValue(CANOE_CARVED) == 12) {
                return true;
            }
        }
        if (level.getBlockState(blockPos2).is(canoeComponentBlock) && level.getBlockState(blockPos3)
                .is(canoeComponentBlock)) {

            return level.getBlockState(blockPos2).getValue(CANOE_CARVED) == 12 && level.getBlockState(blockPos3)
                    .getValue(CANOE_CARVED) == 12;
        }

        return false;
    }

    public static void trySpawnCanoe(final Level level, final BlockPos blockPos, final Block canoeComponentBlock) {

        if (!areValidBlockStates(level, blockPos, canoeComponentBlock)) {
            return;
        }

        final BlockPattern.BlockPatternMatch canoePattern = createCanoeFull(canoeComponentBlock).find(level, blockPos);

        if (canoePattern == null) return;

        final Direction.Axis axis = level.getBlockState(blockPos).getValue(AXIS);
        final BlockPos middleBlockPos = getMiddleBlockPos(level, blockPos, canoeComponentBlock);

        for (int i = 0; i < createCanoeFull(canoeComponentBlock).getHeight(); ++i) {
            final BlockInWorld patternBlock = canoePattern.getBlock(0, i, 0);
            level.setBlock(patternBlock.getPos(), Blocks.AIR.defaultBlockState(), 2);
            level.levelEvent(2001, patternBlock.getPos(), Block.getId(patternBlock.getState()));
        }

        final CanoeComponentBlock ccb = (CanoeComponentBlock) canoeComponentBlock;

        final CanoeEntity canoe = FirmacivEntities.CANOES.get(ccb.wood).get().create(level);

        // Failed to create a canoe will crash with NPE
        if (canoe == null) return;

        {
            // TODO this seems weird, why are we using the axis to figure out if the entity should be rotated?
            final float rotation = axis == Direction.Axis.X ? 90 : 0;
            canoe.moveTo(middleBlockPos.getX() + 0.5, middleBlockPos.getY() + 0.05, middleBlockPos.getZ() + 0.5,
                    rotation, 0);
        }

        level.addFreshEntity(canoe);

        for (final ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class,
                canoe.getBoundingBox().inflate(5))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, canoe);
        }

        for (int l = 0; l < createCanoeFull(canoeComponentBlock).getHeight(); ++l) {
            final BlockInWorld patternBlock = canoePattern.getBlock(0, l, 0);
            level.blockUpdated(patternBlock.getPos(), Blocks.AIR);
        }
    }

    private static BlockPattern createCanoeFull(final Block canoeComponentBlock) {
        return BlockPatternBuilder.start().aisle("#", "#", "#")
                .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(canoeComponentBlock))).build();
    }

    @Override
    public void animateTick(final BlockState blockState, final Level level, final BlockPos blockPos,
            final RandomSource randomSource) {

        if (blockState.getValue(CANOE_CARVED) == 11) {
            final double x = blockPos.getX() + randomSource.nextDouble();
            final double y = blockPos.getY() + randomSource.nextDouble();
            final double z = blockPos.getZ() + randomSource.nextDouble();

            for (int i = 0; i < randomSource.nextInt(3); ++i) {
                level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, x, y, z, 0,
                        0.1 + randomSource.nextDouble() / 8, 0);
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
        return new CanoeComponentBlockEntity(blockPos, blockState);
    }

    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(final BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level level, final BlockState blockState,
            final BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, FirmacivBlockEntities.CANOE_COMPONENT_BLOCK_ENTITY.get(),
                CanoeComponentBlockEntity::serverTick);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(final BlockState blockState, final BlockGetter blockGetter, final BlockPos blockPos,
            final CollisionContext collisionContext) {
        return switch (blockState.getValue(CANOE_CARVED)) {
            case 0, 1, 2, 3 -> Shapes.block();
            default -> HALF_SHAPE;
        };
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext placeContext) {
        return this.defaultBlockState().setValue(AXIS, placeContext.getHorizontalDirection().getAxis());
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder.add(AXIS).add(CANOE_CARVED).add(SHAPE));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(final BlockState blockState, final Direction direction,
            final BlockState neighborState, final LevelAccessor levelAccessor, final BlockPos blockPos,
            final BlockPos neighborPos) {

        // Not the same component block so ignore
        if (!neighborState.is(this)) return blockState;

        final Direction.Axis axis = blockState.getValue(AXIS);

        // Update didn't come from a direction we care about
        if (axis != direction.getAxis()) return blockState;

        // Neighbor isn't on our axis
        if (axis != neighborState.getValue(AXIS)) return blockState;

        final BlockState otherNeighbor = levelAccessor.getBlockState(blockPos.relative(direction.getOpposite()));

        // Only need to handle the neighbor
        if (!otherNeighbor.is(this)) {

            final Shape shape;
            if (axis == Direction.Axis.X) {
                shape = direction.getStepX() > 0 ? Shape.END_LEFT : Shape.END_RIGHT;
            } else {
                assert axis == Direction.Axis.Z : "Axis must be Z";
                shape = direction.getStepZ() > 0 ? Shape.END_RIGHT : Shape.END_LEFT;
            }

            return blockState.setValue(SHAPE, shape);
        }

        return super.updateShape(blockState, direction, neighborState, levelAccessor, blockPos, neighborPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(final BlockState blockState, final Level level, final BlockPos blockPos,
            final BlockState newState, final boolean movedByPiston) {
        super.onRemove(blockState, level, blockPos, newState, movedByPiston);

        if (newState.is(this)) return;

        final Direction.Axis axis = blockState.getValue(AXIS);

        final Shape shape = blockState.getValue(SHAPE);

        // Prevent break propagation to the left if it's an end
        if (shape != Shape.END_LEFT) {
            final BlockPos leftBlockPos = blockPos.relative(axis, axis == Direction.Axis.X ? -1 : 1);
            final BlockState leftBlockState = level.getBlockState(leftBlockPos);
            if (leftBlockState.is(this) && leftBlockState.getValue(AXIS) == axis) {
                level.destroyBlock(leftBlockPos, true);
            }
        }

        // Prevent break propagation to the right if it's an end
        if (shape != Shape.END_RIGHT) {
            final BlockPos rightBlockPos = blockPos.relative(axis, axis == Direction.Axis.X ? 1 : -1);
            final BlockState rightBlockState = level.getBlockState(rightBlockPos);
            if (rightBlockState.is(this) && rightBlockState.getValue(AXIS) == axis) {
                level.destroyBlock(rightBlockPos, true);
            }
        }
    }

    public enum Shape implements StringRepresentable {
        STRAIGHT,
        END_LEFT,
        END_RIGHT;

        @Override
        public String getSerializedName() {
            return switch (this) {
                case STRAIGHT -> "straight";
                case END_LEFT -> "end_left";
                case END_RIGHT -> "end_right";
            };
        }
    }
}