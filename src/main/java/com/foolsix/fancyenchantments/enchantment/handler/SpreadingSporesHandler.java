package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.SPREADING_SPORES;

@EventBusSubscriber(modid = MODID)
public final class SpreadingSporesHandler {
    private static final String SPORE_TAG = "fe_spores";

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            int level = EnchUtils.getEnchantmentLevel(SPREADING_SPORES, player);
            if (level <= 0) return;

            LivingEntity victim = event.getEntity();
            int spores = victim.getPersistentData().getInt(SPORE_TAG);
            int sporeCap = Config.SPREADING_SPORES_SPORE_CAP.get();
            if (spores + level >= sporeCap) {
                victim.getPersistentData().putInt(SPORE_TAG, (spores + level) % sporeCap);
                event.setAmount((float) (event.getAmount() * Config.SPREADING_SPORES_DAMAGE_MULTIPLIER.get()));
                victim.addEffect(new MobEffectInstance(MobEffects.POISON, level * 40, level - 1));
                EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.SPORE_BLOSSOM_AIR);
            } else {
                victim.getPersistentData().putInt(SPORE_TAG, spores + level);
            }
        }
    }
}
