package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import static com.foolsix.fancyenchantments.effect.EffectReg.TREMBLING;

public class ThrillingThunder extends FEBaseEnchantment {
    private static final ModConfig.ThrillingThunderOptions CONFIG = FancyEnchantments.getConfig().thrillingThunderOptions;

    public ThrillingThunder() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public Component getFullname(int pLevel) {
        return EnchUtils.getMixedColorFullName(super.getFullname(pLevel), EnchUtils.Element.AER, EnchUtils.Element.AQUA);
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (Math.random() < CONFIG.probabilityOfApplyingEffectPerLevel * pLevel) {
            if (pTarget instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(TREMBLING.get(), pLevel * 20, pLevel - 1));
            }
        }
    }
}
