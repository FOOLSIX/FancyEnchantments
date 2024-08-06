package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class LavaBurst extends FEBaseEnchantment {
    public static final String NAME = "lava_burst";
    private static final ModConfig.LavaBurstOptions CONFIG = FancyEnchantments.getConfig().lavaBurstOptions;

    public LavaBurst() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public Component getFullname(int pLevel) {
        return EnchUtils.getMixedColorFullName(super.getFullname(pLevel), EnchUtils.Element.IGNIS, EnchUtils.Element.TERRA);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (Math.random() < CONFIG.probability * pLevel) {
            Level level = pTarget.getLevel();
            if (level instanceof ServerLevel) {
                level.explode(pAttacker, pTarget.getX(), pTarget.getY(), pTarget.getZ(), 0.5f * pLevel, Explosion.BlockInteraction.NONE);
                EnchUtils.generateSimpleParticleAroundEntity(pTarget, ParticleTypes.LAVA);
            }
        }
    }
}
