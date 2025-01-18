package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.capability.TimeToLiveHelper;
import com.foolsix.fancyenchantments.damage.FEDamageSource;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingDeathEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

import java.util.List;


public class UnyieldingSpirit extends HolyEnchantment implements LivingDeathEventHandler {
    private static final ModConfig.UnyieldingSpiritOptions CONFIG = FancyEnchantments.getConfig().unyieldingSpiritOptions;

    public UnyieldingSpirit() {
        super(CONFIG, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 25;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return 75;
    }

    public void preventDeath(LivingDamageEvent e) {
        if (e.getEntity() instanceof ServerPlayer player && EnchantmentHelper.getEnchantmentLevel(this, player) > 0) {
            if (e.getSource() != player.damageSources().fellOutOfWorld()
                    && e.getSource() != player.damageSources().genericKill()
                    && e.getAmount() >= player.getHealth()) {
                if (TimeToLiveHelper.getTtl(player) == -1) {
                    int extraTime = CONFIG.extraTime * 20;
                    if (CONFIG.slownessLevel > 0)
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, extraTime, CONFIG.slownessLevel - 1));
                    if (CONFIG.damageResistanceLevel > 0)
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, extraTime, CONFIG.damageResistanceLevel - 1));
                    if (CONFIG.enableBlindness)
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, extraTime));
                    player.setHealth(player.getMaxHealth() * CONFIG.healthPercentage);
                    TimeToLiveHelper.setTtl(player, extraTime);
                    TimeToLiveHelper.setDamageSource(player, e.getSource());
                    player.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak(CONFIG.baseDamage, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.HEAD));
                    e.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void handleLivingDeathEvent(LivingDeathEvent e) {
        clearTag(e);
    }

    public void clearTag(LivingDeathEvent e) {
        if (e.getSource().getEntity() instanceof ServerPlayer player && e.getEntity() instanceof Enemy) {
            if (TimeToLiveHelper.getTtl(player) != -1) {
                TimeToLiveHelper.setTtl(player, -1);
                player.setHealth(player.getMaxHealth());
                EnchUtils.generateSimpleParticleAroundEntity(player, ParticleTypes.TOTEM_OF_UNDYING, 100, 1.0, 1.0, 1.0, 0.5);
                player.clearFire();
                List<MobEffect> effectInstances = player.getActiveEffects().stream().map(MobEffectInstance::getEffect).filter(effect -> effect.getCategory() == MobEffectCategory.HARMFUL).toList();
                effectInstances.forEach(player::removeEffect);
                player.level().playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 3.0F, 1.0F);
            }
        }
    }

    public void unequipped(LivingEquipmentChangeEvent e) {
        if (e.getEntity() instanceof ServerPlayer player && TimeToLiveHelper.getTtl(player) != -1 && e.getSlot() == EquipmentSlot.HEAD) {
            if (e.getTo().getEnchantmentLevel(this) < 1) {
                player.hurt(FEDamageSource.giveUpDamage(player), 100000);
            }
        }
    }
}
