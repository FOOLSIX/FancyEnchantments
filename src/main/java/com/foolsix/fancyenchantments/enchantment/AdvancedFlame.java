package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class AdvancedFlame extends IgnisEnchantment {
    private static final ModConfig.AdvancedFlameOptions CONFIG = FancyEnchantments.getConfig().advancedFlameOptions;
    private final String NAME = "advanced_flame";
    public AdvancedFlame() {
        super(CONFIG, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && !(pOther instanceof ArrowFireEnchantment) && pOther.isCompatibleWith(Enchantments.FLAMING_ARROWS);
    }

    public void enhanceArrow(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, shooter);
            if (level > 0) {
                arrow.setSecondsOnFire(30);
                arrow.getPersistentData().putInt(NAME, level);
            }
        }
    }

    public void arrowImpact(ProjectileImpactEvent e) {
        if (e.getProjectile() != null) {
            int level = e.getProjectile().getPersistentData().getInt(NAME);
            if (level > 0 && e.getEntity() instanceof LivingEntity living) {
                living.setRemainingFireTicks(living.getRemainingFireTicks() + CONFIG.increasedSecondPerHit * 20);
            }
        }
    }

}
