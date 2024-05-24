package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.capability.TimeToLiveCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MODID)
public class CapabilityEvents {
    @SubscribeEvent
    public void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> e) {
        if (!(e.getObject() instanceof Player player)) return;

        if (!player.getCapability(TimeToLiveCapabilityProvider.PLAYER_TTL).isPresent()) {
            e.addCapability(new ResourceLocation(MODID, "time_to_live"), new TimeToLiveCapabilityProvider());
        }
    }

    @SubscribeEvent
    public void timeToLivePlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.player != null && e.side.isServer() && e.phase == TickEvent.Phase.START) {
            e.player.getCapability(TimeToLiveCapabilityProvider.PLAYER_TTL).ifPresent(ttl -> {
                if (ttl.getTtl() == 0) {
                    e.player.hurt(ttl.getDamageSource().bypassArmor(), Float.MAX_VALUE / 16);
                }
                ttl.subTtl(1);
            });
        }
    }
}
