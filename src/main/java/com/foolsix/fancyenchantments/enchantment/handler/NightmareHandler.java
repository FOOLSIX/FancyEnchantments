package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.NIGHTMARE;

@EventBusSubscriber(modid = MODID)
public final class NightmareHandler {
    @SubscribeEvent
    public static void onCanPlayerSleep(CanPlayerSleepEvent event) {
        Player player = event.getEntity();
        int level = EnchUtils.getEnchantmentLevel(NIGHTMARE, player);
        if (level <= 0) {
            return;
        }

        BlockPos pos = event.getPos();
        if (!event.getState().is(net.minecraft.tags.BlockTags.BEDS)) {
            return;
        }

        Vec3 center = pos.getCenter();
        float radius = (float) (Config.NIGHTMARE_EXPLODE_RADIUS_BASE.get()
                + (level - 1) * Config.NIGHTMARE_EXPLODE_RADIUS_PER_LEVEL.get());
        float damage = (float) (Config.NIGHTMARE_EXPLODE_DAMAGE_BASE.get()
                + (level - 1) * Config.NIGHTMARE_EXPLODE_DAMAGE_PER_LEVEL.get());
        player.level().explode(
                null,
                player.damageSources().badRespawnPointExplosion(center),
                new ExplosionDamageCalculator() {
                    @Override
                    public float getEntityDamageAmount(Explosion explosion, Entity entity) {
                        return damage;
                    }
                },
                center,
                radius,
                false,
                Level.ExplosionInteraction.BLOCK
        );
        event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
    }
}
