package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.foolsix.fancyenchantments.enchantment.effect.AddFireTimeEffect;
import com.foolsix.fancyenchantments.enchantment.effect.ApplyMobEffectIfAbsentEffect;
import com.foolsix.fancyenchantments.enchantment.effect.BullyingEffect;
import com.foolsix.fancyenchantments.enchantment.effect.CalmerEffect;
import com.foolsix.fancyenchantments.enchantment.effect.ApplyMobEffectWithChanceEffect;
import com.foolsix.fancyenchantments.enchantment.effect.DrowningEffect;
import com.foolsix.fancyenchantments.enchantment.effect.ErodingEffect;
import com.foolsix.fancyenchantments.enchantment.effect.LavaBurstEffect;
import com.foolsix.fancyenchantments.enchantment.effect.PurificationSlashEffect;
import com.foolsix.fancyenchantments.enchantment.effect.RecoilEffect;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.Tags;

import static com.foolsix.fancyenchantments.effect.EffectReg.MAELSTROM;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.*;

public final class EnchantmentGenerator {

    private EnchantmentGenerator() {
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<MobEffect> mobEffects = context.lookup(Registries.MOB_EFFECT);

        context.register(
                ABYSSAL_MAELSTROM,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(60, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new ApplyMobEffect(
                                        HolderSet.direct(mobEffects.getOrThrow(MAELSTROM.getKey())),
                                        LevelBasedValue.perLevel(5.0F),
                                        LevelBasedValue.perLevel(5.0F),
                                        LevelBasedValue.constant(0.0F),
                                        LevelBasedValue.constant(0.0F)
                                )
                        )
                        .build(ABYSSAL_MAELSTROM.location())
        );

        context.register(
                ADVANCED_FIRE_ASPECT,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.FIRE_ASPECT_ENCHANTABLE),
                                        1,
                                        2,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(60, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new AddFireTimeEffect(LevelBasedValue.perLevel(8.0F))
                        )
                        .exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.FIRE_ASPECT)))
                        .build(ADVANCED_FIRE_ASPECT.location())
        );

        context.register(
                ADVANCED_FLAME,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                        1,
                                        1,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(60, 20),
                                        8,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.FLAME)))
                        .build(ADVANCED_FLAME.location())
        );

        context.register(
                ADVANCED_LOOTING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(15, 9),
                                        Enchantment.dynamicCost(65, 9),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.EQUIPMENT_DROPS,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new AddValue(LevelBasedValue.perLevel(0.02F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.ATTACKER,
                                        EntityPredicate.Builder.entity().of(EntityType.PLAYER)
                                )
                        )
                        .exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.LOOTING)))
                        .build(ADVANCED_LOOTING.location())
        );

        context.register(
                ADVANCED_PROTECTION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        2,
                                        4,
                                        Enchantment.dynamicCost(10, 8),
                                        Enchantment.dynamicCost(60, 8),
                                        4,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(2.0F)),
                                nonBypassInvulnerabilityRequirement()
                        )
                        .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                        .build(ADVANCED_PROTECTION.location())
        );

        context.register(
                ADVANCED_SHARPNESS,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                        2,
                                        5,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(21, 11),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(2.5F, 1.0F))
                        )
                        .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .build(ADVANCED_SHARPNESS.location())
        );

        context.register(
                AFTERIMAGE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(25, 20),
                                        Enchantment.dynamicCost(75, 20),
                                        8,
                                        EquipmentSlotGroup.LEGS
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(AFTERIMAGE.location())
        );

        context.register(
                AILMENT_DEVOURER,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(AILMENT_DEVOURER.location())
        );

        context.register(
                AIR_ATTACK,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(AIR_ATTACK.location())
        );

        context.register(
                ARMOR_FORGING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(25, 20),
                                        Enchantment.dynamicCost(75, 20),
                                        8,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .build(ARMOR_FORGING.location())
        );

        context.register(
                BEYOND_THE_FLASH,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        1,
                                        Enchantment.constantCost(30),
                                        Enchantment.constantCost(80),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .build(BEYOND_THE_FLASH.location())
        );

        context.register(
                BLESSED_WIND,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                        5,
                                        3,
                                        Enchantment.dynamicCost(13, 8),
                                        Enchantment.dynamicCost(58, 8),
                                        4,
                                        EquipmentSlotGroup.FEET
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "blessed_wind/walk_speed"),
                                        Attributes.MOVEMENT_SPEED,
                                        LevelBasedValue.perLevel(0.01F),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.LOCATION_CHANGED,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "blessed_wind/sprint_speed"),
                                        Attributes.MOVEMENT_SPEED,
                                        LevelBasedValue.perLevel(0.03F),
                                        AttributeModifier.Operation.ADD_VALUE
                                ),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setSprinting(true)).build()
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.LOCATION_CHANGED,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "blessed_wind/step_height"),
                                        Attributes.STEP_HEIGHT,
                                        LevelBasedValue.perLevel(1.0F),
                                        AttributeModifier.Operation.ADD_VALUE
                                ),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setSprinting(true)).build()
                                )
                        )
                        .build(BLESSED_WIND.location())
        );

        context.register(
                BLIND_LOYALTY,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.VANISHING_ENCHANTABLE),
                                        10,
                                        1,
                                        Enchantment.constantCost(1),
                                        Enchantment.constantCost(35),
                                        8,
                                        EquipmentSlotGroup.ANY
                                )
                        )
                        .build(BLIND_LOYALTY.location())
        );

        context.register(
                BLOOD_FEED,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .build(BLOOD_FEED.location())
        );

        context.register(
                BLOOD_SACRIFICE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .build(BLOOD_SACRIFICE.location())
        );

        context.register(
                BLOODTHIRSTY,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        1,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .build(BLOODTHIRSTY.location())
        );

        context.register(
                BUBBLE_SHIELD,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        5,
                                        3,
                                        Enchantment.dynamicCost(13, 8),
                                        Enchantment.dynamicCost(58, 8),
                                        4,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .build(BUBBLE_SHIELD.location())
        );

        context.register(
                BULLYING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new BullyingEffect()
                        )
                        .build(BULLYING.location())
        );

        context.register(
                CALMER,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        2,
                                        5,
                                        Enchantment.dynamicCost(10, 5),
                                        Enchantment.dynamicCost(60, 5),
                                        4,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.VICTIM,
                                EnchantmentTarget.VICTIM,
                                new CalmerEffect(LevelBasedValue.constant(5.0F), LevelBasedValue.constant(3.0F))
                        )
                        .build(CALMER.location())
        );

        context.register(
                CHARGE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .build(CHARGE.location())
        );

        context.register(
                CONDITION_OVERLOAD,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .build(CONDITION_OVERLOAD.location())
        );

        context.register(
                COUNTERATTACK,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(Tags.Items.TOOLS_SHIELD),
                                        5,
                                        3,
                                        Enchantment.dynamicCost(10, 5),
                                        Enchantment.dynamicCost(20, 5),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .build(COUNTERATTACK.location())
        );

        context.register(
                CRACKED_CROWN,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.HEAD
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(CRACKED_CROWN.location())
        );

        context.register(
                CUMBERSOME,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.ATTACKER,
                                new ApplyMobEffectWithChanceEffect(
                                        mobEffects.getOrThrow(EffectReg.CUMBERSOME.getKey()),
                                        0.05F,
                                        LevelBasedValue.constant(3.0F)
                                )
                        )
                        .build(CUMBERSOME.location())
        );

        context.register(
                CURSED_GAZE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 5),
                                        Enchantment.dynamicCost(70, 5),
                                        8,
                                        EquipmentSlotGroup.HEAD
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .build(CURSED_GAZE.location())
        );

        context.register(
                DEDICATION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(DEDICATION.location())
        );

        context.register(
                DELAYED_EXECUTION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                        items.getOrThrow(ItemTags.AXES),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new ApplyMobEffectIfAbsentEffect(
                                        mobEffects.getOrThrow(EffectReg.PRISON_CAGE.getKey()),
                                        LevelBasedValue.constant(5.0F)
                                )
                        )
                        .build(DELAYED_EXECUTION.location())
        );

        context.register(
                DEXTERITY,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "dexterity/entity_interaction_range"),
                                        Attributes.ENTITY_INTERACTION_RANGE,
                                        LevelBasedValue.perLevel(0.5F),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        )
                        .build(DEXTERITY.location())
        );

        context.register(
                DOMINION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        1,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .build(DOMINION.location())
        );

        context.register(
                DOWNWIND,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(DOWNWIND.location())
        );

        context.register(
                DROWNING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.ATTACKER,
                                new DrowningEffect(-40, 1.0F)
                        )
                        .build(DROWNING.location())
        );

        context.register(
                DUELLISTS_PREROGATIVE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(HolderSet.direct(enchantments.getOrThrow(FEINT_ATTACK)))
                        .build(DUELLISTS_PREROGATIVE.location())
        );

        context.register(
                EATER_OF_SOULS,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .build(EATER_OF_SOULS.location())
        );

        context.register(
                EMPATHY,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .build(EMPATHY.location())
        );

        context.register(
                ERODING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new ErodingEffect(0.15F, 1)
                        )
                        .build(ERODING.location())
        );

        context.register(
                EUCHARIST,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(EUCHARIST.location())
        );

        context.register(
                FALLING_STONE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                        5,
                                        3,
                                        Enchantment.dynamicCost(5, 5),
                                        Enchantment.dynamicCost(15, 5),
                                        4,
                                        EquipmentSlotGroup.FEET
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .build(FALLING_STONE.location())
        );

        context.register(
                FEARLESS_CHALLENGER,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(FEARLESS_CHALLENGER.location())
        );

        context.register(
                FEATHER_FALL,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.LEGS
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .withEffect(
                                EnchantmentEffectComponents.LOCATION_CHANGED,
                                new ApplyMobEffect(
                                        HolderSet.direct(net.minecraft.world.effect.MobEffects.SLOW_FALLING),
                                        LevelBasedValue.constant(1.0F),
                                        LevelBasedValue.constant(1.0F),
                                        LevelBasedValue.constant(0.0F),
                                        LevelBasedValue.constant(0.0F)
                                ),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().flags(
                                                EntityFlagsPredicate.Builder.flags()
                                                        .setOnGround(false)
                                                        .setCrouching(true)
                                        ).build()
                                )
                        )
                        .build(FEATHER_FALL.location())
        );

        context.register(
                FEINT_ATTACK,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.SWEEPING_EDGE), enchantments.getOrThrow(DUELLISTS_PREROGATIVE)))
                        .build(FEINT_ATTACK.location())
        );

        context.register(
                FIRE_DISASTER,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .build(FIRE_DISASTER.location())
        );

        context.register(
                FLOATING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .withEffect(
                                EnchantmentEffectComponents.LOCATION_CHANGED,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "floating/attack_damage"),
                                        Attributes.ATTACK_DAMAGE,
                                        LevelBasedValue.constant(-0.1F),
                                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                ),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().flags(
                                                EntityFlagsPredicate.Builder.flags()
                                                        .setOnGround(true)
                                        ).build()
                                )
                        )
                        .build(FLOATING.location())
        );

        context.register(
                FROZEN_HEART,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.VICTIM,
                                EnchantmentTarget.ATTACKER,
                                new ApplyMobEffect(
                                        HolderSet.direct(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN),
                                        LevelBasedValue.constant(5.0F),
                                        LevelBasedValue.constant(5.0F),
                                        LevelBasedValue.perLevel(1.0F),
                                        LevelBasedValue.perLevel(1.0F)
                                )
                        )
                        .build(FROZEN_HEART.location())
        );

        context.register(
                GALE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.MINING_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(GALE.location())
        );

        context.register(
                GIFT_OF_FIRE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        10,
                                        5,
                                        Enchantment.dynamicCost(5, 8),
                                        Enchantment.dynamicCost(15, 8),
                                        2,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .build(GIFT_OF_FIRE.location())
        );

        context.register(
                GREED_SUPREME_LOOTING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        items.getOrThrow(ItemTags.AXES),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.LOOTING)))
                        .build(GREED_SUPREME_LOOTING.location())
        );

        context.register(
                HEAVY_ARROW,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .withEffect(
                                EnchantmentEffectComponents.KNOCKBACK,
                                new AddValue(LevelBasedValue.perLevel(1.0F))
                        )
                        .build(HEAVY_ARROW.location())
        );

        context.register(
                HEAVY_BLOW,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(15, 5),
                                        Enchantment.dynamicCost(65, 5),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .build(HEAVY_BLOW.location())
        );

        context.register(
                HUNGRY,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(8, 5),
                                        Enchantment.dynamicCost(18, 5),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .build(HUNGRY.location())
        );

        context.register(
                ICY_BURST,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .build(ICY_BURST.location())
        );

        context.register(
                LAVA_BURST,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(25, 20),
                                        Enchantment.dynamicCost(75, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new LavaBurstEffect()
                        )
                        .build(LAVA_BURST.location())
        );

        context.register(
                LIGHTNESS,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(Tags.Items.TOOLS_SHIELD),
                                        10,
                                        3,
                                        Enchantment.dynamicCost(8, 5),
                                        Enchantment.dynamicCost(23, 5),
                                        2,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(LIGHTNESS.location())
        );

        context.register(
                LITHIC_SIPHON,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.MINING_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .build(LITHIC_SIPHON.location())
        );

        context.register(
                MELTER,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new ApplyMobEffect(
                                        HolderSet.direct(mobEffects.getOrThrow(EffectReg.MELTING.getKey())),
                                        LevelBasedValue.perLevel(3.0F),
                                        LevelBasedValue.perLevel(3.0F),
                                        LevelBasedValue.perLevel(0.0F, 1.0F),
                                        LevelBasedValue.perLevel(0.0F, 1.0F)
                                )
                        )
                        .build(MELTER.location())
        );

        context.register(
                MOUNTAIN_SUPREME_PROTECTION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        1,
                                        4,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(3.0F)),
                                nonBypassInvulnerabilityRequirement()
                        )
                        .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                        .build(MOUNTAIN_SUPREME_PROTECTION.location())
        );

        context.register(
                MULTIPLE_SHOT,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .build(MULTIPLE_SHOT.location())
        );

        context.register(
                NIGHTMARE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.HEAD
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .build(NIGHTMARE.location())
        );

        context.register(
                NIRVANA,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .build(NIRVANA.location())
        );

        context.register(
                OCEAN_CURRENT,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(15, 5),
                                        Enchantment.dynamicCost(65, 5),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .build(OCEAN_CURRENT.location())
        );

        context.register(
                OVERFLOW,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(10, 10),
                                        Enchantment.dynamicCost(60, 10),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .build(OVERFLOW.location())
        );

        context.register(
                OVER_HEALING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(25, 20),
                                        Enchantment.dynamicCost(75, 20),
                                        8,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(OVER_HEALING.location())
        );

        context.register(
                PALADINS_SHIELD,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(Tags.Items.TOOLS_SHIELD),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(5, 5),
                                        Enchantment.dynamicCost(15, 5),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(PALADINS_SHIELD.location())
        );

        context.register(
                PERVERT,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.LEG_ARMOR_ENCHANTABLE),
                                        1,
                                        1,
                                        Enchantment.dynamicCost(15, 10),
                                        Enchantment.dynamicCost(65, 10),
                                        8,
                                        EquipmentSlotGroup.LEGS
                                )
                        )
                        .build(PERVERT.location())
        );

        context.register(
                PURE_FATE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        1,
                                        3,
                                        Enchantment.dynamicCost(25, 20),
                                        Enchantment.dynamicCost(75, 20),
                                        8,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(PURE_FATE.location())
        );

        context.register(
                PURIFICATION_SLASH,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        1,
                                        1,
                                        Enchantment.dynamicCost(15, 10),
                                        Enchantment.dynamicCost(65, 10),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new PurificationSlashEffect()
                        )
                        .build(PURIFICATION_SLASH.location())
        );

        context.register(
                PURIFYING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                        5,
                                        5,
                                        Enchantment.dynamicCost(20, 2),
                                        Enchantment.dynamicCost(70, 2),
                                        4,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(3.0F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .entityType(EntityTypePredicate.of(EntityTypeTags.SENSITIVE_TO_SMITE))
                                                .build()
                                )
                        )
                        .exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.SMITE)))
                        .build(PURIFYING.location())
        );

        context.register(
                PYROMANIAC,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE),
                                        1,
                                        5,
                                        Enchantment.dynamicCost(5, 5),
                                        Enchantment.dynamicCost(55, 5),
                                        8,
                                        EquipmentSlotGroup.CHEST
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .exclusiveWith(HolderSet.direct(enchantments.getOrThrow(Enchantments.BLAST_PROTECTION)))
                        .build(PYROMANIAC.location())
        );

        context.register(
                RECOIL,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        3,
                                        3,
                                        Enchantment.dynamicCost(11, 10),
                                        Enchantment.dynamicCost(61, 10),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.ATTACKER,
                                new RecoilEffect(0.2F, LevelBasedValue.perLevel(0.5F))
                        )
                        .build(RECOIL.location())
        );

        context.register(
                REFLECTING,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(Tags.Items.TOOLS_SHIELD),
                                        5,
                                        5,
                                        Enchantment.dynamicCost(10, 5),
                                        Enchantment.dynamicCost(20, 5),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(REFLECTING.location())
        );

        context.register(
                ROCKET_JUMP,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                        3,
                                        3,
                                        Enchantment.dynamicCost(11, 10),
                                        Enchantment.dynamicCost(61, 10),
                                        8,
                                        EquipmentSlotGroup.FEET
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .build(ROCKET_JUMP.location())
        );

        context.register(
                ROLLING_STONE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                        3,
                                        3,
                                        Enchantment.dynamicCost(9, 8),
                                        Enchantment.dynamicCost(54, 8),
                                        4,
                                        EquipmentSlotGroup.FEET
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .build(ROLLING_STONE.location())
        );

        context.register(
                SACRED_SUPREME_SHARPNESS,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                        items.getOrThrow(ItemTags.AXES),
                                        2,
                                        5,
                                        Enchantment.dynamicCost(11, 10),
                                        Enchantment.dynamicCost(61, 10),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(1.5F, 5.0F))
                        )
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(1.5F, 0.0F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .entityType(EntityTypePredicate.of(EntityTypeTags.SENSITIVE_TO_SMITE))
                                                .build()
                                )
                        )
                        .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .build(SACRED_SUPREME_SHARPNESS.location())
        );

        context.register(
                SANDER,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        3,
                                        3,
                                        Enchantment.dynamicCost(11, 10),
                                        Enchantment.dynamicCost(61, 10),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .build(SANDER.location())
        );

        context.register(
                SELF_IMMOLATION,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        3,
                                        1,
                                        Enchantment.dynamicCost(11, 10),
                                        Enchantment.dynamicCost(61, 10),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .build(SELF_IMMOLATION.location())
        );

        context.register(
                SHARP_ROCK,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        items.getOrThrow(Tags.Items.TOOLS_SHIELD),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .build(SHARP_ROCK.location())
        );

        context.register(
                SIGHS_OF_ASHES,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.IGNIS, c))
                        .build(SIGHS_OF_ASHES.location())
        );

        context.register(
                SOLID_AS_A_ROCK,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(Tags.Items.TOOLS_SHIELD),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(13, 5),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "solid_as_a_rock/armor"),
                                        Attributes.ARMOR,
                                        LevelBasedValue.perLevel(0.15F),
                                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "solid_as_a_rock/armor_toughness"),
                                        Attributes.ARMOR_TOUGHNESS,
                                        LevelBasedValue.perLevel(0.1F),
                                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "solid_as_a_rock/movement_speed"),
                                        Attributes.MOVEMENT_SPEED,
                                        LevelBasedValue.perLevel(-0.1F),
                                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                                )
                        )
                        .build(SOLID_AS_A_ROCK.location())
        );

        context.register(
                SPREADING_SPORES,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .build(SPREADING_SPORES.location())
        );

        context.register(
                STACKING_WAVES,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        3,
                                        3,
                                        Enchantment.dynamicCost(11, 10),
                                        Enchantment.dynamicCost(61, 10),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AQUA, c))
                        .withEffect(
                                EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        ResourceLocation.fromNamespaceAndPath("fancyenchantments", "stacking_waves/attack_speed"),
                                        Attributes.ATTACK_SPEED,
                                        LevelBasedValue.perLevel(-0.1F),
                                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                                )
                        )
                        .build(STACKING_WAVES.location())
        );

        context.register(
                STANDING_WALL,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(Tags.Items.TOOLS_SHIELD),
                                        3,
                                        1,
                                        Enchantment.dynamicCost(11, 10),
                                        Enchantment.dynamicCost(61, 10),
                                        8,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TERRA, c))
                        .build(STANDING_WALL.location())
        );

        context.register(
                STREAMLINE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(STREAMLINE.location())
        );

        context.register(
                THE_FALLEN,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.TWISTED, c))
                        .build(THE_FALLEN.location())
        );

        context.register(
                THRILLING_THUNDER,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(THRILLING_THUNDER.location())
        );

        context.register(
                UNYIELDING_SPIRIT,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.constantCost(25),
                                        Enchantment.constantCost(75),
                                        8,
                                        EquipmentSlotGroup.HEAD
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.HOLY, c))
                        .build(UNYIELDING_SPIRIT.location())
        );

        context.register(
                WIND_BLADE,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        2,
                                        3,
                                        Enchantment.dynamicCost(15, 5),
                                        Enchantment.dynamicCost(65, 5),
                                        8,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(WIND_BLADE.location())
        );

        context.register(
                WIND_FIRE_WHEELS,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                                        2,
                                        1,
                                        Enchantment.dynamicCost(20, 20),
                                        Enchantment.dynamicCost(70, 20),
                                        8,
                                        EquipmentSlotGroup.FEET
                                )
                        )
                        .withCustomName(c -> EnchUtils.applyElementStyle(Element.AER, c))
                        .build(WIND_FIRE_WHEELS.location())
        );
    }

    private static net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder nonBypassInvulnerabilityRequirement() {
        return DamageSourceCondition.hasDamageSource(
                DamageSourcePredicate.Builder.damageType()
                        .tag(net.minecraft.advancements.critereon.TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
        );
    }
}
