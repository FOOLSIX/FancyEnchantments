package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.FALLING_STONE;

@EventBusSubscriber(modid = MODID)
public final class FallingStoneHandler {
    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        int level = EnchUtils.getEnchantmentLevel(FALLING_STONE, player);
        if (level <= 0 || player.onGround() || player.fallDistance <= 2.0F) {
            return;
        }

        List<Entity> entities = player.level().getEntities(player, player.getBoundingBox().inflate(0.3 * level, 0.3, 0.3 * level));
        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity living) || !EnchUtils.isHostileToLivingEntity(living, player)) {
                continue;
            }

            float fallDistance = player.fallDistance;
            living.hurt(player.damageSources().playerAttack(player), fallDistance * (1.0F + (float) Config.FALLING_STONE_DAMAGE_MULTIPLIER.get().doubleValue() * level));
            Vec3 pushAngle = new Vec3(living.getX() - player.getX(), 0.0D, living.getZ() - player.getZ());
            living.push(pushAngle.x, 0.1D, pushAngle.z);
            player.resetFallDistance();
        }
    }
}
