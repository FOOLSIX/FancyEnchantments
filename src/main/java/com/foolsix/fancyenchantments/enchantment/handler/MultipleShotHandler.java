package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.MULTIPLE_SHOT;

@EventBusSubscriber(modid = MODID)
public final class MultipleShotHandler {
    private static final String TAG_NAME = MODID + ":multiple_shot";

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof AbstractArrow arrow)
                || arrow.getPersistentData().getBoolean(TAG_NAME)
                || arrow.getPersistentData().contains("apoth.generated")
                || !(arrow.getOwner() instanceof LivingEntity shooter)
                || !(shooter.level() instanceof ServerLevel level)) {
            return;
        }

        int enchantmentLevel = EnchUtils.getEnchantmentLevel(MULTIPLE_SHOT, shooter);
        if (enchantmentLevel <= 0) {
            return;
        }

        ItemStack pickupItem = arrow.getPickupItemStackOrigin().copyWithCount(1);
        ItemStack weapon = arrow.getWeaponItem();
        if (!(pickupItem.getItem() instanceof ArrowItem arrowItem)) {
            return;
        }

        for (int index = 1; index <= enchantmentLevel; ++index) {
            AbstractArrow clone = arrowItem.createArrow(level, pickupItem, shooter, weapon);
            clone.setBaseDamage(arrow.getBaseDamage());
            clone.setCritArrow(arrow.isCritArrow());
            clone.setRemainingFireTicks(arrow.getRemainingFireTicks());
            clone.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            clone.getPersistentData().merge(arrow.getPersistentData().copy());
            clone.getPersistentData().putBoolean(TAG_NAME, true);
            clone.shootFromRotation(
                    shooter,
                    shooter.getXRot() - (float) ((shooter.getRandom().nextDouble() - 0.5D) * 10.0D),
                    shooter.getYRot() - (float) ((shooter.getRandom().nextDouble() - 0.5D) * 10.0D),
                    0.0F,
                    3.0F,
                    1.0F
            );
            level.addFreshEntity(clone);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow) || !arrow.getPersistentData().getBoolean(TAG_NAME)) {
            return;
        }

        HitResult hitResult = event.getRayTraceResult();
        if (hitResult instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof LivingEntity living) {
            living.invulnerableTime = 0;
        }
    }
}
