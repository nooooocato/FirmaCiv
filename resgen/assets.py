from mcresources import ResourceManager

import blockStates
import constants
import lootTables


def generate(rm: ResourceManager):
    # Generate atlases for rowboats and our sloops
    rm.atlas("alekiships:rowboats", *[{"type": "alekiships:boat_texture_generator",
                                       "woodTexture": f"firmaciv:entity/watercraft/rowboat/{wood}",
                                       "paintPrefix": "alekiships:entity/watercraft/rowboat/paint"} for wood in
                                      constants.TFC_WOODS])
    rm.atlas("alekiships:sloops", *[{"type": "alekiships:boat_texture_generator",
                                     "woodTexture": f"firmaciv:entity/watercraft/sloop/{wood}",
                                     "paintPrefix": "alekiships:entity/watercraft/sloop/paint"} for wood in
                                    constants.TFC_WOODS])

    for wood in constants.TFC_WOODS:
        # Generate models from templates
        for progress in ["first", "second", "third", "fourth"]:
            # Slab frame models
            rm.block_model(f"wood/watercraft_frame/flat/{wood}/{progress}",
                           {"plank": f"tfc:block/wood/planks/{wood}"},
                           f"alekiships:block/watercraft_frame/flat/template/{progress}")

            for shape in ["straight", "inner", "outer"]:
                rm.block_model(f"wood/watercraft_frame/angled/{wood}/{shape}/{progress}",
                               {"plank": f"tfc:block/wood/planks/{wood}"},
                               f"alekiships:block/watercraft_frame/angled/template/{shape}/{progress}")

        rm.blockstate_multipart(f"wood/watercraft_frame/flat/{wood}",
                                *blockStates.getWoodFrameFlatMultipart(wood)).with_lang(
            f"{constants.langify(wood)} Flat Shipwright's Scaffolding").with_block_loot(
            *lootTables.boat_frame_flat(wood))

        rm.blockstate_multipart(f"wood/watercraft_frame/angled/{wood}",
                                *blockStates.getWoodFrameMultipart(wood)).with_lang(
            f"{constants.langify(wood)} Sloped Shipwright's Scaffolding").with_block_loot(
            *lootTables.boat_frame(wood))

        # Canoe components now
        canoe_component_textures = {"0": f"tfc:block/wood/stripped_log/{wood}",
                                    "1": f"tfc:block/wood/stripped_log_top/{wood}",
                                    "particle": f"tfc:block/wood/stripped_log/{wood}"}

        # Models that are shared by the end and middle states
        for n in range(8):
            rm.block_model(f"wood/canoe_component_block/{wood}/all/{n}", canoe_component_textures,
                           f"firmaciv:block/canoe_component_block/template/all/{n}")

        # End and Middle only models
        for n in range(8, 13):
            rm.block_model(f"wood/canoe_component_block/{wood}/end/{n}", canoe_component_textures,
                           f"firmaciv:block/canoe_component_block/template/end/{n}")
            rm.block_model(f"wood/canoe_component_block/{wood}/middle/{n}", canoe_component_textures,
                           f"firmaciv:block/canoe_component_block/template/middle/{n}")
            rm.blockstate(f"wood/canoe_component_block/{wood}",
                          variants=blockStates.canoe_component(wood)).with_lang(
                f"{constants.langify(wood)} Canoe Component").with_block_loot(f"tfc:wood/lumber/{wood}")

    # Basic frame
    rm.blockstate("watercraft_frame_angled", variants=blockStates.angledWaterCraftFrame).with_lang(
        "Sloped Shipwright's Scaffolding").with_block_loot("firmaciv:watercraft_frame_angled")

    # Need to manually make the models
    for shape in ["inner", "straight", "outer"]:
        rm.block_model(f"watercraft_frame/angled/{shape}", parent=f"alekiships:block/watercraft_frame/angled/{shape}",
                       textures={
                           "particle": "tfc:block/wood/planks/maple",
                           "frame": "tfc:block/wood/planks/maple"
                       })
    rm.item_model("watercraft_frame_angled", parent="firmaciv:block/watercraft_frame/angled/straight",
                  no_textures=True)

    # Basic flat frame
    rm.blockstate("watercraft_frame_flat", "firmaciv:block/watercraft_frame/flat/frame").with_lang(
        "Flat Shipwright's Scaffolding").with_block_loot("firmaciv:watercraft_frame_flat")

    # Need to manually make the models
    rm.block_model("watercraft_frame/flat/frame", parent="alekiships:block/watercraft_frame/flat/frame",
                   textures={
                       "particle": "tfc:block/wood/planks/maple",
                       "frame": "tfc:block/wood/planks/maple"
                   })
    rm.item_model("watercraft_frame_flat", parent="firmaciv:block/watercraft_frame/flat/frame",
                  no_textures=True)

    rm.block("thatch_roofing_slab").with_lang("Thatch Slab")
    rm.block("thatch_roofing_stairs").with_lang("Angled Thatch")
    rm.block("thatch_roofing").with_lang("Angled Thatch")

    # Items with generated models
    rm.item("unfinished_barometer").with_item_model().with_lang("Unfinished Barometer")
    rm.item("unfinished_nav_clock").with_item_model().with_lang("Unfinished Navigator's Timepiece")
    rm.item("unfinished_sextant").with_item_model().with_lang("Unfinished Sextant")
    rm.item("cannon_barrel").with_item_model().with_lang("Cannon Barrel")
    rm.item("small_triangular_sail").with_item_model().with_lang("Small Sail")
    rm.item("medium_triangular_sail").with_item_model().with_lang("Medium Sail")
    rm.item("large_triangular_sail").with_item_model().with_lang("Large Sail")
    rm.item("rope_coil").with_item_model().with_lang("Jute Rope")

    rm.item_model("cannon", parent="alekiships:item/cannon", no_textures=True).with_lang("Cannon")

    rm.item("sloop_icon_only").with_item_model().with_lang("ICON ONLY")
    rm.item("canoe_icon_only").with_item_model().with_lang("ICON ONLY")
    rm.item("canoe_with_paddle_icon_only").with_item_model().with_lang("ICON ONLY")
    rm.item("kayak_with_paddle_icon_only").with_item_model().with_lang("ICON ONLY")
    rm.item("rowboat_icon_only").with_item_model().with_lang("ICON ONLY")

    rm.item("copper_bolt").with_item_model().with_lang("Copper Bolt")
    rm.item("kayak").with_item_model().with_lang("Kayak")
    rm.item("large_waterproof_hide").with_item_model().with_lang("Large Waterproof Hide")
    rm.item("nav_toolkit").with_item_model().with_lang("Navigator's Toolkit")

    # Items with custom models
    rm.item("barometer").with_lang("Barometer")
    rm.item("sextant").with_lang("Sextant")
    rm.item("nav_clock").with_lang("Navigator's Timepiece")
    rm.item("firmaciv_compass").with_lang("Compass (Declination: True North)")

    rm.item("kayak_paddle").with_lang("Kayak Paddle")
    rm.item("canoe_paddle").with_lang("Canoe Paddle")

    for material in constants.ROOF_MATERIALS:
        true = True
        roofing = {
            "facing=east,shape=inner_left": {
                "model": f"firmaciv:block/wood/{material}_roofing_inner",
                "y": 270,
                "uvlock": true
            },
            "facing=east,shape=inner_right": {
                "model": f"firmaciv:block/wood/{material}_roofing_inner"
            },
            "facing=east,shape=outer_left": {
                "model": f"firmaciv:block/wood/{material}_roofing_outer",
                "y": 270,
                "uvlock": true
            },
            "facing=east,shape=outer_right": {
                "model": f"firmaciv:block/wood/{material}_roofing_outer"
            },
            "facing=east,shape=straight": {
                "model": f"firmaciv:block/wood/{material}_roofing"
            },
            "facing=north,shape=inner_left": {
                "model": f"firmaciv:block/wood/{material}_roofing_inner",
                "y": 180,
                "uvlock": true
            },
            "facing=north,shape=inner_right": {
                "model": f"firmaciv:block/wood/{material}_roofing_inner",
                "y": 270,
                "uvlock": true
            },
            "facing=north,shape=outer_left": {
                "model": f"firmaciv:block/wood/{material}_roofing_outer",
                "y": 180,
                "uvlock": true
            },
            "facing=north,shape=outer_right": {
                "model": f"firmaciv:block/wood/{material}_roofing_outer",
                "y": 270,
                "uvlock": true
            },
            "facing=north,shape=straight": {
                "model": f"firmaciv:block/wood/{material}_roofing",
                "y": 270,
                "uvlock": true
            },
            "facing=south,shape=inner_left": {
                "model": f"firmaciv:block/wood/{material}_roofing_inner"
            },
            "facing=south,shape=inner_right": {
                "model": f"firmaciv:block/wood/{material}_roofing_inner",
                "y": 90,
                "uvlock": true
            },
            "facing=south,shape=outer_left": {
                "model": f"firmaciv:block/wood/{material}_roofing_outer"
            },
            "facing=south,shape=outer_right": {
                "model": f"firmaciv:block/wood/{material}_roofing_outer",
                "y": 90,
                "uvlock": true
            },
            "facing=south,shape=straight": {
                "model": f"firmaciv:block/wood/{material}_roofing",
                "y": 90,
                "uvlock": true
            },
            "facing=west,shape=inner_left": {
                "model": f"firmaciv:block/wood/{material}_roofing_inner",
                "y": 90,
                "uvlock": true
            },
            "facing=west,shape=inner_right": {
                "model": f"firmaciv:block/wood/{material}_roofing_inner",
                "y": 180,
                "uvlock": true
            },
            "facing=west,shape=outer_left": {
                "model": f"firmaciv:block/wood/{material}_roofing_outer",
                "y": 90,
                "uvlock": true
            },
            "facing=west,shape=outer_right": {
                "model": f"firmaciv:block/wood/{material}_roofing_outer",
                "y": 180,
                "uvlock": true
            },
            "facing=west,shape=straight": {
                "model": f"firmaciv:block/wood/{material}_roofing",
                "y": 180,
                "uvlock": true
            }
        }

        rm.blockstate(f"wood/{material}_roofing", variants=roofing).with_lang(f"{constants.langify(material)} Roofing").with_block_loot(f"firmaciv:wood/{material}_roofing")

        texture = material+""

        generateRoofModels(material, texture, rm)


def generateRoofModels(material, texture, rm: ResourceManager):
    for state in ["_inner", "_outer", ""]:
        rm.block_model(f"wood/{material}_roofing{state}",
                       textures={"bottom": f"tfc:block/wood/planks/{texture}","top": f"tfc:block/wood/planks/{texture}","side": f"tfc:block/wood/planks/{texture}"},
                       parent=f"alekiroofs:block/roofing{state}")

    rm.item_model(f"wood/{material}_roofing", parent=f"firmaciv:block/wood/{material}_roofing", no_textures=True)
