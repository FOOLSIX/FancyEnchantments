package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class DuellistsPrerogative extends FEBaseEnchantment {
    public static final String NAME = "duellists_prerogative";
    private static final ModConfig.DuellistsPrerogativeOptions CONFIG = FancyEnchantments.getConfig().duellistsPrerogativeOptions;

    public DuellistsPrerogative() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void hurtSingle(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                LivingEntity victim = e.getEntity();
                float multiplier = 1.0f;
                List<LivingEntity> entities1 = victim.level.getEntitiesOfClass(LivingEntity.class, victim.getBoundingBox().inflate(2));
                List<LivingEntity> entities2 = player.level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(2));
                multiplier = entities1.size() <= 1 && entities2.size() <= 1 ? (1 + CONFIG.damageMultiplier * level) : multiplier;
                e.setAmount(e.getAmount() * multiplier);
            }
        }
    }
}
