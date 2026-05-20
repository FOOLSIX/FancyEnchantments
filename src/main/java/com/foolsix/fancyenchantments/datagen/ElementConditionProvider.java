package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.*;

public final class ElementConditionProvider implements DataProvider {

    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

    private final PackOutput.PathProvider pathProvider;

    public ElementConditionProvider(PackOutput output) {
        this.pathProvider =
                output.createPathProvider(
                        PackOutput.Target.DATA_PACK,
                        "element_condition"
                );
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (ElementConditionData data : collectConditions()) {

            JsonElement json =
                    ElementConditionData.CODEC
                            .encodeStart(JsonOps.INSTANCE, data)
                            .getOrThrow();

            Path path =
                    pathProvider.json(data.enchantment());

            futures.add(
                    DataProvider.saveStable(
                            output,
                            GSON.toJsonTree(json),
                            path
                    )
            );
        }

        return CompletableFuture.allOf(
                futures.toArray(CompletableFuture[]::new)
        );
    }

    private static List<ElementConditionData> collectConditions() {

        List<ElementConditionData> list = new ArrayList<>();

        put(list, BLOOD_FEED.location(), Map.of(
                Element.TWISTED, 5,
                Element.AQUA, 5
        ));

        put(list, BLOOD_SACRIFICE.location(), Map.of(
                Element.TWISTED, 3
        ));

        put(list, LAVA_BURST.location(), Map.of(
                Element.IGNIS, 5,
                Element.TERRA, 3
        ));

        put(list, MOUNTAIN_SUPREME_PROTECTION.location(), Map.of(
                Element.TERRA, 5
        ));

        return list;
    }

    private static void put(
            List<ElementConditionData> list,
            ResourceLocation enchantment,
            Map<Element, Integer> values
    ) {
        list.add(new ElementConditionData(enchantment, values));
    }

    @Override
    public String getName() {
        return "Element Conditions";
    }
}