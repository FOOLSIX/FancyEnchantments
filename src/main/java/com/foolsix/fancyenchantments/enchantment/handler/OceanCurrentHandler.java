package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.OCEAN_CURRENT;

@EventBusSubscriber(modid = MODID)
public final class OceanCurrentHandler {
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(MODID, "ocean_current/attack_speed");

    @SubscribeEvent
    public static void onEntityTickPre(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity living)) {
            return;
        }

        var attackSpeed = living.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeed == null) {
            return;
        }

        attackSpeed.removeModifier(MODIFIER_ID);
        if (Config.OCEAN_CURRENT_INEFFECTIVE_WHEN_ON_FIRE.get() && living.isOnFire()) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(OCEAN_CURRENT, living);
        if (level <= 0) {
            return;
        }

        double amount = Config.OCEAN_CURRENT_SPEED_MULTIPLIER.get() * level;
        if (living.isInWater()) {
            amount *= Config.OCEAN_CURRENT_EXTRA_SPEED_MULTIPLIER.get();
        }

        attackSpeed.addTransientModifier(new AttributeModifier(MODIFIER_ID, amount, AttributeModifier.Operation.ADD_VALUE));
    }
}
