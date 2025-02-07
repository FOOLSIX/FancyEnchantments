package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingDeathEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.List;
import java.util.Objects;

public class IcyBurst extends AquaEnchantment implements LivingDeathEventHandler {
    private static final ModConfig.IcyBurstOptions CONFIG = FancyEnchantments.getConfig().icyBurstOptions;
    public IcyBurst() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void handleLivingDeathEvent(LivingDeathEvent e) {
        LivingEntity victim = e.getEntity();
        if (e.getSource().getEntity() instanceof ServerPlayer player && Objects.nonNull(victim) && Objects.nonNull(victim.getEffect(MobEffects.MOVEMENT_SLOWDOWN)) && victim.level() instanceof ServerLevel world) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                List<Entity> entities = world.getEntities(victim, victim.getBoundingBox().inflate(3), EnchUtils.VISIBLE_HOSTILE);
                float damage = (float) (victim.getMaxHealth() * CONFIG.damageMultiplier * level);
                EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.SNOWFLAKE, 100, 3, 1, 3, 0.1);
                for (var entity : entities) {
                    if (entity instanceof LivingEntity living) {
                        living.hurt(player.damageSources().playerAttack(player), damage);
                        living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, CONFIG.durationPerLevel * level, level - 1));
                    }
                }
            }
        }
    }
}
