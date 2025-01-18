package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Objects;

public class FrozenHeart extends AquaEnchantment {
    private static final ModConfig.FrozenHeartOptions CONFIG = FancyEnchantments.getConfig().frozenHeartOptions;
    public FrozenHeart() {
        super(CONFIG, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {
        LivingEntity attacker = pTarget.getLastHurtByMob();
        if (Objects.nonNull(attacker)) {
            attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, CONFIG.duration * 20, pLevel));
        }
    }
}
