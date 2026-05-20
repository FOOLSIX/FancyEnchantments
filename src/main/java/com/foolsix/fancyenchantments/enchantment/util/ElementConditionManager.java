package com.foolsix.fancyenchantments.enchantment.util;

import com.foolsix.fancyenchantments.datagen.ElementConditionData;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class ElementConditionManager extends SimpleJsonResourceReloadListener {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<ResourceLocation, int[]> CONDITIONS =
            new HashMap<>();

    public ElementConditionManager() {
        super(
                new GsonBuilder().create(),
                "element_condition"
        );
    }

    public static int[] getCondition(ResourceLocation id) {
        return CONDITIONS.getOrDefault(
                id,
                EnchUtils.EMPTY_CONDITION
        );
    }

    public static int[] getCondition(ResourceLocation id, Enchantment ignored) {
        return getCondition(id);
    }

    public static Set<ResourceLocation> getConditionalEnchantments() {
        return CONDITIONS.keySet();
    }

    @Override
    protected void apply(
            Map<ResourceLocation, JsonElement> entries,
            @NotNull ResourceManager resourceManager,
            @NotNull ProfilerFiller profiler
    ) {

        CONDITIONS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : entries.entrySet()) {

            ResourceLocation fileId = entry.getKey();
            JsonElement json = entry.getValue();

            if (!json.isJsonObject()) {
                LOGGER.error("Skipping invalid element condition file: {}", fileId);
                continue;
            }

            ElementConditionData.CODEC
                    .parse(JsonOps.INSTANCE, json)
                    .resultOrPartial(error ->
                            LOGGER.error(
                                    "Failed to parse element condition {}: {}",
                                    fileId,
                                    error
                            )
                    )
                    .ifPresent(data -> {

                        int[] arr =
                                new int[EnchUtils.ELEMENT_COUNT];

                        for (Map.Entry<Element, Integer> e :
                                data.values().entrySet()) {

                            arr[e.getKey().ordinal()] =
                                    e.getValue();
                        }

                        CONDITIONS.put(
                                data.enchantment(),
                                arr
                        );
                    });
        }

        LOGGER.info(
                "Loaded {} element conditions",
                CONDITIONS.size()
        );
    }
}