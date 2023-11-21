package com.hyperdash.firmaciv.block.custom;

import com.hyperdash.firmaciv.block.FirmacivBlockStateProperties;
import com.hyperdash.firmaciv.block.FirmacivBlocks;
import com.hyperdash.firmaciv.block.blockentity.custom.WatercraftFrameBlockEntity;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.PitKilnBlockEntity;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;

public class AngledWatercraftFrameBlock extends SquaredAngleBlock implements EntityBlock {

    public static final IntegerProperty FRAME_PROCESSED = FirmacivBlockStateProperties.FRAME_PROCESSED_8;
    @Deprecated // Forge: Use the other constructor that takes a Supplier
    public AngledWatercraftFrameBlock(BlockState pBaseState, BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, Half.BOTTOM).setValue(SHAPE, StairsShape.STRAIGHT).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pLevel.getBlockState(pPos.above()).is(FirmacivBlocks.OARLOCK.get())){
            pLevel.destroyBlock(pPos.above(), true);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            BlockEntity thisBlockEntity = level.getBlockEntity(pos);
            if (thisBlockEntity instanceof WatercraftFrameBlockEntity frameBlockEntity) {
                ItemStack held = player.getItemInHand(hand);
                Item item = held.getItem();
                int processState = state.getValue(FRAME_PROCESSED);
                if(processState < 4){
                    level.setBlock(pos, (BlockState)state.setValue(FRAME_PROCESSED, processState + 1), 10);
                    frameBlockEntity.addPlankItems(held.split(1), processState + 1);
                    level.playSound(player, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1f, level.getRandom().nextFloat() * 0.1F + 0.9F);
                } if (processState > 4 && processState < 8){
                    level.setBlock(pos, (BlockState)state.setValue(FRAME_PROCESSED, processState + 1), 10);
                    frameBlockEntity.addBoltItems(held.split(1), processState + 1);
                    level.playSound(player, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.5f, level.getRandom().nextFloat() * 0.1F + 0.9F);
                } else if(held.isEmpty()){
                    NonNullList<ItemStack> plankItems = frameBlockEntity.getPlankItems();
                    NonNullList<ItemStack> boltItems = frameBlockEntity.getBoltItems();
                    ItemStack dropStack = ItemStack.EMPTY;
                    if (processState >= 5) {
                        dropStack = ((ItemStack)boltItems.get(processState - 4)).copy();
                        frameBlockEntity.deleteBoltItems(processState - 4);
                    } else {
                        dropStack = ((ItemStack)plankItems.get(processState)).copy();
                        frameBlockEntity.deletePlankItems(processState);
                    }
                    if (!dropStack.isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(player, dropStack);
                    }
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext).setValue(FRAME_PROCESSED, 0);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WatercraftFrameBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public boolean triggerEvent(BlockState pState, Level pLevel, BlockPos pPos, int pId, int pParam) {
        super.triggerEvent(pState, pLevel, pPos, pId, pParam);
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        return blockentity == null ? false : blockentity.triggerEvent(pId, pParam);
    }

    @Override
    @javax.annotation.Nullable
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        return blockentity instanceof MenuProvider ? (MenuProvider)blockentity : null;
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
        return pClientType == pServerType ? (BlockEntityTicker<A>)pTicker : null;
    }


}
