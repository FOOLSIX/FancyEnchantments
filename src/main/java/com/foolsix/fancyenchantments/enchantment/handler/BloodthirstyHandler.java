package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BLOODTHIRSTY;

@EventBusSubscriber(modid = MODID)
public final class BloodthirstyHandler {
    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(BLOODTHIRSTY, player);
        if (level <= 0) {
            return;
        }

        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 10, 1, false, false, true));
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(BLOODTHIRSTY, player);
        if (level <= 0) {
            return;
        }

        FoodData foodData = player.getFoodData();
        float damageValue = event.getAmount();
        int hungerUpperLimit = Config.BLOODTHIRSTY_HUNGER_UPPER_LIMIT.get();
        if (foodData.getFoodLevel() < hungerUpperLimit) {
            foodData.setFoodLevel((int) Math.min(hungerUpperLimit, foodData.getFoodLevel() + damageValue * Config.BLOODTHIRSTY_HUNGER_MULTIPLIER.get().floatValue()));
        }
        float saturationCap = Config.BLOODTHIRSTY_SATURATION_CAP.get().floatValue();
        if (foodData.getSaturationLevel() < saturationCap) {
            foodData.setSaturation(Math.min(saturationCap, foodData.getSaturationLevel() + damageValue * Config.BLOODTHIRSTY_SATURATION_MULTIPLIER.get().floatValue()));
        }
    }
}
