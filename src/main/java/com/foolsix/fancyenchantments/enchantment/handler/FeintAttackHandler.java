package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.FEINT_ATTACK;

@EventBusSubscriber(modid = MODID)
public final class FeintAttackHandler {
    private static final ResourceKey<DamageType> GENERAL_ENCHANTMENT_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(MODID, "general_enchantment_damage"));

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player) || event.getSource().is(GENERAL_ENCHANTMENT_DAMAGE)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(FEINT_ATTACK, player);
        if (level <= 0 || !player.getMainHandItem().canPerformAction(ItemAbilities.SWORD_SWEEP) || player.getAttackStrengthScale(0.5F) <= 0.9F) {
            return;
        }

        List<Entity> entities = event.getEntity().level().getEntities(event.getEntity(), event.getEntity().getBoundingBox().inflate(3.0D), EnchUtils::isHostileToPlayer);
        DamageSource damageSource = player.damageSources().source(GENERAL_ENCHANTMENT_DAMAGE, player);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity living) {
                EnchUtils.generateSimpleParticleAroundEntity(living, net.minecraft.core.particles.ParticleTypes.SOUL);
                living.hurt(damageSource, event.getAmount() * (1.0F + (float) Config.FEINT_ATTACK_DAMAGE_MULTIPLIER.get().doubleValue() * level));
            }
        }
        event.setAmount(event.getAmount() * (1.0F - (float) Config.FEINT_ATTACK_DAMAGE_REDUCER.get().doubleValue()));
    }
}
