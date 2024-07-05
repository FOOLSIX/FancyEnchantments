package com.foolsix.fancyenchantments.enchantment.EssentiaEnch;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AquaEnchantment extends FEBaseEnchantment {
    protected AquaEnchantment(ModConfig.BaseOptions options, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(options, pCategory, pApplicableSlots);
    }

    public Component getFullname(int level) {
        MutableComponent name = ((MutableComponent) super.getFullname(level)).withStyle(ChatFormatting.AQUA);
        if (isCurse()) {
            name = EnchUtils.CURSE_PREFIX.copy().append(name);
        }
        return name;
    }

    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && (!FancyEnchantments.getConfig().enableIncompatibility || !(pOther instanceof IgnisEnchantment));
    }
}
