package com.foolsix.fancyenchantments.tag;

import com.foolsix.fancyenchantments.FancyEnchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FETags {
    public static class Items {

        public static final TagKey<Item> UPGRADE_MATERIALS =
                TagKey.create(
                        Registries.ITEM,
                        ResourceLocation.fromNamespaceAndPath(
                                FancyEnchantments.MODID,
                                "upgrade_materials"
                        )
                );
    }
}
