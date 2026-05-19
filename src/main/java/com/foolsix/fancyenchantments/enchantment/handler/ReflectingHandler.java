package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.REFLECTING;

@EventBusSubscriber(modid = MODID)
public final class ReflectingHandler {
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent e) {
        Projectile projectile = e.getProjectile();
        if (projectile.level().isClientSide) return;

        HitResult hitResult = e.getRayTraceResult();
        if (hitResult == null
                || hitResult.getType() != HitResult.Type.ENTITY
                || !(((EntityHitResult) hitResult).getEntity() instanceof LivingEntity living)
                || living == projectile.getOwner()) {
            return;
        }

        int reflectingLevel = EnchUtils.getEnchantmentLevel(REFLECTING, living);
        if (reflectingLevel < 1 || !living.isBlocking() || !EnchUtils.canBlock(living, projectile.position()))
            return;

        if (projectile instanceof AbstractArrow arrow) {
            AbstractArrow.Pickup pickup = arrow.pickup;
            arrow.setOwner(living);
            arrow.pickup = pickup;
        } else {
            projectile.setOwner(living);
        }

        Vec3 reboundAngle = living.getLookAngle();
        projectile.shoot(reboundAngle.x, reboundAngle.y, reboundAngle.z, 1.0f + Config.REFLECTING_BASE_VELOCITY.get().floatValue() * reflectingLevel, 0);
        if (projectile instanceof AbstractHurtingProjectile hurting) {

        }
        if (living.getType() == EntityType.PLAYER) {
            if (living instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(projectile));
            }
        }
        living.level().playSound(null, living.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.5F + living.level().random.nextFloat() * 0.4F);
        ItemStack stack = living.getUseItem();
        InteractionHand hand = living.getUsedItemHand();
        EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        stack.hurtAndBreak(Config.REFLECTING_BASE_DAMAGE.get(), living, slot);
        e.setCanceled(true);
    }
}
