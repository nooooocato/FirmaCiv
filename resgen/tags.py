from mcresources import ResourceManager

import constants


def generate(manager: ResourceManager):
    # Tags with all wood types
    manager.block_tag("canoe_component_blocks",
                      *[f"wood/canoe_component_block/{wood}" for wood in constants.TFC_WOODS])
    manager.block_tag("can_make_canoe_unrestricted",
                      *[f"tfc:wood/stripped_log/{wood}" for wood in constants.TFC_WOODS])
    manager.block_tag("alekiships:wooden_watercraft_frames",
                      *[f"wood/watercraft_frame/angled/{wood}" for wood in constants.TFC_WOODS],
                      *[f"wood/watercraft_frame/flat/{wood}" for wood in constants.TFC_WOODS])
    manager.entity_tag("dugout_canoes", *[f"dugout_canoe/{wood}" for wood in constants.TFC_WOODS])
    manager.entity_tag("alekiships:rowboats", *[f"rowboat/{wood}" for wood in constants.TFC_WOODS])
    manager.entity_tag("alekiships:sloops", *[f"sloop/{wood}" for wood in constants.TFC_WOODS])

    # Default hard woods for TFC
    manager.item_tag("hard_wood", "tfc:wood/planks/acacia", "tfc:wood/planks/ash", "tfc:wood/planks/aspen",
                     "tfc:wood/planks/birch", "tfc:wood/planks/blackwood", "tfc:wood/planks/chestnut",
                     "tfc:wood/planks/hickory", "tfc:wood/planks/maple", "tfc:wood/planks/oak",
                     "tfc:wood/planks/rosewood", "tfc:wood/planks/sycamore")

    # Compartment Entities
    manager.entity_tag("alekiships:compartments", "compartment_tfc_chest", "compartment_tfc_barrel")

    # Compartment placing tags
    manager.item_tag("alekiships:can_place_in_compartments", "#firmaciv:chests", "#tfc:anvils", "#tfc:barrels",
                     "#tfc:fired_large_vessels")
    manager.item_tag("alekiships:crafting_tables",
                     *[f"tfc:wood/planks/{wood}_workbench" for wood in constants.TFC_WOODS])
    manager.item_tag("chests", *[f"tfc:wood/chest/{wood}" for wood in constants.TFC_WOODS])

    # Vanilla mining tags
    manager.block_tag("minecraft:mineable/axe", "#firmaciv:canoe_component_blocks")

    # Plant breaking
    manager.entity_tag("alekiships:plants_that_get_mowed", "#tfc:plants")

    # TFC tags
    manager.block_tag("tfc:mineable_with_blunt_tool", "#firmaciv:canoe_component_blocks")
    manager.block_tag("tfc:mineable_with_sharp_tool", "firmaciv:thatch_roofing", "firmaciv:thatch_roofing_slab", "firmaciv:thatch_roofing_stairs")
    manager.item_tag("tfc:usable_on_tool_rack", "canoe_paddle", "kayak_paddle", "alekiships:oar", "kayak", "nav_clock",
                     "sextant", "barometer")

    # Carryon blacklist tags (as of writing carryon has a bug which means these are ignored)
    manager.block_tag("carryon:block_blacklist", "#firmaciv:canoe_component_blocks")
    manager.entity_tag("carryon:entity_blacklist", "firmaciv:kayak", "#firmaciv:dugout_canoes",
                       *[f"firmaciv:sloop_construction/{wood}" for wood in constants.TFC_WOODS])
