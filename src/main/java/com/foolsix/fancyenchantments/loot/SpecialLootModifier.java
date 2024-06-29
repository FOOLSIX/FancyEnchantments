package com.foolsix.fancyenchantments.loot;

import com.foolsix.fancyenchantments.capability.ElementStatsCapabilityProvider;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class SpecialLootModifier extends LootModifier {
    public static final Supplier<Codec<SpecialLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, SpecialLootModifier::new)));

    protected SpecialLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @NonNull
    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loots, LootContext context) {
        if (isChest(context)) {
            if (context.getParamOrNull(LootContextParams.THIS_ENTITY) instanceof ServerPlayer player) {
                player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).ifPresent(stats -> {
                    //todo
                });
            }
        }

        return loots;
    }

    private boolean isChest(LootContext context) {
        return String.valueOf(context.getQueriedLootTableId()).contains("chests/");
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
