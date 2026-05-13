package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.AFTERIMAGE;

@EventBusSubscriber(modid = MODID)
public final class AfterimageHandler {
    private static final double DEFAULT_EXTRA_SPEED = 0.2D;
    private static final double PROBABILITY_MULTIPLIER = 0.5D;
    private static final double PROBABILITY_CAP_PER_LEVEL = 0.3D;
    private static final double PROBABILITY_MAX_CAP = 0.8D;
    private static final int INVULNERABILITY_TICKS = 60;

    private AfterimageHandler() {
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity living = event.getEntity();
        int level = getAfterimageLevel(living);
        if (level <= 0) {
            return;
        }

        double chance = Math.min(
                getExtraSpeed(living) * PROBABILITY_MULTIPLIER,
                Math.min(level * PROBABILITY_CAP_PER_LEVEL, PROBABILITY_MAX_CAP)
        );
        if (living.getRandom().nextDouble() >= chance) {
            return;
        }

        event.setCanceled(true);
        event.setInvulnerabilityTicks(INVULNERABILITY_TICKS);
        living.invulnerableTime = Math.max(living.invulnerableTime, INVULNERABILITY_TICKS);
    }

    private static int getAfterimageLevel(LivingEntity living) {
        int maxLevel = 0;
        for (ItemStack stack : living.getArmorSlots()) {
            if (stack.isEmpty()) {
                continue;
            }

            for (var entry : EnchUtils.enchantmentsOn(stack).entrySet()) {
                if (EnchUtils.matchesKey(entry.getKey(), AFTERIMAGE)) {
                    maxLevel = Math.max(maxLevel, entry.getIntValue());
                }
            }
        }
        return maxLevel;
    }

    private static double getExtraSpeed(LivingEntity living) {
        AttributeInstance moveSpeedAttr = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (moveSpeedAttr == null || moveSpeedAttr.getBaseValue() <= 0.0D) {
            return DEFAULT_EXTRA_SPEED;
        }

        return Math.max(0.0D, moveSpeedAttr.getValue() - moveSpeedAttr.getBaseValue()) / moveSpeedAttr.getBaseValue();
    }
}
