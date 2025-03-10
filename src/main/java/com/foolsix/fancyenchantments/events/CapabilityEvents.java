package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.capability.ElementStatsCapability;
import com.foolsix.fancyenchantments.capability.ElementStatsCapabilityProvider;
import com.foolsix.fancyenchantments.capability.TimeToLiveCapabilityProvider;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils.Element;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchUtils.Element.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = MODID)
public class CapabilityEvents {
    private final ModConfig.ElementStatOptions CONFIG = FancyEnchantments.getConfig().elementStatOptions;
    private final MobEffect[] DEBUFF = new MobEffect[]{MobEffects.WEAKNESS, MobEffects.DARKNESS, MobEffects.MOVEMENT_SLOWDOWN};
    private final Optional<MobEffect>[] BUFF = new Optional[4];

    @SubscribeEvent
    public void onServerSetup(ServerStartingEvent e) {
        for (Element element : Element.values()) {
            if (element.ordinal() < BUFF.length) {
                BUFF[element.ordinal()] = Optional.ofNullable(ForgeRegistries.MOB_EFFECTS.getValue(ResourceLocation.tryParse(CONFIG.buffs[element.ordinal()])));
            }
        }
    }

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
                    e.player.hurt(ttl.getDamageSource().bypassArmor(), 10000000);
                }
                ttl.subTtl(1);
            });
        }
    }

    @SubscribeEvent
    public void elementPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.player == null || e.isCanceled() || !e.side.isServer() || e.phase != TickEvent.Phase.START)
            return;
        //Calculate once per second
        if (e.player.tickCount % 20 != 0) {
            e.player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).ifPresent(elementStats -> {
                for (Element element : Element.values()) {
                    if (element.ordinal() < BUFF.length) {
                        int index = element.ordinal();
                        int point = elementStats.getPoint(element);
                        if (BUFF[index].isPresent() && point >= CONFIG.conditions[index]) {
                            e.player.addEffect(new MobEffectInstance(
                                    BUFF[index].get(),
                                    20,
                                    Math.min((point / CONFIG.conditions[index]), CONFIG.maxEffectLevel[index]) - 1));
                        }
                    }
                }
                if (elementStats.getPoint(IGNIS) >= CONFIG.ignisLevelToGetFireResistance) {
                    e.player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20));
                }
                if (elementStats.getPoint(TWISTED) - elementStats.getPoint(HOLY) >= CONFIG.twistedLevelToGetDebuff) {
                    if (Math.random() < CONFIG.probabilityToGetDebuff) {
                        MobEffect debuff = DEBUFF[e.player.getRandom().nextInt(DEBUFF.length)];
                        e.player.addEffect(new MobEffectInstance(debuff, 20 * CONFIG.debuffDuration));
                    }
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

            if (e.getTo().is(Items.AIR)) return;
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
