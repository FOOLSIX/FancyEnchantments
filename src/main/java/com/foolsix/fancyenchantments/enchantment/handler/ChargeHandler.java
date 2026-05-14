package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.CHARGE;

@EventBusSubscriber(modid = MODID)
public final class ChargeHandler {
    private static final float CHARGE_DISTANCE_MULTIPLIER = 2.0F;
    private static final int INVINCIBLE_DURATION_PER_LEVEL = 5;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity living)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(CHARGE, living);
        if (level <= 0) {
            return;
        }

        Vec3 lookAt = living.getLookAngle();
        living.push(lookAt.x * CHARGE_DISTANCE_MULTIPLIER, lookAt.y * CHARGE_DISTANCE_MULTIPLIER, lookAt.z * CHARGE_DISTANCE_MULTIPLIER);
        if (living instanceof ServerPlayer player) {
            player.connection.send(
                    new ClientboundSetEntityMotionPacket(player)
            );
        }
        living.addEffect(new MobEffectInstance(EffectReg.INVINCIBLE, 5 + INVINCIBLE_DURATION_PER_LEVEL * level));
    }
}
