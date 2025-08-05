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
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class Streamline extends AerEnchantment {
    private static final ModConfig.StreamlineOptions CONFIG = FancyEnchantments.getConfig().streamlineOptions;
    private final String TAG = "fe_streamline";

    public Streamline() {
        super(CONFIG, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void speedBoost(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof AbstractArrow arrow && arrow.getOwner() instanceof LivingEntity shooter) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, shooter);
            if (level > 0) {
                arrow.setNoGravity(true);
                arrow.addTag(TAG);
                Vec3 direction = shooter.getViewVector(0.1f).scale(CONFIG.speedMultiplierPerLevel * level);
                arrow.push(direction.x(), direction.y(), direction.z());
            }
        }
    }

    public void impactInvulnerable(ProjectileImpactEvent e) {
        var entity = e.getEntity();
        var arrow = e.getProjectile();
        if (entity == null || arrow == null) return;
        if (arrow.getTags().contains(TAG)) {
            arrow.setNoGravity(false);
        }
    }
}
