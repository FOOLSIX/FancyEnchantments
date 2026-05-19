package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ROCKET_JUMP;

@EventBusSubscriber(modid = MODID)
public final class RocketJumpHandler {
    private static final Vec3 DOWN_DIRECTION = new Vec3(0, -1, 0);

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (player == null) return;

        int level = EnchUtils.getEnchantmentLevel(ROCKET_JUMP, player);
        if (level <= 0) return;

        HitResult hitResult = player.pick(4, 5f, false);
        Vec3 lookAt = player.getLookAngle();
        if (hitResult.getType() == HitResult.Type.BLOCK && lookAt.normalize().dot(DOWN_DIRECTION) > 0.8) {
            Vec3 explosionCenter = player.position().add(0, -0.5, 0);
            double crouchingBonus = player.isCrouching() ? level : 1;
            double onAirBonus = player.onGround() ? 1 : level;
            Vec3 force = player.position()
                    .subtract(explosionCenter)
                    .add(lookAt.multiply(1, 0, 1).normalize())
                    .multiply(1, 0.5 + 0.5 * crouchingBonus, 1)
                    .multiply(onAirBonus, onAirBonus, onAirBonus);
            player.push(force.x(), force.y(), force.z());
            if (player.level() instanceof ServerLevel world) {
                world.playSound(null, BlockPos.containing(explosionCenter), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 4.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat() * 0.2F) * 0.7F);
                world.sendParticles(ParticleTypes.EXPLOSION, explosionCenter.x(), explosionCenter.y(), explosionCenter.z(), 10, 0, 0, 0, 1);
            }
        }
    }
}
