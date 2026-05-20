package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.STACKING_WAVES;

@EventBusSubscriber(modid = MODID)
public final class StackingWavesHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            int level = EnchUtils.getEnchantmentLevel(STACKING_WAVES, attacker);
            if (level <= 0) return;

            var attackSpeedBoost = EffectReg.ATTACK_SPEED_BOOST;
            MobEffectInstance instance = attacker.getEffect(attackSpeedBoost);
            int duration = Config.STACKING_WAVES_DURATION.get() * 20;
            if (instance == null) {
                attacker.addEffect(new MobEffectInstance(attackSpeedBoost, duration));
            } else {
                attacker.addEffect(new MobEffectInstance(attackSpeedBoost, duration,
                        Math.min(level - 1, instance.getAmplifier() + 1)));
            }
        }
    }
}
