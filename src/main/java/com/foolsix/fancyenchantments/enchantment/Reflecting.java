package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.ProjectileImpactEvent;


public class Reflecting extends AerEnchantment {
    public static final String NAME = "reflecting";
    private static final ModConfig.ReflectingOptions CONFIG = FancyEnchantments.getConfig().reflectingOptions;

    public Reflecting() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 5 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 10;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    //Reference @Tinkers' Construct 3 reflecting
    //todo: fix reflected projectile cant hit very close enemy
    public void projectReflecting(ProjectileImpactEvent e) {
        Entity entity = e.getEntity();
        if (entity.level.isClientSide) return;
        Projectile projectile = e.getProjectile();
        HitResult hitResult = e.getRayTraceResult();
        if (hitResult.getType() == HitResult.Type.ENTITY
                && ((EntityHitResult) hitResult).getEntity() instanceof LivingEntity living
                && living != projectile.getOwner()) {
            int reflectingLevel = EnchantmentHelper.getEnchantmentLevel(this, living);
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
            projectile.shoot(reboundAngle.x, reboundAngle.y, reboundAngle.z, 1.0f + CONFIG.baseVelocity * reflectingLevel, 0);
            if (projectile instanceof AbstractHurtingProjectile hurting) {
                hurting.xPower = reboundAngle.x * 0.1;
                hurting.yPower = reboundAngle.y * 0.1;
                hurting.zPower = reboundAngle.z * 0.1;
            }
            if (living.getType() == EntityType.PLAYER) {
                if (living instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(projectile));
                }
            }
            living.level.playSound(null, living.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.5F + living.level.random.nextFloat() * 0.4F);
            ItemStack stack = living.getUseItem();
            stack.hurtAndBreak(CONFIG.baseDamage, living, (p) -> p.broadcastBreakEvent(living.getUsedItemHand()));
            e.setCanceled(true);
        }
    }
}
