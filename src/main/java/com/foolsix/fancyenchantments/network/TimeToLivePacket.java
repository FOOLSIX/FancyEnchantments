package com.foolsix.fancyenchantments.network;

import com.foolsix.fancyenchantments.capability.TimeToLiveCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
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
        if(!context.getDirection().getReceptionSide().isClient()) return;
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null) {
            player.getCapability(TimeToLiveCapabilityProvider.PLAYER_TTL).ifPresent(timeToLiveCapability -> {
                timeToLiveCapability.setTtl(message.ttl);
            });
        }
        context.setPacketHandled(true);
    }
}
