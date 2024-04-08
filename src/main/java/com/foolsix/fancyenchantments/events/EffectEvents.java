package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.effect.CritRateBoost;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.foolsix.fancyenchantments.effect.EffectReg.CRIT_RATE_BOOST;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EffectEvents {
    @SubscribeEvent
    public void hurtEvent(LivingHurtEvent e) {
        Entity attacker = e.getSource().getEntity();
        Entity victim = e.getEntity();
        if (attacker instanceof LivingEntity) {
            ((CritRateBoost)CRIT_RATE_BOOST.get()).effect(e);
        }
    }
}
