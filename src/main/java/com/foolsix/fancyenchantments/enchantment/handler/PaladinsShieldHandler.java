package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;
import java.util.Objects;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.PALADINS_SHIELD;

@EventBusSubscriber(modid = MODID)
public final class PaladinsShieldHandler {
    private static final ResourceKey<DamageType> GENERAL_ENCHANTMENT_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "general_enchantment_damage"));

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack shield = getShieldWithEnchantment(player);
        if (shield.isEmpty()) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(PALADINS_SHIELD, shield, player.registryAccess());
        if (level <= 0) {
            return;
        }

        event.setAmount(event.getAmount() * (1.0F - (float) Config.PALADINS_SHIELD_BASE_DAMAGE_REDUCTION_RATIO.get().doubleValue() * level));
    }

    @SubscribeEvent
    public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
        if (event.getSource().is(GENERAL_ENCHANTMENT_DAMAGE)) {
            return;
        }

        LivingEntity victim = event.getEntity();
        List<Player> transferTargets = null;
        if (victim instanceof OwnableEntity ownable) {
            transferTargets = victim.level().getEntitiesOfClass(Player.class, victim.getBoundingBox().inflate(5.0D),
                    player -> Objects.equals(ownable.getOwnerUUID(), player.getUUID()) && hasPaladinsShield(player));
        } else if (victim instanceof Player) {
            transferTargets = victim.level().getEntitiesOfClass(Player.class, victim.getBoundingBox().inflate(5.0D), PaladinsShieldHandler::hasPaladinsShield);
        }

        if (transferTargets == null || transferTargets.isEmpty()) {
            return;
        }

        Player player = transferTargets.getFirst();
        float damage = (float) (event.getNewDamage()
                * Config.PALADINS_SHIELD_DAMAGE_TRANSFER_RATIO.get()
                * Config.PALADINS_SHIELD_TRANSFERRED_DAMAGE_RATIO.get());
        if (damage >= player.getHealth() || damage >= player.getMaxHealth() * Config.PALADINS_SHIELD_UPPER_LIMIT.get()) {
            return;
        }

        DamageSource damageSource = player.damageSources().source(GENERAL_ENCHANTMENT_DAMAGE, victim);
        player.hurt(damageSource, damage);
        event.setNewDamage((float) (event.getNewDamage() * (1.0D - Config.PALADINS_SHIELD_DAMAGE_TRANSFER_RATIO.get())));
    }

    private static boolean hasPaladinsShield(Player player) {
        return !getShieldWithEnchantment(player).isEmpty();
    }

    private static ItemStack getShieldWithEnchantment(Player player) {
        ItemStack offhand = player.getOffhandItem();
        if (offhand.is(Tags.Items.TOOLS_SHIELD) && EnchUtils.getEnchantmentLevel(PALADINS_SHIELD, offhand, player.registryAccess()) > 0) {
            return offhand;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (mainHand.is(Tags.Items.TOOLS_SHIELD) && EnchUtils.getEnchantmentLevel(PALADINS_SHIELD, mainHand, player.registryAccess()) > 0) {
            return mainHand;
        }
        return ItemStack.EMPTY;
    }
}
