package com.alekiponi.firmaciv.network;

import com.alekiponi.firmaciv.Firmaciv;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {
    private static final String PROTOCOL_VERSION = ModList.get().getModFileById(Firmaciv.MOD_ID).versionString();
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Firmaciv.MOD_ID, "network"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);


    public static void send(final PacketDistributor.PacketTarget target, final Object message) {
        CHANNEL.send(target, message);
    }

    public static void init() {
        int id = 0;
        CHANNEL.messageBuilder(ClientBoundBarrelCompartmentUpdatePacket.class, id++)
                .encoder(ClientBoundBarrelCompartmentUpdatePacket::encoder).decoder(
                        ClientBoundBarrelCompartmentUpdatePacket::new)
                .consumerMainThread(
                        (clientBoundBarrelCompartmentPacket, contextSupplier) -> clientBoundBarrelCompartmentPacket.handle())
                .add();
    }
}