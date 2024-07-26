package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.effect.CritRateBoost;
import com.foolsix.fancyenchantments.effect.Invincible;
import com.foolsix.fancyenchantments.effect.PrisonCage;
import com.foolsix.fancyenchantments.effect.TemplarShield;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.foolsix.fancyenchantments.effect.EffectReg.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EffectEvents {
    @SubscribeEvent(priority = EventPriority.LOW)
    public void hurtEvent(LivingHurtEvent e) {
        if (e.getSource() == null) return;
        ((CritRateBoost) CRIT_RATE_BOOST.get()).effect(e);
        ((PrisonCage) PRISON_CAGE.get()).hurt(e);
    }

    @SubscribeEvent
    public void livingDamage(LivingDamageEvent e) {
        ((TemplarShield) TEMPLAR_SHIELD.get()).effect(e);
    }

    @SubscribeEvent
    public void onEffectAdd(MobEffectEvent.Added e) {
        ((Invincible) INVINCIBLE.get()).onAddEffect(e);
    }

    @SubscribeEvent
    public void onRemoveEffect(MobEffectEvent.Remove e) {
        ((Invincible) INVINCIBLE.get()).onRemoveEffect(e);
    }

    @SubscribeEvent
    public void onExpire(MobEffectEvent.Expired e) {
        ((Invincible) INVINCIBLE.get()).onExpire(e);
        ((PrisonCage) PRISON_CAGE.get()).onExpire(e);
    }
}
