package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ROLLING_STONE;

@EventBusSubscriber(modid = MODID)
public final class RollingStoneHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        int level = EnchUtils.getEnchantmentLevel(ROLLING_STONE, player);
        if (!player.isSprinting() || level <= 0 || !(player.level() instanceof ServerLevel world))
            return;

        List<Entity> entities = world.getEntities(player, player.getBoundingBox().inflate(0.3, 0.3, 0.3));
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity monster && EnchUtils.isHostileToLivingEntity(monster, player)) {
                float v = player.getSpeed();
                entity.hurt(player.damageSources().playerAttack(player), (level * Config.ROLLING_STONE_DAMAGE_MULTIPLIER.get().floatValue() * v));
                Vec3 pushAngel = new Vec3(monster.getX() - player.getX(), 0, monster.getZ() - player.getZ());
                entity.push(pushAngel.x * v * 2, 0.2, pushAngel.z * v * 2);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent e) {
        int level = EnchUtils.getEnchantmentLevel(ROLLING_STONE, e.getEntity());
        if (level <= 0) return;

        e.setAmount((e.getAmount() * Math.max(1 - Config.ROLLING_STONE_DAMAGE_REDUCER.get().floatValue() * level, Config.ROLLING_STONE_LOWER_LIMIT.get().floatValue())));
    }
}
