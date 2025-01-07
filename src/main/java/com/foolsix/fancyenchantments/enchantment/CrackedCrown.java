package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CrackedCrown extends HolyEnchantment implements LivingHurtEventHandler {
    public static final String NAME = "cracked_crown";
    private static final ModConfig.CrackedCrownOptions CONFIG = FancyEnchantments.getConfig().crackedCrownOptions;

    public CrackedCrown() {
        super(CONFIG, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public int getLivingHurtPriority() {
        return MULTIPLY;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        doMoreDamage(e);
    }

    public void doMoreDamage(LivingHurtEvent e) {
        int level = EnchantmentHelper.getEnchantmentLevel(this, e.getEntity());
        if (level > 0) {
            e.setAmount((float) (e.getAmount() * (1 + CONFIG.takenDamageMultiplier * level)));
        }
        if (e.getSource().getEntity() instanceof LivingEntity living) {
            level = EnchantmentHelper.getEnchantmentLevel(this, living);
            e.setAmount((float) (e.getAmount() * (1 + CONFIG.damageMultiplier * level)));
        }
    }
}
