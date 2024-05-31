package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.capability.TimeToLiveHelper;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;


public class UnyieldingSpirit extends HolyEnchantment {
    public static final String NAME = "unyielding_spirit";
    private static final ModConfig.UnyieldingSpiritOptions CONFIG = FancyEnchantments.getConfig().unyieldingSpiritOptions;
    private static final DamageSource GIVING_UP = new DamageSource("fancyenchantments.give_up").bypassArmor();

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

    public void preventDeath(LivingHurtEvent e) {
        if (e.getEntity() instanceof ServerPlayer player && EnchantmentHelper.getEnchantmentLevel(this, player) > 0) {
            if (e.getSource() != DamageSource.OUT_OF_WORLD && e.getAmount() >= player.getHealth()) {
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
                    player.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak(5, player, (p) -> p.broadcastBreakEvent(EquipmentSlot.HEAD));
                    e.setCanceled(true);
                }
            }
        }
    }

    public void clearTag(LivingDeathEvent e) {
        if (e.getSource().getEntity() instanceof ServerPlayer player && e.getEntity() instanceof Monster) {
            if (TimeToLiveHelper.getTtl(player) != -1) {
                TimeToLiveHelper.setTtl(player, -1);
                player.setHealth(player.getMaxHealth());
                ((ServerLevel) player.level).sendParticles(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY(), player.getZ(), 100, 1.0D, 1.0D, 1.0D, 0.5);
                player.level.playSound(player, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 4.0F, 1.5F);
            }
        }
    }

    public void unequipped(LivingEquipmentChangeEvent e) {
        if (e.getEntity() instanceof Player player && TimeToLiveHelper.getTtl(player) != -1 && e.getSlot() == EquipmentSlot.HEAD) {
            if (e.getTo().getEnchantmentLevel(this) < 1) {
                e.getEntity().hurt(GIVING_UP, Float.MAX_VALUE / 16);
            }
        }
    }
}
