package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SighsOfAshes extends IgnisEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.SighsOfAshesOptions CONFIG = FancyEnchantments.getConfig().sighsOfAshesOptions;

    public SighsOfAshes() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getLivingHurtPriority() {
        return ADD;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            LivingEntity victim = e.getEntity();
            if (level > 0 && victim.isOnFire() && player.getAttackStrengthScale(0.5F) > 0.95) {
                int rest = victim.getRemainingFireTicks() / 20;
                victim.clearFire();
                victim.level.playSound(null, victim.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.5F + victim.level.random.nextFloat() * 0.4F);
                EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.SMOKE);
                e.setAmount((float) (e.getAmount() + rest * CONFIG.damagePerSecond));
            }
        }
    }
}
