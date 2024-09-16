package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class HeavyArrow extends TerraEnchantment {
    public static final String NAME = "heavy_arrow";
    private static final ModConfig.HeavyArrowOptions CONFIG = FancyEnchantments.getConfig().heavyArrowOptions;

    public HeavyArrow() {
        super(CONFIG, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void enhanceArrow(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, shooter);
            if (level > 0) {
                arrow.setBaseDamage(arrow.getBaseDamage() * (1 + CONFIG.damageMultiplierPerLevel * level));
                arrow.setKnockback(arrow.getKnockback() + CONFIG.knockbackAddonPerLevel * level);
                arrow.getPersistentData().putInt(NAME, level);
            }
        }
    }

    public void arrowImpact(ProjectileImpactEvent e) {
        if (e.getProjectile() != null) {
            int level = e.getProjectile().getPersistentData().getInt(NAME);
            if (level > 0 && e.getEntity() instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, level - 1));
            }
        }
    }
}
