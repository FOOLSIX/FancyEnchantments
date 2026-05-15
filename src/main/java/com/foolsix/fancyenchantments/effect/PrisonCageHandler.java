package com.foolsix.fancyenchantments.effect;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@EventBusSubscriber(modid = MODID)
public final class PrisonCageHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        EffectReg.PRISON_CAGE.get().onLivingIncomingDamage(event);
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        EffectReg.PRISON_CAGE.get().onExpire(event);
    }
}
