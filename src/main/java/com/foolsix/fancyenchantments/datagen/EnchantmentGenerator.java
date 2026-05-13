package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.foolsix.fancyenchantments.enchantment.effect.AddFireTimeEffect;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.*;

public final class EnchantmentGenerator {

    private EnchantmentGenerator() {
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);

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
    }
}
