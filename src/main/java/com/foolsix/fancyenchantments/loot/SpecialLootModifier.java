package com.foolsix.fancyenchantments.loot;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.capability.ElementStatsCapabilityProvider;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.LITHIC_SIPHON;

public class SpecialLootModifier extends LootModifier {
    public static final Supplier<Codec<SpecialLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, SpecialLootModifier::new)));
    private static final ModConfig.ChestLootOptions CONFIG = FancyEnchantments.getConfig().chestLootOptions;

    protected SpecialLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @NonNull
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loots, LootContext context) {
        if (handleLithicSiphon(loots, context)) {
            return loots;
        }
        RandomSource rand = context.getRandom();
        if (isChest(context)) {
            if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof ServerPlayer player) {
                if (Math.random() < CONFIG.chanceOfSpawningBook) {
                    ItemStack enchantedBook = Items.ENCHANTED_BOOK.getDefaultInstance();
                    Enchantment enchantment = EnchUtils.getRandomModEnchantment(rand);
                    if (enchantment != null) {
                        EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentInstance(enchantment, rand.nextInt(enchantment.getMaxLevel()) + 1));
                        loots.add(enchantedBook);
                    }
                }
                player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).ifPresent(stats -> {
                    for (final Enchantment enchantment : EnchUtils.getAllSpecialLootEnchantment()) {
                        if (enchantment instanceof FEBaseEnchantment fe && fe.tryGenerateOnce(stats.getStats())) {
                            ItemStack enchantedBook = Items.ENCHANTED_BOOK.getDefaultInstance();
                            //1 ~ maxLevel - 1
                            int level = Math.max(1, rand.nextInt(fe.getMaxLevel()));
                            EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentInstance(fe, level));
                            loots.add(enchantedBook);
                        }
                    }
                });
            }
        }

        return loots;
    }

    private boolean isChest(LootContext context) {
        return String.valueOf(context.getQueriedLootTableId()).contains("chests/");
    }

    private boolean handleLithicSiphon(ObjectArrayList<ItemStack> loots, LootContext context) {
        if (context.getParamOrNull(LootContextParams.BLOCK_STATE) != null && context.getParam(LootContextParams.BLOCK_STATE).is(Tags.Blocks.STONE) && context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof Player player) {
            if (EnchantmentHelper.getEnchantmentLevel(LITHIC_SIPHON.get(), player) > 0) {
                loots.clear();
                return true;
            }
        }
        return false;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
