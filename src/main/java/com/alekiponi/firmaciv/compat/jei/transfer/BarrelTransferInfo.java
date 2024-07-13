package com.alekiponi.firmaciv.compat.jei.transfer;

import com.alekiponi.firmaciv.common.menu.BarrelCompartmentMenu;
import com.alekiponi.firmaciv.common.menu.FirmacivMenus;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.dries007.tfc.common.blockentities.BarrelBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Copied from my TFC PR before alc closed it. Includes improvements made before I had noticed
 */
public class BarrelTransferInfo<R> implements IRecipeTransferInfo<BarrelCompartmentMenu, R> {

    private final IRecipeTransferHandlerHelper transferHelper;
    private final RecipeType<R> recipeType;

    public BarrelTransferInfo(IRecipeTransferHandlerHelper transferHelper, RecipeType<R> recipeType) {
        this.transferHelper = transferHelper;
        this.recipeType = recipeType;
    }

    @Override
    public Class<? extends BarrelCompartmentMenu> getContainerClass() {
        return BarrelCompartmentMenu.class;
    }

    @Override
    public Optional<MenuType<BarrelCompartmentMenu>> getMenuType() {
        return Optional.of(FirmacivMenus.BARREL_COMPARTMENT_MENU.get());
    }

    @Override
    public RecipeType<R> getRecipeType() {
        return this.recipeType;
    }

    @Override
    public boolean canHandle(final BarrelCompartmentMenu container, final R recipe) {
        return container.getBarrelCompartment().canModify();
    }

    @Nullable
    @Override
    public IRecipeTransferError getHandlingError(final BarrelCompartmentMenu container, final R recipe) {
        return this.transferHelper.createUserErrorWithTooltip(
                Component.translatable("firmaciv.jei.transfer.error.barrel_sealed"));
    }

    @Override
    public List<Slot> getRecipeSlots(final BarrelCompartmentMenu container, final R recipe) {
        return List.of(container.getSlot(BarrelBlockEntity.SLOT_ITEM));
    }

    @Override
    public List<Slot> getInventorySlots(final BarrelCompartmentMenu container, final R recipe) {
        return IntStream.range(BarrelBlockEntity.SLOTS, BarrelBlockEntity.SLOTS + Inventory.INVENTORY_SIZE)
                .mapToObj(container::getSlot).collect(Collectors.toList());
    }
}