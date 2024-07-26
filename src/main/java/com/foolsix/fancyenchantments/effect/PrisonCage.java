package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;

import static com.foolsix.fancyenchantments.effect.EffectReg.PRISON_CAGE;

public class PrisonCage extends MobEffect {
    public static final String NAME = "prison_cage";
    private final String CAGE_TAG = NAME + "_damage";
    private static final ModConfig.DelayedExecutionOptions CONFIG = FancyEnchantments.getConfig().delayedExecutionOptions;

    public PrisonCage() {
        super(MobEffectCategory.HARMFUL, 0x292421);
    }

    public void hurt(LivingHurtEvent e) {
        if (e.getEntity() != null) {
            LivingEntity living = e.getEntity();
            MobEffectInstance instance = living.getEffect(PRISON_CAGE.get());
            if (instance != null && instance.getDuration() > 0) {
                CompoundTag nbt = living.getPersistentData();
                nbt.putFloat(CAGE_TAG, nbt.getFloat(CAGE_TAG) + e.getAmount() * CONFIG.storedDamage);
                e.setAmount(e.getAmount() * (1 - CONFIG.storedDamage));
            }
        }
    }

    public void onExpire(MobEffectEvent.Expired e) {
        LivingEntity living = e.getEntity();
        if (living == null || e.getEffectInstance() == null) return;
        MobEffectInstance instance = e.getEffectInstance();
        if (instance.getEffect() == PRISON_CAGE.get()) {
            float damage = living.getPersistentData().getFloat(CAGE_TAG) * (1f + CONFIG.damageMultiplier * (instance.getAmplifier() + 1));
            living.getPersistentData().remove(CAGE_TAG);
            LivingEntity attacker = living.getLastHurtByMob();
            living.hurt(attacker == null ? DamageSource.MAGIC : DamageSource.mobAttack(attacker), damage);
            EnchUtils.generateSimpleParticleAroundEntity(living, ParticleTypes.CRIT);
        }
    }
}
