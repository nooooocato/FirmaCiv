package com.alekiponi.firmaciv.network;

import com.alekiponi.alekiships.util.ClientHelper;
import com.alekiponi.firmaciv.common.entity.compartment.TFCBarrelCompartmentEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

public final class ClientBoundBarrelCompartmentUpdatePacket {

    private final int compartmentID;
    private final FluidStack fluid;
    private final ItemStack visibleStack;

    public ClientBoundBarrelCompartmentUpdatePacket(final TFCBarrelCompartmentEntity barrelCompartment,
            final FluidStack fluid, final ItemStack visibleStack) {
        this.compartmentID = barrelCompartment.getId();
        this.fluid = fluid;
        this.visibleStack = visibleStack;
    }

    ClientBoundBarrelCompartmentUpdatePacket(final FriendlyByteBuf friendlyByteBuf) {
        this.compartmentID = friendlyByteBuf.readInt();
        this.fluid = friendlyByteBuf.readFluidStack();
        this.visibleStack = friendlyByteBuf.readItem();
    }

    void encoder(final FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(this.compartmentID);
        friendlyByteBuf.writeFluidStack(this.fluid);
        friendlyByteBuf.writeItem(this.visibleStack);
    }

    void handle() {
        final Level level = ClientHelper.getLevel();

        if (level == null) return;

        if (level.getEntity(this.compartmentID) instanceof final TFCBarrelCompartmentEntity barrelCompartment) {
            barrelCompartment.syncContents(this.fluid, this.visibleStack);
        }
    }
}