package com.foolsix.fancyenchantments.events;

import com.foolsix.fancyenchantments.enchantment.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

    @OnlyIn(Dist.CLIENT)
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
            ((Nirvana) NIRVANA.get()).getRegeneration(e);
            ((SharpRock) SHARP_ROCK.get()).updateDamage(e);
            ((WindFireWheels) WIND_FIRE_WHEELS.get()).avoidFallDamage(e);
        } else if (e.player != null && e.side.isClient() && e.phase == TickEvent.Phase.START) {
            ((Floating) FLOATING.get()).damageReduce(e);
        } else if (e.player != null && e.side.isClient()) {
            ((WindFireWheels) WIND_FIRE_WHEELS.get()).SprintAndFly(e);
        }
    }

    @SubscribeEvent
    public void livingAttackEvent(LivingAttackEvent e) {
        LivingEntity victim = e.getEntity();
        DamageSource source = e.getSource();

        if (victim instanceof Player player) {
            ((Counterattack) COUNTERATTACK.get()).getBuff(player);
            ((StandingWall) STANDING_WALL.get()).getAttacked(e);
        }

        if (source != null && source.getEntity() instanceof LivingEntity) {
            //use entity.push(), should be called at client and server
            ((Charge) CHARGE.get()).charge(e);
            ((Recoil) RECOIL.get()).push(e);
        }

    }

    @SubscribeEvent
    public void lootingLevelEvent(LootingLevelEvent e) {
        if (e.isCanceled() || e.getDamageSource() == null)
            return;
        if (e.getDamageSource().getEntity() instanceof Player player) {
            ((AdvancedLooting) ADVANCED_LOOTING.get()).lootingHandle(e, player);
            ((GreedySupremeLooting) GREEDY_SUPREME_LOOTING.get()).lootingHandle(e, player);
        }
    }

    @SubscribeEvent
    public void projectileImpactEvent(ProjectileImpactEvent e) {
        if (e.isCanceled() || e.getEntity() == null || e.getEntity().level().isClientSide()) return;

        var hit = e.getRayTraceResult();
        if (hit.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hit).getEntity() instanceof Player player) {
            ((Counterattack) COUNTERATTACK.get()).getBuff(player);
        }
        ((Reflecting) REFLECTING.get()).projectReflecting(e);
    }

    @SubscribeEvent
    public void livingTickEvent(LivingEvent.LivingTickEvent e) {
        if (!e.isCanceled() && e.getEntity() != null && !e.getEntity().level().isClientSide()) {
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
        LivingEntity victim = e.getEntity();
        if (attacker instanceof LivingEntity living && !living.level().isClientSide()) {
            //add
            ((GiftOfFire) GIFT_OF_FIRE.get()).doExtraDamage(e);
            ((AirAttack) AIR_ATTACK.get()).attack(e);
            //multiply
            ((HeavyBlow) HEAVY_BLOW.get()).criticalHit(e);
            ((WindBlade) WIND_BLADE.get()).damageBoost(e);
            ((RollingStone) ROLLING_STONE.get()).reduceDamageTakenWhileSprinting(e);
            ((FeintAttack) FEINT_ATTACK.get()).attack(e);
            ((BloodSacrifice) BLOOD_SACRIFICE.get()).attack(e);
            ((PaladinsShield) PALADINS_SHIELD.get()).reduceDamage(e);
            ((ConditionOverload) CONDITION_OVERLOAD.get()).attack(e);
            //Do not modify damage
            ((Bloodthirsty) BLOODTHIRSTY.get()).gainFoodLevel(e);
        }

        if (victim instanceof LivingEntity && !victim.level().isClientSide) {
            //below cancel the event
            ((Pyromaniac) PYROMANIAC.get()).receiveExplosive(e);
            if(e.isCanceled()) return;

            //add
            ((BubbleShield) BUBBLE_SHIELD.get()).reduceDamage(e);
            //multiply
            ((DuellistsPrerogative) DUELLIST.get()).hurtSingle(e);
            //Do not modify damage
            ((ArmorForging) ARMOR_FORGING.get()).hurtForging(e);

        }
    }

    @SubscribeEvent
    public void livingDeath(LivingDeathEvent e) {
        if (!e.isCanceled() && e.getSource() != null && e.getSource().getEntity() != null) {
            ((UnyieldingSpirit) UNYIELDING_SPIRIT.get()).clearTag(e);
            ((EaterOfSouls) EATER_OF_SOULS.get()).killcount(e);
            ((Overflow) OVERFLOW.get()).generateWater(e);
            ((FireDisaster) FIRE_DISASTER.get()).generateFire(e);

            if (e.getEntity() != null)
                ((Purifying) PURIFYING.get()).purify(e);
        }

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void playerDeath(LivingDamageEvent e) {
        if (!e.isCanceled() && !e.getEntity().level().isClientSide) {
            ((UnyieldingSpirit) UNYIELDING_SPIRIT.get()).preventDeath(e);
        }
    }

    @SubscribeEvent
    public void LivingDamageEvent(LivingDamageEvent e) {
        if (!e.isCanceled() && !e.getEntity().level().isClientSide) {
            //below cancel the event
            ((Afterimage) AFTERIMAGE.get()).avoidDamage(e);
            if (e.isCanceled()) return;

            ((MountainSupremeProtection) MOUNTAIN_SUPREME_PROTECTION.get()).reduceDamage(e);
            ((PaladinsShield) PALADINS_SHIELD.get()).whenHurtTransDamage(e);
            ((SpreadingSpores) SPREADING_SPORES.get()).damageSpread(e);
            ((FearlessChallenger) FEARLESS_CHALLENGER.get()).damageBonus(e);

        }
    }

    @SubscribeEvent
    public void arrowLooseEvent(ArrowLooseEvent e) {
        if (!e.isCanceled()) {
            //Below cancel the event
            //use entity.push(), should be called at client and server
            ((Empathy) EMPATHY.get()).throwPlayer(e);
        }
    }

    @SubscribeEvent
    public void itemAttributeModifierEvent(ItemAttributeModifierEvent e) {
        if (e.isCanceled() || e.getItemStack() == null) return;

        ((TheFallen) THE_FALLEN.get()).addDamageBonus(e);
        ((WindBlade) WIND_BLADE.get()).damageReduce(e);
        ((HeavyBlow) HEAVY_BLOW.get()).attackSpeedReduce(e);
        ((SolidAsARock) SOLID_AS_A_ROCK.get()).addArmor(e);
        ((Melter) MELTER.get()).attribute(e);
        ((StackingWaves) STACKING_WAVES.get()).attribute(e);
        ((ArmorForging) ARMOR_FORGING.get()).modifyArmor(e);
        ((SharpRock) SHARP_ROCK.get()).attachAttributes(e);
        ((Sander) SANDER.get()).attribute(e);
    }

    @SubscribeEvent
    public void dropEvent(LivingDropsEvent e) {
        if (e.getSource() != null && e.getSource().getEntity() != null) {
            ((Hungry) HUNGRY.get()).dropFoods(e);
        }
    }

    @SubscribeEvent
    public void equipmentChange(LivingEquipmentChangeEvent e) {
        ((Pervert) PERVERT.get()).dropOnWear(e);
        ((UnyieldingSpirit) UNYIELDING_SPIRIT.get()).unequipped(e);
    }

    @SubscribeEvent
    public void useItemFinish(LivingEntityUseItemEvent.Finish e) {
        if (!e.isCanceled()) {
            ((Eucharist) EUCHARIST.get()).getBuff(e);
        }
    }

    @SubscribeEvent
    public void babySpawn(BabyEntitySpawnEvent e) {
        if (e.getCausedByPlayer() instanceof ServerPlayer) {
            ((PureFate) PURE_FATE.get()).removeCurse(e);
        }
    }

}
