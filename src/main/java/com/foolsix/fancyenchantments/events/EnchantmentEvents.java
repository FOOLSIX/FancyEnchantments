package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.enchantment.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnchantmentEvents {
    @SubscribeEvent
    public void tooltip(ItemTooltipEvent e) {
        ItemStack itemStack = e.getItemStack();
        if (itemStack.getEnchantmentLevel(EATER_OF_SOULS.get()) > 0) {
            CompoundTag nbt = itemStack.getOrCreateTag();
            if (nbt.contains("eater_of_souls_killcount")) {
                e.getToolTip().add(Component.translatable("Kill Count:").append(String.valueOf(nbt.getInt("eater_of_souls_killcount"))).withStyle(ChatFormatting.DARK_PURPLE));
            }
        }
    }

    @SubscribeEvent
    public void playerTickEvent(TickEvent.PlayerTickEvent e) {
        if (e.player != null) {
            ((RollingStone) ROLLING_STONE.get()).dealDamageWhileSprinting(e);
            ((BlessedWind) BLESSED_WIND.get()).speedBoostWhileSprinting(e);
        }
    }

    @SubscribeEvent
    public void livingAttackEvent(LivingAttackEvent e) {
        if (e.getEntity() instanceof Player player) {
            ((Counterattack) COUNTERATTACK.get()).getBuff(player);
        }
    }

    @SubscribeEvent
    public void lootingLevelEvent(LootingLevelEvent lootingLevelEvent) {
        if (lootingLevelEvent.getDamageSource() == null)
            return;
        if (lootingLevelEvent.getDamageSource().getEntity() instanceof Player player) {
            ((AdvancedLooting) ADVANCED_LOOTING.get()).lootingHandle(lootingLevelEvent, player);
        }
    }

    @SubscribeEvent
    public void projectileImpactEvent(ProjectileImpactEvent e) {
        var hit = e.getRayTraceResult();
        if (hit.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hit).getEntity() instanceof Player player) {
            ((Counterattack) COUNTERATTACK.get()).getBuff(player);
        }
        ((Reflecting) REFLECTING.get()).projectReflecting(e);
    }

    @SubscribeEvent
    public void livingEvent(LivingEvent.LivingTickEvent e) {
        ((Lightness) LIGHTNESS.get()).livingEvent(e);
        ((SolidAsARock) SOLID_AS_A_ROCK.get()).addArmor(e);
        ((OceanCurrent) OCEAN_CURRENT.get()).attackSpeedBoost(e);
    }

    @SubscribeEvent
    public void hurtEvent(LivingHurtEvent e) {
        if (e.getSource() == null) {
            return;
        }

        Entity attacker = e.getSource().getEntity();
        Entity victim = e.getEntity();
        if (attacker instanceof LivingEntity) {
            ((GiftOfFire) GIFT_OF_FIRE.get()).doExtraDamage(e);
            ((Pyromaniac) PYROMANIAC.get()).receiveExplosive(e);
            ((RollingStone) ROLLING_STONE.get()).reduceDamageTakenWhileSprinting(e);
        }
    }

    @SubscribeEvent
    public void livingDeath(LivingDeathEvent e) {
        ((EaterOfSouls) EATER_OF_SOULS.get()).killcount(e);
        ((Overflow) OVERFLOW.get()).generateWater(e);
        ((FireDisaster) FIRE_DISASTER.get()).generateFire(e);
    }

    @SubscribeEvent
    public void arrowLooseEvent(ArrowLooseEvent e) {
        //Below cancel the event
        ((Empathy) EMPATHY.get()).throwPlayer(e);
    }


}
