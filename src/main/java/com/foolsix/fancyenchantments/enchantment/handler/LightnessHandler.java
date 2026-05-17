package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.LIGHTNESS;

@EventBusSubscriber(modid = MODID)
public final class LightnessHandler {
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(MODID, "lightness/movement_speed");

    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        var movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        movementSpeed.removeModifier(MODIFIER_ID);
        int level = player.isBlocking() ? EnchUtils.getEnchantmentLevel(LIGHTNESS, player.getUseItem(), player.registryAccess()) : 0;
        if (level <= 0) {
            return;
        }

        movementSpeed.addTransientModifier(new AttributeModifier(
                MODIFIER_ID,
                Config.LIGHTNESS_SPEED_MULTIPLIER.get() * level,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ));
    }
}
