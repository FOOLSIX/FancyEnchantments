package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.SIGHS_OF_ASHES;

@EventBusSubscriber(modid = MODID)
public final class SighsOfAshesHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            int level = EnchUtils.getEnchantmentLevel(SIGHS_OF_ASHES, player);
            LivingEntity victim = event.getEntity();
            if (level > 0 && victim.isOnFire() && player.getAttackStrengthScale(0.5F) > 0.95) {
                int rest = victim.getRemainingFireTicks() / 20;
                victim.clearFire();
                victim.level().playSound(null, victim.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.5F + victim.level().random.nextFloat() * 0.4F);
                EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.SMOKE);
                event.setAmount((float) (event.getAmount() + rest * Config.SIGHS_OF_ASHES_DAMAGE_PER_SECOND.get()));
            }
        }
    }
}
