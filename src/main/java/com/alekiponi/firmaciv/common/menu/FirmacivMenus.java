package com.alekiponi.firmaciv.common.menu;

import com.alekiponi.firmaciv.Firmaciv;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class FirmacivMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU,
            Firmaciv.MOD_ID);


    public static final RegistryObject<MenuType<BarrelCompartmentMenu>> BARREL_COMPARTMENT_MENU = MENU_TYPES.register(
            "barrel_compartment_menu", () -> IForgeMenuType.create(BarrelCompartmentMenu::fromNetwork));

    public static final RegistryObject<MenuType<LargeVesselCompartmentMenu>> LARGE_VESSEL_COMPARTMENT_MENU = MENU_TYPES.register(
            "vessel_compartment_menu", () -> IForgeMenuType.create(LargeVesselCompartmentMenu::fromNetwork));
}