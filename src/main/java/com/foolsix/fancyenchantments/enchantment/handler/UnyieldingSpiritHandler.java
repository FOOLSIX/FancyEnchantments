package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.attachment.TimeToLiveHelper;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Enemy;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.UNYIELDING_SPIRIT;

@EventBusSubscriber(modid = MODID)
public final class UnyieldingSpiritHandler {
    private static final ResourceKey<DamageType> GIVE_UP =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "give_up"));

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        if (EnchUtils.getEnchantmentLevel(UNYIELDING_SPIRIT, player) <= 0
                || event.getSource() == player.damageSources().fellOutOfWorld()
                || event.getSource() == player.damageSources().genericKill()
                || event.getNewDamage() < player.getHealth()
                || TimeToLiveHelper.getTtl(player) != -1) {
            return;
        }

        int extraTime = Config.UNYIELDING_SPIRIT_EXTRA_TIME_SECONDS.get() * 20;
        if (Config.UNYIELDING_SPIRIT_SLOWNESS_LEVEL.get() > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, extraTime, Config.UNYIELDING_SPIRIT_SLOWNESS_LEVEL.get() - 1));
        }
        if (Config.UNYIELDING_SPIRIT_DAMAGE_RESISTANCE_LEVEL.get() > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, extraTime, Config.UNYIELDING_SPIRIT_DAMAGE_RESISTANCE_LEVEL.get() - 1));
        }
        if (Config.UNYIELDING_SPIRIT_ENABLE_BLINDNESS.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, extraTime));
        }

        player.setHealth((float) (player.getMaxHealth() * Config.UNYIELDING_SPIRIT_HEALTH_PERCENTAGE.get()));
        TimeToLiveHelper.setTtl(player, extraTime);
        player.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak(Config.UNYIELDING_SPIRIT_BASE_DAMAGE.get(), player, EquipmentSlot.HEAD);
        event.setNewDamage(0);
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer player) || !(event.getEntity() instanceof Enemy)) {
            return;
        }

        if (TimeToLiveHelper.getTtl(player) == -1) {
            return;
        }

        TimeToLiveHelper.setTtl(player, -1);
        player.setHealth(player.getMaxHealth());
        EnchUtils.generateSimpleParticleAroundEntity(player, net.minecraft.core.particles.ParticleTypes.TOTEM_OF_UNDYING, 100, 1.0D, 1.0D, 1.0D, 0.5D);
        player.clearFire();
        player.getActiveEffects().stream()
                .map(MobEffectInstance::getEffect)
                .filter(effect -> effect.value().getCategory() == MobEffectCategory.HARMFUL)
                .toList()
                .forEach(player::removeEffect);
        player.level().playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 3.0F, 1.0F);
    }

    @SubscribeEvent
    public static void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || event.getSlot() != EquipmentSlot.HEAD || TimeToLiveHelper.getTtl(player) == -1) {
            return;
        }

        if (EnchUtils.getEnchantmentLevel(UNYIELDING_SPIRIT, event.getTo(), player.registryAccess()) > 0) {
            return;
        }

        player.hurt(player.damageSources().source(GIVE_UP, player), 100000.0F);
    }

    @SubscribeEvent
    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        int ttl = TimeToLiveHelper.getTtl(player);
        if (ttl == -1) {
            return;
        }

        if (ttl == 0 && !player.isDeadOrDying()) {
            player.hurt(player.damageSources().genericKill(), Float.MAX_VALUE);
        }
        TimeToLiveHelper.subTtl(player, 1);
    }
}
