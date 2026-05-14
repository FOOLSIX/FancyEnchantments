package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.CONDITION_OVERLOAD;

@EventBusSubscriber(modid = MODID)
public final class ConditionOverloadHandler {
    private static final float DAMAGE_MULTIPLIER = 0.05F;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(CONDITION_OVERLOAD, player);
        if (level <= 0) {
            return;
        }

        int debuffCount = (int) target.getActiveEffects().stream()
                .filter(effect -> effect.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)
                .count();
        event.setAmount(event.getAmount() * (1.0F + debuffCount * level * DAMAGE_MULTIPLIER));
    }
}
