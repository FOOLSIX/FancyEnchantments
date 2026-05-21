package com.foolsix.fancyenchantments.mixin;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEEnchantments;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
abstract class EnchantmentCompatibilityMixin {
    @Inject(method = "areCompatible", at = @At("HEAD"), cancellable = true)
    private static void fancyenchantments$applyElementalCompatibilityToggle(
            Holder<Enchantment> first,
            Holder<Enchantment> second,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (!Config.ENABLE_INCOMPATIBILITY.get()) {
            return;
        }

        if (!FEEnchantments.isFancyEnchantment(first) || !FEEnchantments.isFancyEnchantment(second)) {
            return;
        }

        if (EnchUtils.hasElementalConflict(first, second)) {
            cir.setReturnValue(false);
        }
    }
}
