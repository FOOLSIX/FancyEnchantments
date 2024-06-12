package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.capability.ElementStatsCapability;
import com.foolsix.fancyenchantments.capability.ElementStatsCapabilityProvider;
import com.foolsix.fancyenchantments.capability.TimeToLiveCapabilityProvider;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils.Element;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
        if (!player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).isPresent()) {
            e.addCapability(new ResourceLocation(MODID, "element_stats"), new ElementStatsCapabilityProvider());
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

    @SubscribeEvent
    public void elementPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.player != null && e.side.isServer() && e.phase == TickEvent.Phase.START) {
            e.player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).ifPresent(elementStats -> {
                if (elementStats.getPoint(Element.AER) >= 5) {
                    e.player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20));
                }
            });
        }
    }

    @SubscribeEvent
    public void elementPlayerEquipmentChange(LivingEquipmentChangeEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;

        player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).ifPresent(elementStats -> {
            for (var entry : e.getFrom().getAllEnchantments().entrySet()) {
                Element element = Element.getElement(entry.getKey());
                if (element != null) {
                    elementStats.subPoint(element, entry.getValue());
                }
            }

            for (var entry : e.getTo().getAllEnchantments().entrySet()) {
                Element element = Element.getElement(entry.getKey());
                if (element != null) {
                    elementStats.addPoint(element, entry.getValue());
                }
            }
        });
    }

    @SubscribeEvent
    public void elementPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent e) {
        if (e.getEntity() instanceof ServerPlayer player) {
            player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).ifPresent(ElementStatsCapability::resetPoint);
        }
    }
}
