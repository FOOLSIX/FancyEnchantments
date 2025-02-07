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
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.level instanceof ServerLevel world) {
            EnchUtils.generateSimpleParticleAroundEntity(pLivingEntity, ParticleTypes.RAIN);
            List<Entity> entities = world.getEntities(pLivingEntity, pLivingEntity.getBoundingBox().inflate(5, 2, 5), EnchUtils.VISIBLE_HOSTILE);
            Vec3 pos = pLivingEntity.getPosition(1);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity living) {
                    Vec3 pushDir = living.position().vectorTo(pos).normalize().multiply(0.25, 0.25, 0.25);
                    living.push(pushDir.x(), pushDir.y(), pushDir.z());
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 10 == 0;
    }
}
