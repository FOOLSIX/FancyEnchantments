package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.WIND_FIRE_WHEELS;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public final class WindFireWheelsClientHandler {
    @SubscribeEvent
    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide() || Minecraft.getInstance().player != player) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(WIND_FIRE_WHEELS, player);
        if (level <= 0 || !player.isSprinting() || player.onGround()) {
            return;
        }

        Vec3 movement = player.getDeltaMovement();
        double y = Minecraft.getInstance().options.keyJump.isDown() ? 0.3D : 0.0D;
        Vec3 look = player.getLookAngle();
        player.setDeltaMovement(movement.x(), y, movement.z());
        player.push(look.x * Config.WIND_FIRE_WHEELS_SPEED_MULTIPLIER.get(), 0.0D, look.z * Config.WIND_FIRE_WHEELS_SPEED_MULTIPLIER.get());
    }
}
