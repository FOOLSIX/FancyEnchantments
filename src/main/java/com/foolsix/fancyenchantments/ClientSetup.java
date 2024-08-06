package com.foolsix.fancyenchantments;

import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

public class ClientSetup {
    @OnlyIn(Dist.CLIENT)
    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(Items.ENCHANTED_BOOK, new ResourceLocation(MODID + ":enchanted_book"), (stack, world, entity, i) -> {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
                if (enchantments.isEmpty()) return 0.0f;
                Enchantment enchantment = enchantments.entrySet().iterator().next().getKey();
                EnchUtils.Element element = EnchUtils.Element.getElement(enchantment);
                if (element != null) {
                    return 1.0f + element.ordinal() * 0.1f;
                } else if (enchantment instanceof FEBaseEnchantment) {
                    return 2.0f;
                }
                return 0.0f;
            });
        });
    }
}
