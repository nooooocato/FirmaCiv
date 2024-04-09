from enum import Enum, auto

from mcresources import ResourceManager, utils

from constants import METALS


class Size(Enum):
    tiny = auto()
    very_small = auto()
    small = auto()
    normal = auto()
    large = auto()
    very_large = auto()
    huge = auto()


class Weight(Enum):
    very_light = auto()
    light = auto()
    medium = auto()
    heavy = auto()
    very_heavy = auto()


def generate(rm: ResourceManager):
    copper = METALS["copper"]
    brass = METALS["brass"]
    cast_iron = METALS["cast_iron"]
    steel = METALS["steel"]
    item_heat(rm, "unfinished_sextant", "firmaciv:unfinished_sextant", brass.ingot_heat_capacity(),
              brass.melt_temperature, 200)
    item_heat(rm, "unfinished_barometer", "firmaciv:unfinished_barometer", brass.ingot_heat_capacity(),
              brass.melt_temperature, 200)
    item_heat(rm, "unfinished_nav_clock", "firmaciv:unfinished_nav_clock", brass.ingot_heat_capacity(),
              brass.melt_temperature, 400)
    item_heat(rm, "sextant", "firmaciv:sextant", brass.ingot_heat_capacity(), brass.melt_temperature, 200)
    item_heat(rm, "barometer", "firmaciv:barometer", brass.ingot_heat_capacity(), brass.melt_temperature, 200)
    item_heat(rm, "nav_clock", "firmaciv:nav_clock", brass.ingot_heat_capacity(), brass.melt_temperature, 400)
    item_heat(rm, "copper_bolt", "firmaciv:copper_bolt", copper.ingot_heat_capacity(), copper.melt_temperature, 25)
    item_heat(rm, "cannon_barrel", "firmaciv:cannon_barrel", cast_iron.ingot_heat_capacity(),
              cast_iron.melt_temperature, 400)

    item_heat(rm, "oarlock", "alekiships:oarlock", cast_iron.ingot_heat_capacity(), cast_iron.melt_temperature, 200)
    item_heat(rm, "cannon", "alekiships:cannon", cast_iron.ingot_heat_capacity(), cast_iron.melt_temperature, 1300)
    item_heat(rm, "cannonball", "alekiships:cannonball", cast_iron.ingot_heat_capacity(), cast_iron.melt_temperature,
              200)
    item_heat(rm, "cleat", "alekiships:cleat", steel.ingot_heat_capacity(), steel.melt_temperature, 200)
    item_heat(rm, "anchor", "alekiships:anchor", steel.ingot_heat_capacity(), steel.melt_temperature, 400)


def item_size(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, size: Size,
              weight: Weight):
    """
    Copied from TFC data gen
    """
    rm.data(('tfc', 'item_sizes', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'size': size.name,
        'weight': weight.name
    })


def item_heat(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json,
              heat_capacity: float, melt_temperature: float = None, mb: int = None):
    """
    Copied from TFC data gen
    """
    if melt_temperature is not None:
        forging_temperature = round(melt_temperature * 0.6)
        welding_temperature = round(melt_temperature * 0.8)
    else:
        forging_temperature = welding_temperature = None
    if mb is not None:
        # Interpret heat capacity as a specific heat capacity - so we need to scale by the mB present. Baseline is 100 mB (an ingot)
        # Higher mB = higher heat capacity = heats and cools slower = consumes proportionally more fuel
        heat_capacity = round(10 * heat_capacity * mb) / 1000
    rm.data(('tfc', 'item_heats', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'heat_capacity': heat_capacity,
        'forging_temperature': forging_temperature,
        'welding_temperature': welding_temperature
    })
