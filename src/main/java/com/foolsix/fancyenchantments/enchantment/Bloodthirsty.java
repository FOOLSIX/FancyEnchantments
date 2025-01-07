package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TwistedEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class Bloodthirsty extends TwistedEnchantment implements LivingHurtEventHandler {
    public static final String NAME = "bloodthirsty";
    private static final ModConfig.BloodthirstyOptions CONFIG = FancyEnchantments.getConfig().bloodthirstyOptions;

    public Bloodthirsty() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    public void getHungry(TickEvent.PlayerTickEvent e) {
        if (EnchantmentHelper.getEnchantmentLevel(this, e.player) > 0) {
            e.player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 10, 1));
        }
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        gainFoodLevel(e);
    }

    public void gainFoodLevel(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                FoodData foodData = player.getFoodData();
                float damageValue = e.getAmount();
                int foodLevel = foodData.getFoodLevel();
                //If other mods cause hunger values to exceed the upper limit, I will not handle it
                if (foodLevel < CONFIG.hungerUpperLimit)
                    foodData.setFoodLevel((int) Math.min(CONFIG.hungerUpperLimit, foodLevel + damageValue * CONFIG.hungerMultiplier));
                if (foodData.getSaturationLevel() < CONFIG.saturationCap)
                    foodData.setSaturation(Math.min(CONFIG.saturationCap, foodData.getSaturationLevel() + damageValue * CONFIG.saturationMultiplier));
            }
        }
    }
}
