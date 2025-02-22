package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AdvancedFireAspect extends IgnisEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.AdvancedFireAspectOptions CONFIG = FancyEnchantments.getConfig().advancedFireAspectOptions;

    public AdvancedFireAspect() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && pOther.isCompatibleWith(Enchantments.FIRE_ASPECT);
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof LivingEntity attacker) {
            LivingEntity victim = e.getEntity();
            int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
            victim.setRemainingFireTicks(victim.getRemainingFireTicks() + CONFIG.increasedSecondPerHit * level * 20);
        }
    }
}
