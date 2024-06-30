package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.effect.CritRateBoost;
import com.foolsix.fancyenchantments.effect.TemplarShield;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.foolsix.fancyenchantments.effect.EffectReg.CRIT_RATE_BOOST;
import static com.foolsix.fancyenchantments.effect.EffectReg.TEMPLAR_SHIELD;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EffectEvents {
    @SubscribeEvent
    public void hurtEvent(LivingHurtEvent e) {
        if (e.getSource() == null) return;
        ((CritRateBoost) CRIT_RATE_BOOST.get()).effect(e);
    }

    @SubscribeEvent
    public void livingDamage(LivingDamageEvent e) {
        ((TemplarShield) TEMPLAR_SHIELD.get()).effect(e);
    }
}
