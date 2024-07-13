package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class Charge extends FEBaseEnchantment {
    private static final ModConfig.ChargeOptions CONFIG = FancyEnchantments.getConfig().chargeOptions;
    public static final String NAME = "charge";

    public Charge() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void charge(LivingAttackEvent e) {
        if (!(e.getSource().getEntity() instanceof LivingEntity living)) return;
        int level = EnchantmentHelper.getEnchantmentLevel(this, living);
        if (level > 0) {
            Vec3 lookAt = living.getLookAngle();
            living.push(lookAt.x * CONFIG.chargeDistanceMultiplier, lookAt.y * CONFIG.chargeDistanceMultiplier, lookAt.z * CONFIG.chargeDistanceMultiplier);
            living.addEffect(new MobEffectInstance(EffectReg.INVINCIBLE.get(), 5 + CONFIG.invincibleDurationPerLevel * level));
        }
    }
}
