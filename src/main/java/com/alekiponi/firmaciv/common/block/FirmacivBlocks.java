package com.alekiponi.firmaciv.common.block;

import com.alekiponi.alekiroofs.SquaredAngleBlock;
import com.alekiponi.alekiships.common.block.AngledBoatFrameBlock;
import com.alekiponi.alekiships.common.block.FlatBoatFrameBlock;
import com.alekiponi.alekiships.util.CommonHelper;
import com.alekiponi.firmaciv.Firmaciv;
import com.alekiponi.firmaciv.common.item.FirmacivItems;
import com.alekiponi.firmaciv.util.TFCWood;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.function.Supplier;

public final class FirmacivBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Firmaciv.MOD_ID);

    public static final EnumMap<TFCWood, RegistryObject<CanoeComponentBlock>> CANOE_COMPONENT_BLOCKS = CommonHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerBlock("wood/canoe_component_block/" + tfcWood.getSerializedName(),
                    () -> new CanoeComponentBlock(
                            BlockBehaviour.Properties.copy(tfcWood.wood.getBlock(Wood.BlockType.STRIPPED_LOG).get())
                                    .mapColor(tfcWood.wood.woodColor()).noOcclusion(), tfcWood)));

    public static final EnumMap<Wood, RegistryObject<SquaredAngleBlock>> WOOD_ROOFING = CommonHelper.mapOfKeys(
            Wood.class, tfcWood -> registerBlockWithItem("wood/" + tfcWood.getSerializedName() + "_roofing",
                    () -> new SquaredAngleBlock(Blocks.ACACIA_STAIRS.defaultBlockState(),
                            BlockBehaviour.Properties.copy(tfcWood.getBlock(Wood.BlockType.STAIRS).get())
                                    .mapColor(tfcWood.woodColor()).noOcclusion())));

    public static final RegistryObject<Block> THATCH_ROOFING = registerBlockWithItem("thatch_roofing",
            () -> new AngledThatchRoofingBlock(Blocks.ACACIA_STAIRS.defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never)
                            .sound(TFCSounds.THATCH)));

    public static final RegistryObject<Block> THATCH_ROOFING_STAIRS = registerBlock("thatch_roofing_stairs",
            () -> new StairThatchRoofingBlock(Blocks.ACACIA_STAIRS.defaultBlockState(),
                    BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never)
                            .sound(TFCSounds.THATCH)));

    public static final RegistryObject<Block> THATCH_ROOFING_SLAB = registerBlockWithItem("thatch_roofing_slab",
            () -> new FlatThatchRoofingBlock(
                    BlockBehaviour.Properties.of().strength(0.6F, 0.4F).noOcclusion().isViewBlocking(TFCBlocks::never)
                            .sound(TFCSounds.THATCH)));

    public static final RegistryObject<AngledBoatFrameBlock> BOAT_FRAME_ANGLED = registerBlockWithItem(
            "watercraft_frame_angled",
            () -> new FirmacivAngledBoatFrameBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));

    public static final EnumMap<TFCWood, RegistryObject<FirmacivAngledWoodenBoatFrameBlock>> WOODEN_BOAT_FRAME_ANGLED = CommonHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerBlock("wood/watercraft_frame/angled/" + tfcWood.getSerializedName(),
                    () -> new FirmacivAngledWoodenBoatFrameBlock(tfcWood,
                            BlockBehaviour.Properties.copy(BOAT_FRAME_ANGLED.get()))));

    public static final RegistryObject<FlatBoatFrameBlock> BOAT_FRAME_FLAT = registerBlockWithItem(
            "watercraft_frame_flat",
            () -> new FirmacivFlatBoatFrameBlock(BlockBehaviour.Properties.copy(BOAT_FRAME_ANGLED.get())));

    public static final EnumMap<TFCWood, RegistryObject<FirmacivFlatWoodenBoatFrameBlock>> WOODEN_BOAT_FRAME_FLAT = CommonHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerBlock("wood/watercraft_frame/flat/" + tfcWood.getSerializedName(),
                    () -> new FirmacivFlatWoodenBoatFrameBlock(tfcWood,
                            BlockBehaviour.Properties.copy(BOAT_FRAME_FLAT.get()))));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
        RegistryObject<T> blockRegistryObject = BLOCKS.register(name, block);
        registerBlockItem(name, blockRegistryObject);
        return blockRegistryObject;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        FirmacivItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}