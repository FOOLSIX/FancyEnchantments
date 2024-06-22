package com.foolsix.fancyenchantments.enchantment.util;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EnchUtils {
    public static final MutableComponent CURSE_PREFIX = Component.translatable("Curse:").withStyle(ChatFormatting.RED);
    public static final String MOD_NAME_PREFIX = "Fancy Enchantment:";

    public static final int ELEMENT_COUNT = 6;

    public enum Element {
        AER, AQUA, IGNIS, TERRA, HOLY, TWISTED;

        @Nullable
        public static Element getElement(Enchantment enchantment) {
            if (enchantment instanceof AerEnchantment) {
                return AER;
            }
            if (enchantment instanceof AquaEnchantment) {
                return AQUA;
            }
            if (enchantment instanceof IgnisEnchantment) {
                return IGNIS;
            }
            if (enchantment instanceof TerraEnchantment) {
                return TERRA;
            }
            if (enchantment instanceof HolyEnchantment) {
                return HOLY;
            }
            if (enchantment instanceof TwistedEnchantment) {
                return TWISTED;
            }
            return null;
        }

    }

    public static final Predicate<Entity> VISIBLE_HOSTILE =
            entity -> !entity.isSpectator() && isHostileToPlayer(entity);

    public static List<BlockPos> getRandomValidPos(Entity entity, Level world, int tryTimes) {
        BlockPos originPos = entity.blockPosition();
        RandomSource random = RandomSource.create();
        List<BlockPos> blockPosList = new ArrayList<>();
        while (tryTimes-- > 0) {
            BlockPos pos = new BlockPos(originPos.getX() + random.nextInt(3) - 1, originPos.getY() + random.nextInt(3) - 1, originPos.getZ() + random.nextInt(2));
            if (world.isEmptyBlock(pos) && !world.isEmptyBlock(pos.below())) {
                blockPosList.add(pos);
            }
        }
        return blockPosList;
    }

    public static void generateSimpleParticleAroundEntity(Entity entity, SimpleParticleType type) {
        if (entity.getLevel() instanceof ServerLevel level) {
            level.sendParticles(type, entity.getX(), entity.getY(), entity.getZ(),
                                30, 0.2D, 0.7D, 0.2D, 0);
        }
    }

    public static void generateSimpleParticleAroundEntity(Entity entity, SimpleParticleType type, int pParticleCount, double pXOffset, double pYOffset, double pZOffset, double pSpeed) {
        if (entity.getLevel() instanceof ServerLevel level) {
            level.sendParticles(type, entity.getX(), entity.getY(), entity.getZ(),
                                pParticleCount, pXOffset, pYOffset, pZOffset, pSpeed);
        }
    }


    public static boolean isHostileToPlayer(Entity entity) {
        return entity instanceof Monster && !(entity instanceof NeutralMob neutralMob && !neutralMob.isAngry());
    }

    public static boolean isHostileToLivingEntity(Entity entity, LivingEntity living) {
        return entity instanceof Monster && !(entity instanceof NeutralMob neutralMob && !neutralMob.isAngryAt(living));
    }

    public static LivingEntity getLookAtLivingEntity(LivingEntity viewer, float partialTicks, double reachDistance) {
        Vec3 eye = viewer.getEyePosition();
        Vec3 look = viewer.getViewVector(partialTicks);
        Vec3 end = eye.add(look.x * reachDistance, look.y * reachDistance, look.z * reachDistance);
        AABB searchBox =
                viewer.getBoundingBox().expandTowards(look.scale(reachDistance)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult result = ProjectileUtil.getEntityHitResult(viewer, eye, end, searchBox, VISIBLE_HOSTILE, reachDistance * reachDistance);
        if (result == null) {
            return null;
        } else if (result.getEntity() instanceof LivingEntity)
            return (LivingEntity) result.getEntity();

        return null;
    }
}
