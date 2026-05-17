package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.OVER_HEALING;

@EventBusSubscriber(modid = MODID)
public final class OverHealingHandler {
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(MODID, "over_healing/max_absorption");

    @SubscribeEvent
    public static void onEntityTickPre(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity living)) {
            return;
        }

        var maxAbsorption = living.getAttribute(Attributes.MAX_ABSORPTION);
        if (maxAbsorption == null) {
            return;
        }

        maxAbsorption.removeModifier(MODIFIER_ID);
        int level = EnchUtils.getEnchantmentLevel(OVER_HEALING, living);
        if (level <= 0) {
            return;
        }

        maxAbsorption.addTransientModifier(new AttributeModifier(
                MODIFIER_ID,
                level * Config.OVER_HEALING_CAP.get(),
                AttributeModifier.Operation.ADD_VALUE
        ));
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        LivingEntity living = event.getEntity();
        float missingHealth = living.getMaxHealth() - living.getHealth();
        float overflow = Math.max(0.0F, event.getAmount() - missingHealth);
        if (overflow <= 0.0F) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(OVER_HEALING, living);
        if (level <= 0) {
            return;
        }

        float cap = level * Config.OVER_HEALING_CAP.get();
        if (living.getAbsorptionAmount() < cap) {
            living.setAbsorptionAmount(Math.min(cap, living.getAbsorptionAmount() + overflow));
        }
    }
}
