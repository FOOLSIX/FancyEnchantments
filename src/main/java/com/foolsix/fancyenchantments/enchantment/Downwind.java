package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Downwind extends AerEnchantment {
    private static final ModConfig.DownwindOptions CONFIG = FancyEnchantments.getConfig().downwindOptions;

    public Downwind() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void attackAndPush(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof LivingEntity attacker) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
            if (level > 0) {
                LivingEntity victim = e.getEntity();
                if (!victim.onGround()) {
                    e.setAmount((float) (e.getAmount() * (1 + CONFIG.damageMultiplierPerLevel)));
                }
                victim.teleportTo(victim.getX(), victim.getY() + 0.5, victim.getZ());
                double down = victim.getDeltaMovement().y() > 0 ? 0 : -victim.getDeltaMovement().y();
                victim.push(0, CONFIG.pushForceMultiplier + down, 0);
            }
        }
    }
}
