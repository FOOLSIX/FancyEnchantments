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

    private static final List<ElementConditionData> list = new ArrayList<>();

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

            Path path = pathProvider.json(data.enchantment());

            futures.add(
                    DataProvider.saveStable(
                            output,
                            GSON.toJsonTree(json),
                            path
                    )
            );
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private static List<ElementConditionData> collectConditions() {

        put(BLOOD_FEED.location(), Map.of(
                Element.TWISTED, 5,
                Element.AQUA, 5
        ), 0.1);

        put(BLOOD_SACRIFICE.location(), Map.of(
                Element.TWISTED, 3
        ), 0.1);

        put(LAVA_BURST.location(), Map.of(
                Element.IGNIS, 5,
                Element.TERRA, 3
        ), 0.25);

        put(MOUNTAIN_SUPREME_PROTECTION.location(), Map.of(
                Element.TERRA, 5
        ), 0.2);

        put(EATER_OF_SOULS.location(), Map.of(
                Element.TWISTED, 10
        ), 0.05);

        put(SOLID_AS_A_ROCK.location(), Map.of(
                Element.TERRA, 6
        ), 0.1);

        put(ARMOR_FORGING.location(), Map.of(
                Element.IGNIS, 6,
                Element.TERRA, 6
        ), 0.1);

        put(PURE_FATE.location(), Map.of(
                Element.HOLY, 3
        ), 0.1);

        put(SHARP_ROCK.location(), Map.of(
                Element.TERRA, 5
        ), 0.05);

        put(SACRED_SUPREME_SHARPNESS.location(), Map.of(
                Element.HOLY, 3
        ), 0.25);

        put(GREED_SUPREME_LOOTING.location(), Map.of(
                Element.TWISTED, 3
        ), 0.25);

        put(WIND_FIRE_WHEELS.location(), Map.of(
                Element.AER, 5,
                Element.IGNIS, 5
        ), 0.1);

        put(SPREADING_SPORES.location(), Map.of(
                Element.AQUA, 5,
                Element.TERRA, 5
        ), 0.2);

        put(THRILLING_THUNDER.location(), Map.of(
                Element.AER, 5,
                Element.AQUA, 5
        ), 0.2);

        put(STANDING_WALL.location(), Map.of(
                Element.TERRA, 8
        ), 0.1);

        put(FEARLESS_CHALLENGER.location(), Map.of(
                Element.HOLY, 8
        ), 0.1);

        put(SIGHS_OF_ASHES.location(), Map.of(
                Element.IGNIS, 8
        ), 0.1);

        return list;
    }

    private static void put(
            ResourceLocation enchantment,
            Map<Element, Integer> values,
            double chance
    ) {
        list.add(new ElementConditionData(enchantment, values, chance));
    }

    @Override
    public String getName() {
        return "Element Conditions";
    }
}