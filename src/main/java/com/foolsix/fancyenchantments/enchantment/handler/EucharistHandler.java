package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.EUCHARIST;

@EventBusSubscriber(modid = MODID)
public final class EucharistHandler {
    @SubscribeEvent
    public static void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        int level = EnchUtils.getEnchantmentLevel(EUCHARIST, event.getEntity());
        FoodProperties food = event.getItem().getFoodProperties(event.getEntity());
        if (level <= 0 || food == null) {
            return;
        }

        int hunger = food.nutrition();
        if (hunger < Config.EUCHARIST_MINIMUM_HUNGER.get()) {
            return;
        }

        int duration = (int) (20 * food.saturation() * hunger * 2 * Config.EUCHARIST_DURATION_MULTIPLIER.get());
        event.getEntity().addEffect(new MobEffectInstance(EffectReg.TEMPLAR_SHIELD, duration, level - 1));
    }
}
