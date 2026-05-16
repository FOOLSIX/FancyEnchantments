package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.Config;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@EventBusSubscriber(modid = MODID)
public final class TemplarShieldHandler {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity living = event.getEntity();
        MobEffectInstance effectInstance = living.getEffect(EffectReg.TEMPLAR_SHIELD);
        if (effectInstance == null) {
            return;
        }

        event.setAmount(event.getAmount() * (float) Config.EUCHARIST_DAMAGE_MULTIPLIER.get().doubleValue());
        if (effectInstance.getAmplifier() > 0) {
            living.forceAddEffect(new MobEffectInstance(EffectReg.TEMPLAR_SHIELD, effectInstance.getDuration(), effectInstance.getAmplifier() - 1), null);
        } else {
            living.removeEffect(EffectReg.TEMPLAR_SHIELD);
        }
    }
}
