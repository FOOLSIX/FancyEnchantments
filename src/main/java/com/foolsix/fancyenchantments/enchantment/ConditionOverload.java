package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ConditionOverload extends FEBaseEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.ConditionOverloadOptions CONFIG = FancyEnchantments.getConfig().conditionOverloadOptions;

    public ConditionOverload() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getLivingHurtPriority() {
        return MULTIPLY;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        attack(e);
    }

    public void attack(LivingHurtEvent e) {
        LivingEntity living = e.getEntity();
        if (e.getSource().getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                int debuffCount = (int) living.getActiveEffects().stream().filter(effect -> effect.getEffect().getCategory() == MobEffectCategory.HARMFUL).count();
                e.setAmount(e.getAmount() * (1 + debuffCount * level * (float) CONFIG.damageMultiplier));
            }
        }
    }
}
