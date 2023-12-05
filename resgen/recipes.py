from mcresources import ResourceManager
from mcresources.type_definitions import ResourceIdentifier

import constants


def generate(rm: ResourceManager):
    def disableRecipe(name_parts: ResourceIdentifier):
        rm.recipe(name_parts, None, {}, conditions="forge:false")

    # Disable TFC boat recipes
    for woodType in constants.TFC_WOODS.keys():
        disableRecipe(f"tfc:crafting/wood/{woodType}_boat")

    # Disable vanilla boat recipes
    for woodType in ["acacia", "birch", "cherry", "dark_oak", "jungle", "mangrove", "oak", "spruce"]:
        disableRecipe(f"minecraft:{woodType}_boat")
        disableRecipe(f"minecraft:{woodType}_chest_boat")
    # Bamboo raft as well
    disableRecipe("minecraft:bamboo_raft")

    rm.crafting_shaped("minecraft:compass", ["X", "Y", "Z"], {
        "X": {
            "item": "tfc:lens"
        },
        "Y": {
            "tag": "tfc:magnetic_rocks"
        },
        "Z": {
            "item": "minecraft:bowl"
        }}, "firmaciv:firmaciv_compass")

    rm.crafting_shaped("crafting/watercraft_frame_angled", [" LL", "LLL", "LL "], {"L": "#tfc:lumber"},
                       "firmaciv:watercraft_frame_angled").with_advancement("firmaciv:watercraft_frame_angled")

    rm.crafting_shapeless("crafting/barometer",
                          ["firmaciv:unfinished_barometer", "tfc:brass_mechanisms", "#tfc:glass_bottles",
                           {"type": "tfc:fluid_item",
                            "fluid_ingredient": {
                                "ingredient": "minecraft:water",
                                "amount": 100
                            }}],
                          "firmaciv:barometer").with_advancement("firmaciv:barometer")
