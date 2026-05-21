package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;

public record FEBaseEnchantment(
        ResourceKey<Enchantment> key,
        @Nullable Element element,
        int maxLevel,
        boolean treasureOnly,
        boolean tradeable,
        boolean allowedOnBooks,
        boolean discoverable,
        boolean inElementalTable
) {
    public boolean is(ResourceKey<Enchantment> other) {
        return this.key.equals(other);
    }

    public boolean is(Holder<Enchantment> enchantment) {
        return EnchUtils.matchesKey(enchantment, this.key);
    }

}
