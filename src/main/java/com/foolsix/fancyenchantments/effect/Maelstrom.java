package com.foolsix.fancyenchantments.effect;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Maelstrom extends MobEffect {
    public Maelstrom() {
        super(MobEffectCategory.HARMFUL, 0x7B68EE);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity living, int amplifier) {
        if (living.level() instanceof ServerLevel level) {
            EnchUtils.generateSimpleParticleAroundEntity(living, ParticleTypes.RAIN);
            List<Entity> entities = level.getEntities(living, living.getBoundingBox().inflate(5, 2, 5), EnchUtils.VISIBLE_HOSTILE);
            Vec3 pos = living.getPosition(1.0F);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity target) {
                    Vec3 pushDir = target.position().vectorTo(pos).normalize().multiply(0.25, 0.25, 0.25);
                    target.push(pushDir.x(), pushDir.y(), pushDir.z());
                }
            }
        }
        return true;
    }
}
