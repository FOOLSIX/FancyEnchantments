package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.HEAVY_ARROW;

@EventBusSubscriber(modid = MODID)
public final class HeavyArrowHandler {
    private static final String LEVEL_TAG = MODID + ":heavy_arrow_level";

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof AbstractArrow arrow) || !(arrow.getOwner() instanceof LivingEntity shooter)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(HEAVY_ARROW, shooter);
        if (level <= 0) {
            return;
        }

        arrow.setBaseDamage(arrow.getBaseDamage() * (1.0D + Config.HEAVY_ARROW_DAMAGE_MULTIPLIER.get() * level));
        arrow.getPersistentData().putInt(LEVEL_TAG, level);
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile().level().isClientSide()) {
            return;
        }
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) {
            return;
        }

        int level = arrow.getPersistentData().getInt(LEVEL_TAG);
        if (level <= 0) {
            return;
        }

        HitResult hit = event.getRayTraceResult();
        if (hit instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, level - 1));
        }
    }
}
