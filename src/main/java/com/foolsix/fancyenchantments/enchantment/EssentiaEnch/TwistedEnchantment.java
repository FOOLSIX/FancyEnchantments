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

public class TwistedEnchantment extends FEBaseEnchantment {
    protected TwistedEnchantment(ModConfig.BaseOptions options, EnchantmentCategory category, EquipmentSlot[] equipmentSlots) {
        super(options, category, equipmentSlots);
    }

    public Component getFullname(int level) {
        MutableComponent name = ((MutableComponent) super.getFullname(level)).withStyle(ChatFormatting.DARK_PURPLE);
        if (isCurse()) {
            name = name.append(EnchUtils.CURSE_SUFFIX);
        }
        return name;
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && (!FancyEnchantments.getConfig().enableIncompatibility || !(pOther instanceof HolyEnchantment));
    }
}
