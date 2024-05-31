package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.enchantment.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnchantmentEvents {
    @SubscribeEvent
    public void tooltip(ItemTooltipEvent e) {
        ((EaterOfSouls) EATER_OF_SOULS.get()).tooltip(e);

    }

    @SubscribeEvent
    public void playerTickEvent(TickEvent.PlayerTickEvent e) {
        if (e.player != null && e.side.isServer() && e.phase == TickEvent.Phase.START) {
            ((FeatherFall) FEATHER_FALL.get()).gainEffect(e);
            ((CursedGaze) CURSED_GAZE.get()).cursedGaze(e);
            ((FallingStone) FALLING_STONE.get()).doFallingDamage(e);
            ((RollingStone) ROLLING_STONE.get()).dealDamageWhileSprinting(e);
            ((BlessedWind) BLESSED_WIND.get()).speedBoostWhileSprinting(e);
            ((Bloodthirsty) BLOODTHIRSTY.get()).getHungry(e);
        }

        if (e.player != null && e.side.isClient() && e.phase == TickEvent.Phase.START) {
            ((Floating) FLOATING.get()).damageReduce(e);

        }
    }

    @SubscribeEvent
    public void livingAttackEvent(LivingAttackEvent e) {
        LivingEntity victim = e.getEntity();

        if (victim instanceof Player player) {
            ((Counterattack) COUNTERATTACK.get()).getBuff(player);
        }
        if (e.getSource() == null || e.getSource().getEntity() == null) return;
        Entity attacker = e.getSource().getEntity();

        if (attacker instanceof Player player) {
            ((Cumbersome) CUMBERSOME.get()).getCooldown(e);
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
    public void livingTickEvent(LivingEvent.LivingTickEvent e) {
        if (!e.isCanceled() && e.getEntity() != null && !e.getEntity().level.isClientSide()) {
            ((Lightness) LIGHTNESS.get()).livingEvent(e);
            ((OceanCurrent) OCEAN_CURRENT.get()).attackSpeedBoost(e);
        }
    }

    @SubscribeEvent
    public void hurtEvent(LivingHurtEvent e) {
        if (e.isCanceled() || e.getSource() == null) {
            return;
        }

        Entity attacker = e.getSource().getEntity();
        Entity victim = e.getEntity();
        if (attacker instanceof LivingEntity) {
            ((GiftOfFire) GIFT_OF_FIRE.get()).doExtraDamage(e);
            ((HeavyBlow) HEAVY_BLOW.get()).criticalHit(e);
            ((WindBlade) WIND_BLADE.get()).damageBoost(e);
            ((Bloodthirsty) BLOODTHIRSTY.get()).gainFoodLevel(e);
            ((RollingStone) ROLLING_STONE.get()).reduceDamageTakenWhileSprinting(e);
            ((Pyromaniac) PYROMANIAC.get()).receiveExplosive(e);
        }
        if (victim instanceof LivingEntity) {
            ((Calmer) CALMER.get()).gainBuff(e);
        }
    }

    @SubscribeEvent
    public void livingDeath(LivingDeathEvent e) {
        if (!e.isCanceled() && e.getSource() != null) {
            ((UnyieldingSpirit) UNYIELDING_SPIRIT.get()).clearTag(e);
            ((EaterOfSouls) EATER_OF_SOULS.get()).killcount(e);
            ((Overflow) OVERFLOW.get()).generateWater(e);
            ((FireDisaster) FIRE_DISASTER.get()).generateFire(e);
        }

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void playerDeath(LivingHurtEvent e) {
        if (!e.isCanceled() && !e.getEntity().level.isClientSide) {
            ((UnyieldingSpirit) UNYIELDING_SPIRIT.get()).preventDeath(e);
        }
    }

    @SubscribeEvent
    public void arrowLooseEvent(ArrowLooseEvent e) {
        if (!e.isCanceled() && !e.getLevel().isClientSide()) {
            //Below cancel the event
            ((Empathy) EMPATHY.get()).throwPlayer(e);
        }
    }

    @SubscribeEvent
    public void itemAttributeModifierEvent(ItemAttributeModifierEvent e) {
        if (e.isCanceled() || e.getItemStack() == null) return;

        ((WindBlade) WIND_BLADE.get()).damageReduce(e);
        ((HeavyBlow) HEAVY_BLOW.get()).attackSpeedReduce(e);
        ((SolidAsARock) SOLID_AS_A_ROCK.get()).addArmor(e);
    }

    @SubscribeEvent
    public void dropEvent(LivingDropsEvent e) {
        if (e.getSource() != null && e.getSource().getEntity() != null) {
            ((Hungry) HUNGRY.get()).dropFoods(e);
        }
    }

    @SubscribeEvent
    public void equipmentChange(LivingEquipmentChangeEvent e) {
        ((UnyieldingSpirit) UNYIELDING_SPIRIT.get()).unequipped(e);
    }


}
