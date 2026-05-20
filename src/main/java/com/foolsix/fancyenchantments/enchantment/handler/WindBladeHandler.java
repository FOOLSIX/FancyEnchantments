package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.WIND_BLADE;

@EventBusSubscriber(modid = MODID)
public final class WindBladeHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(WIND_BLADE, player);
        if (level <= 0) {
            return;
        }

        AttributeInstance movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
        double attributeValue = movement != null ? Math.max(0.1D, movement.getValue()) : 0.1D;
        double extraSpeed = Math.max(0.0D, (attributeValue - 0.1D) / 0.1D);
        event.setAmount((float) (event.getAmount() * (1.0D + extraSpeed * Config.WIND_BLADE_BASE_DAMAGE_MULTIPLIER.get() * level)));
    }
}
