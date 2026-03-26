package com.foolsix.fancyenchantments.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TimeToLivePacket {
    private final int ttl;

    public TimeToLivePacket(int ttl) {
        this.ttl = ttl;
    }

    public static void encode(TimeToLivePacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.ttl);
    }

    public static TimeToLivePacket decode(FriendlyByteBuf buf) {
        return new TimeToLivePacket(buf.readInt());
    }

    public static void handle(TimeToLivePacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.setPacketHandled(true);

        if (!context.getDirection().getReceptionSide().isClient()) {
            return;
        }

        context.enqueueWork(() -> ClientHandler.handleTimeToLivePacket(message.ttl));
    }
}
