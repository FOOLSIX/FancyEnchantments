package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class GiftOfFire extends IgnisEnchantment {
    public static final String NAME = "gift_of_fire";
    private static final ModConfig.GiftOfFireOptions CONFIG = FancyEnchantments.getConfig().giftOfFireOptions;

    public GiftOfFire() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 8;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    public void doExtraDamage(LivingHurtEvent e) {
        Entity attacker = e.getSource().getEntity();
        Entity victim = e.getEntity();
        if (attacker instanceof LivingEntity living) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, living);
            if (level > 0) {
                if (living.isInWater()) {
                    living.level.playSound(null, living.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.5F + living.level.random.nextFloat() * 0.4F);
                    ((ServerLevel) attacker.level).sendParticles(ParticleTypes.SMOKE, victim.getX(), victim.getY(), victim.getZ(), 30, 0.2D, 0.7D, 0.1D, 0);
                    e.setAmount(e.getAmount() - level * CONFIG.harmfulMultiplier);
                } else if (victim.isOnFire()) {
                    ((ServerLevel) attacker.level).sendParticles(ParticleTypes.LAVA, victim.getX(), victim.getY(), victim.getZ(), 15, 0.2D, 0.7D, 0.1D, 0);
                    e.setAmount(e.getAmount() + level * CONFIG.beneficialMultiplier);
                }
            }
        }
    }
}
