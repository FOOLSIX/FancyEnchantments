package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.MOUNTAIN_SUPREME_PROTECTION;

@EventBusSubscriber(modid = MODID)
public final class MountainSupremeProtectionHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }

        int levelSum = 0;
        for (ItemStack armor : event.getEntity().getArmorSlots()) {
            levelSum += EnchUtils.getEnchantmentLevel(MOUNTAIN_SUPREME_PROTECTION, armor, event.getEntity().registryAccess());
        }
        if (levelSum <= 0) {
            return;
        }

        event.setAmount((float) Math.max(0.0D, event.getAmount() - levelSum * Config.MOUNTAIN_SUPREME_PROTECTION_REDUCER.get()));
    }
}
