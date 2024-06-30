package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import static com.foolsix.fancyenchantments.effect.EffectReg.TEMPLAR_SHIELD;

public class Eucharist extends HolyEnchantment {
    public static final String NAME = "eucharist";
    private static final ModConfig.EucharistOptions CONFIG = FancyEnchantments.getConfig().eucharistOptions;

    public Eucharist() {
        super(CONFIG, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    public void getBuff(LivingEntityUseItemEvent.Finish e) {
        if (!e.getItem().isEdible()) return;
        int level = EnchantmentHelper.getEnchantmentLevel(this, e.getEntity());
        FoodProperties food = e.getItem().getFoodProperties(e.getEntity());
        if (level > 0 && food != null && e.getEntity() != null) {
            int hunger = food.getNutrition();
            int duration = (int) (20 * food.getSaturationModifier() * hunger * 2 * CONFIG.durationMultiplier);
            if (hunger >= CONFIG.minimumHunger) {
                LivingEntity living = e.getEntity();
                living.addEffect(new MobEffectInstance(TEMPLAR_SHIELD.get(), duration, level - 1));
            }
        }
    }
}
