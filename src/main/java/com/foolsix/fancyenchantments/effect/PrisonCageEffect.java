package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public final class PrisonCageEffect extends MobEffect {
    public static final String NAME = "prison_cage";
    private static final String STORED_DAMAGE_TAG = NAME + "_damage";

    public PrisonCageEffect() {
        super(MobEffectCategory.HARMFUL, 0x292421);
    }

    public void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity living = event.getEntity();
        MobEffectInstance instance = living.getEffect(EffectReg.PRISON_CAGE);
        if (instance == null || instance.getDuration() <= 0) {
            return;
        }

        float storedDamage = event.getAmount() * Config.DELAYED_EXECUTION_STORED_DAMAGE.get().floatValue();
        living.getPersistentData().putFloat(STORED_DAMAGE_TAG, living.getPersistentData().getFloat(STORED_DAMAGE_TAG) + storedDamage);
        event.setAmount(event.getAmount() - storedDamage);
    }

    public void onExpire(MobEffectEvent.Expired event) {
        LivingEntity living = event.getEntity();
        MobEffectInstance instance = event.getEffectInstance();
        if (instance == null || !instance.getEffect().is(EffectReg.PRISON_CAGE.getKey())) {
            return;
        }

        float storedDamage = living.getPersistentData().getFloat(STORED_DAMAGE_TAG);
        living.getPersistentData().remove(STORED_DAMAGE_TAG);
        if (storedDamage <= 0.0F) {
            return;
        }

        float damage = storedDamage * (float) (1.0D + Config.DELAYED_EXECUTION_DAMAGE_MULTIPLIER.get() * (instance.getAmplifier() + 1));
        LivingEntity attacker = living.getLastHurtByMob();
        living.hurt(attacker == null ? living.damageSources().magic() : attacker.damageSources().mobAttack(attacker), damage);
        EnchUtils.generateSimpleParticleAroundEntity(living, ParticleTypes.CRIT);
    }
}
