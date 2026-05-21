package com.foolsix.fancyenchantments.resource.catalyst;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class CatalystResourceLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().create();

    public CatalystResourceLoader() {
        super(GSON, "catalysts");
    }

    @Override
    protected void apply(
            Map<ResourceLocation, JsonElement> entries,
            @NotNull ResourceManager resourceManager,
            @NotNull ProfilerFiller profiler
    ) {
        Catalyst.catalystDataMap.clear();

        for (JsonElement jsonElement : entries.values()) {
            CatalystData catalystData = GSON.fromJson(jsonElement, CatalystData.class);
            if (catalystData == null || catalystData.item() == null) {
                continue;
            }

            Map<Catalyst, Integer> catalystWeights = new HashMap<>();
            for (Map.Entry<String, Integer> entry : catalystData.catalysisOrEmpty().entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    continue;
                }

                Catalyst catalyst = Catalyst.getCatalyst(entry.getKey());
                if (catalyst != null) {
                    catalystWeights.put(catalyst, entry.getValue());
                }
            }

            if (!catalystWeights.isEmpty()) {
                Catalyst.catalystDataMap.put(catalystData.item(), catalystWeights);
            }
        }
    }
}
