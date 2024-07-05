package com.foolsix.fancyenchantments.capability;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicInteger;

import static com.foolsix.fancyenchantments.capability.TimeToLiveCapabilityProvider.PLAYER_TTL;

public class TimeToLiveHelper {
    public static int getTtl(Player player) {
        AtomicInteger ttlVal = new AtomicInteger(-1);
        player.getCapability(PLAYER_TTL).ifPresent(ttl -> ttlVal.set(ttl.getTtl()));
        return ttlVal.get();
    }

    public static void setTtl(Player player, int ttlVal) {
        player.getCapability(PLAYER_TTL).ifPresent(ttl -> ttl.setTtl(ttlVal));
    }

    public static void setDamageSource(Player player, DamageSource damageSource) {
        player.getCapability(PLAYER_TTL).ifPresent(ttl -> ttl.setDamageSource(damageSource));
    }
}
