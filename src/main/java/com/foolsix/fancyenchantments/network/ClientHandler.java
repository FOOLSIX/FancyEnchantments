package com.foolsix.fancyenchantments.network;

import com.foolsix.fancyenchantments.capability.TimeToLiveCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {
    public static void handleTimeToLivePacket(int ttl) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        player.getCapability(TimeToLiveCapabilityProvider.PLAYER_TTL).ifPresent(timeToLiveCapability -> {
            timeToLiveCapability.setTtl(ttl);
        });
    }
}
