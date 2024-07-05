package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class Bullying extends TwistedEnchantment {
    public static final String NAME = "bullying";
    private static final ModConfig.BullyingOptions CONFIG = FancyEnchantments.getConfig().bullyingOptions;

    public Bullying() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int pLevel) {
        if (target instanceof LivingEntity living && living.isBaby() && living.isAlive()) {
            living.die(DamageSource.mobAttack(attacker));
            living.setHealth(0);
        }
    }
}
