package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.foolsix.fancyenchantments.enchantment.effect.AddFireTimeEffect;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EnchantmentTags;
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
    }

    private static net.minecraft.world.level.storage.loot.predicates.LootItemCondition.Builder nonBypassInvulnerabilityRequirement() {
        return DamageSourceCondition.hasDamageSource(
                DamageSourcePredicate.Builder.damageType()
                        .tag(net.minecraft.advancements.critereon.TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY))
        );
    }
}
