package com.foolsix.fancyenchantments.datagen;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public record ElementConditionData(
        ResourceLocation enchantment,
        Map<Element, Integer> values,
        double chance
) {

    public static final Codec<ElementConditionData> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            ResourceLocation.CODEC
                                    .fieldOf("enchantment")
                                    .forGetter(ElementConditionData::enchantment),

                            Codec.unboundedMap(
                                            Element.CODEC,
                                            Codec.INT
                                    ).fieldOf("values")
                                    .forGetter(ElementConditionData::values),
                            Codec.DOUBLE
                                    .fieldOf("chance")
                                    .forGetter(ElementConditionData::chance)

                    ).apply(instance, ElementConditionData::new)
            );
}