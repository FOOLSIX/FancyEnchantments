package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

public class Streamline extends AerEnchantment {
    private static final ModConfig.StreamlineOptions CONFIG = FancyEnchantments.getConfig().windWingsOptions;

    public Streamline() {
        super(CONFIG, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void speedBoost(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, shooter);
            if (level > 0) {
                arrow.setNoGravity(true);
                Vec3 direction = shooter.getViewVector(0.1f).scale(CONFIG.speedMultiplierPerLevel * level);
                arrow.push(direction.x(), direction.y(), direction.z());
            }
        }
    }
}
