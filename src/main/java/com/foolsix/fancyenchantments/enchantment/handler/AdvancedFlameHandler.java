package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ADVANCED_FLAME;

@EventBusSubscriber(modid = MODID)
public final class AdvancedFlameHandler {
    private static final String LEVEL_TAG = MODID + ":advanced_flame_level";
    private static final int PROJECTILE_FIRE_SECONDS = 30;
    private static final int HIT_FIRE_TICKS = 8 * 20;

    private AdvancedFlameHandler() {
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof AbstractArrow arrow) || !(arrow.getOwner() instanceof LivingEntity shooter)) {
            return;
        }

        int level = Math.max(getLevel(shooter.getMainHandItem()), getLevel(shooter.getOffhandItem()));
        if (level <= 0) {
            return;
        }

        arrow.igniteForSeconds(PROJECTILE_FIRE_SECONDS);
        arrow.getPersistentData().putInt(LEVEL_TAG, level);
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getProjectile().level().isClientSide()) {
            return;
        }
        if (!(event.getProjectile() instanceof AbstractArrow arrow) || arrow.getPersistentData().getInt(LEVEL_TAG) <= 0) {
            return;
        }

        HitResult hit = event.getRayTraceResult();
        if (hit instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof LivingEntity living) {
            living.setRemainingFireTicks(Math.max(0, living.getRemainingFireTicks()) + HIT_FIRE_TICKS);
        }
    }

    private static int getLevel(ItemStack stack) {
        for (var entry : EnchUtils.enchantmentsOn(stack).entrySet()) {
            if (EnchUtils.matchesKey(entry.getKey(), ADVANCED_FLAME)) {
                return entry.getIntValue();
            }
        }
        return 0;
    }
}
