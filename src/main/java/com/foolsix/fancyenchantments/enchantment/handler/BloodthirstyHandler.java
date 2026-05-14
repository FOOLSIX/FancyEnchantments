package com.foolsix.fancyenchantments.enchantment.handler;

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
    private static final float HUNGER_MULTIPLIER = 0.5F;
    private static final int HUNGER_UPPER_LIMIT = 20;
    private static final float SATURATION_MULTIPLIER = 0.2F;
    private static final float SATURATION_CAP = 25.0F;

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
        if (foodData.getFoodLevel() < HUNGER_UPPER_LIMIT) {
            foodData.setFoodLevel((int) Math.min(HUNGER_UPPER_LIMIT, foodData.getFoodLevel() + damageValue * HUNGER_MULTIPLIER));
        }
        if (foodData.getSaturationLevel() < SATURATION_CAP) {
            foodData.setSaturation(Math.min(SATURATION_CAP, foodData.getSaturationLevel() + damageValue * SATURATION_MULTIPLIER));
        }
    }
}
