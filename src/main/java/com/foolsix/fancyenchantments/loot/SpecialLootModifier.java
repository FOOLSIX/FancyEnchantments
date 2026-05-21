package com.foolsix.fancyenchantments.loot;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.ArrayList;
import java.util.List;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.LITHIC_SIPHON;

public final class SpecialLootModifier extends LootModifier {
    public static final MapCodec<SpecialLootModifier> CODEC =
            RecordCodecBuilder.mapCodec(instance -> codecStart(instance).apply(instance, SpecialLootModifier::new));

    public SpecialLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (handleLithicSiphon(generatedLoot, context)) {
            return generatedLoot;
        }

        if (!isChest(context) || !(context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Player player)) {
            return generatedLoot;
        }

        RandomSource random = context.getRandom();
        HolderLookup.Provider registries = context.getLevel().registryAccess();

        if (random.nextDouble() < Config.CHEST_LOOT_MOD_BOOK_CHANCE.get()) {
            EnchUtils.getRandomModEnchantment(random)
                    .filter(enchantment -> random.nextDouble() < getChanceOfRarity(enchantment, registries))
                    .ifPresent(enchantment -> generatedLoot.add(createRandomBook(enchantment, random, registries)));
        }

        int[] elementStats = EnchUtils.getElementStatsFromEquipment(player);
        List<ResourceKey<Enchantment>> candidates = new ArrayList<>();
        for (ResourceLocation enchantment : EnchUtils.getAllSpecialLootEnchantments()) {
            if (EnchUtils.tryGenerateOnce(elementStats, enchantment)) {
                ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, enchantment);
                candidates.add(key);
            }
        }

        if (candidates.isEmpty()) {
            return generatedLoot;
        }

        ResourceKey<Enchantment> enchantment = candidates.get(random.nextInt(candidates.size()));
        generatedLoot.add(createRandomBook(enchantment, random, registries));
        return generatedLoot;
    }

    private static boolean isChest(LootContext context) {
        return String.valueOf(context.getQueriedLootTableId()).contains("chests/");
    }

    private static boolean handleLithicSiphon(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.getParamOrNull(LootContextParams.BLOCK_STATE) != null
                && context.getParam(LootContextParams.BLOCK_STATE).is(Tags.Blocks.STONES)
                && context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Player player
                && EnchUtils.getEnchantmentLevel(LITHIC_SIPHON, player) > 0) {
            generatedLoot.clear();
            return true;
        }
        return false;
    }

    private static ItemStack createRandomBook(ResourceKey<Enchantment> enchantment, RandomSource random, HolderLookup.Provider registries) {
        var holder = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);
        int level = random.nextInt(Math.max(holder.value().getMaxLevel(), 1)) + 1;
        return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(holder, level));
    }

    private static double getChanceOfRarity(ResourceKey<Enchantment> enchantment, HolderLookup.Provider registries) {
        int weight = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment).value().getWeight();
        if (weight <= 1) {
            return Config.CHEST_LOOT_VERY_RARE_CHANCE.get();
        }
        if (weight <= 2) {
            return Config.CHEST_LOOT_RARE_CHANCE.get();
        }
        if (weight <= 5) {
            return Config.CHEST_LOOT_UNCOMMON_CHANCE.get();
        }
        return Config.CHEST_LOOT_COMMON_CHANCE.get();
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
