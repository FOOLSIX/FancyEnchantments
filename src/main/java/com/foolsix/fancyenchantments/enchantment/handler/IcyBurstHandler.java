package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;
import java.util.Objects;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ICY_BURST;

@EventBusSubscriber(modid = MODID)
public final class IcyBurstHandler {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (!(event.getSource().getEntity() instanceof ServerPlayer player)
                || Objects.isNull(victim.getEffect(MobEffects.MOVEMENT_SLOWDOWN))
                || !(victim.level() instanceof ServerLevel level)) {
            return;
        }

        int enchantmentLevel = EnchUtils.getEnchantmentLevel(ICY_BURST, player);
        if (enchantmentLevel <= 0) {
            return;
        }

        List<Entity> entities = level.getEntities(victim, victim.getBoundingBox().inflate(3.0D), EnchUtils.VISIBLE_HOSTILE);
        float damage = (float) (victim.getMaxHealth() * Config.ICY_BURST_DAMAGE_MULTIPLIER.get() * enchantmentLevel);
        int durationTicks = Config.ICY_BURST_DURATION_PER_LEVEL.get() * enchantmentLevel * 20;
        EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.SNOWFLAKE, 100, 3.0D, 1.0D, 3.0D, 0.1D);
        player.level().playSound(null, victim.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.HOSTILE, 1.0F, 1.5F + player.level().random.nextFloat() * 0.4F);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living) {
                living.hurt(player.damageSources().playerAttack(player), damage);
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, durationTicks, enchantmentLevel - 1));
            }
        }
    }
}
