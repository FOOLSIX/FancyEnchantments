package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ROCKET_JUMP;

public class RocketJump extends IgnisEnchantment {
    private static final ModConfig.RocketJumpOptions CONFIG = FancyEnchantments.getConfig().rocketJumpOptions;
    private final Vec3 DOWN_DIRECTION = new Vec3(0, -1, 0);
    private final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator() {
        @Override
        public @NotNull Optional<Float> getBlockExplosionResistance(Explosion pExplosion, BlockGetter pReader, BlockPos pPos, BlockState pState, FluidState pFluid) {
            return Optional.of(0f);
        }
    };

    public RocketJump() {
        super(CONFIG, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[] { EquipmentSlot.FEET });
    }

    public void explode(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        if (player != null) {
            int level = EnchantmentHelper.getEnchantmentLevel(ROCKET_JUMP.get(), player);
            if (level > 0) {
                HitResult hitResult = player.pick(4, 5f, false);
                var lookAt = player.getLookAngle();
                if (hitResult.getType() == HitResult.Type.BLOCK && lookAt.normalize().dot(DOWN_DIRECTION) > 0.8) {
                    Vec3 explosionCenter = player.position().add(0, -0.5, 0);
                    double crouchingBonus = player.isCrouching() ? level : 1;
                    double onAirBonus = player.isOnGround() ? 1 : level;
                    Vec3 force = player.position()
                            .subtract(explosionCenter)
                            .add(lookAt.multiply(1, 0, 1).normalize())
                            .multiply(1, 0.5 + 0.5 * crouchingBonus, 1)
                            .multiply(onAirBonus, onAirBonus, onAirBonus);
                    player.push(force.x(), force.y(), force.z());
                    if (player.level instanceof ServerLevel world) {
                        world.playSound(null, new BlockPos(explosionCenter), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat() * 0.2F) * 0.7F);
                        world.sendParticles(ParticleTypes.EXPLOSION, explosionCenter.x(), explosionCenter.y(), explosionCenter.z(), 10, 0, 0, 0, 1);
                    }
                }
            }
        }
    }
}
