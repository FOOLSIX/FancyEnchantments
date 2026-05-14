package com.foolsix.fancyenchantments.effect;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@EventBusSubscriber(modid = MODID)
public final class InvincibleHandler {
    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event) {
        EffectReg.INVINCIBLE.get().onAddEffect(event);
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        EffectReg.INVINCIBLE.get().onRemoveEffect(event);
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        EffectReg.INVINCIBLE.get().onExpire(event);
    }
}
