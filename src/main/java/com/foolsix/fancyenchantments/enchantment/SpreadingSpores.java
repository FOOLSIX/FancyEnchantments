package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class SpreadingSpores extends FEBaseEnchantment {
    public static final String NAME = "spreading_spores";
    private static final ModConfig.SpreadSporesOptions CONFIG = FancyEnchantments.getConfig().spreadSporesOptions;

    public SpreadingSpores() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public Component getFullname(int pLevel) {
        return EnchUtils.getMixedColorFullName(super.getFullname(pLevel), EnchUtils.Element.AQUA, EnchUtils.Element.TERRA);
    }

    public void damageSpread(LivingDamageEvent e) {
        if (e.getSource() != null && e.getSource().getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                final String SPORE_TAG = "fe_spores";
                LivingEntity victim = e.getEntity();
                int spores = victim.getPersistentData().getInt(SPORE_TAG);
                if (spores + level >= CONFIG.sporeCap) {
                    victim.getPersistentData().putInt(SPORE_TAG, (spores + level) % CONFIG.sporeCap);
                    e.setAmount(e.getAmount() * (float) CONFIG.damageMultiplier);
                    victim.addEffect(new MobEffectInstance(MobEffects.POISON, level * 40, level - 1));
                    EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.SPORE_BLOSSOM_AIR);
                } else {
                    victim.getPersistentData().putInt(SPORE_TAG, spores + level);
                }
            }
        }
    }
}
