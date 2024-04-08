package com.alekiponi.firmaciv.common.block;

import com.alekiponi.alekiships.common.block.AngledBoatFrameBlock;
import com.alekiponi.alekiships.common.block.FlatBoatFrameBlock;
import com.alekiponi.alekiships.util.AlekiShipsHelper;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.EnumMap;
import java.util.function.Supplier;

public final class FirmacivBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Firmaciv.MOD_ID);

    public static final EnumMap<TFCWood, RegistryObject<CanoeComponentBlock>> CANOE_COMPONENT_BLOCKS = AlekiShipsHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerBlock("wood/canoe_component_block/" + tfcWood.getSerializedName(),
                    () -> new CanoeComponentBlock(
                            BlockBehaviour.Properties.copy(tfcWood.wood.getBlock(Wood.BlockType.STRIPPED_LOG).get())
                                    .mapColor(tfcWood.wood.woodColor()).noOcclusion(), tfcWood)));

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
            () -> new AngledBoatFrameBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));

    public static final EnumMap<TFCWood, RegistryObject<AngledWoodenBoatFrameBlock>> WOODEN_BOAT_FRAME_ANGLED = AlekiShipsHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerBlock("wood/watercraft_frame_angled/" + tfcWood.getSerializedName(),
                    () -> new AngledWoodenBoatFrameBlock(tfcWood,
                            BlockBehaviour.Properties.copy(BOAT_FRAME_ANGLED.get()))));

    public static final RegistryObject<FlatBoatFrameBlock> BOAT_FRAME_FLAT = registerBlockWithItem(
            "watercraft_frame_flat",
            () -> new FlatBoatFrameBlock(BlockBehaviour.Properties.copy(BOAT_FRAME_ANGLED.get())));

    public static final EnumMap<TFCWood, RegistryObject<FlatWoodenBoatFrameBlock>> WOODEN_BOAT_FRAME_FLAT = AlekiShipsHelper.mapOfKeys(
            TFCWood.class, tfcWood -> registerBlock("wood/watercraft_frame_flat/" + tfcWood.getSerializedName(),
                    () -> new FlatWoodenBoatFrameBlock(tfcWood,
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

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
