package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LavaBurst extends FEBaseEnchantment {
    public static final String NAME = "lava_burst";
    private static final ModConfig.LavaBurstOptions CONFIG = FancyEnchantments.getConfig().lavaBurstOptions;

    public LavaBurst() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public Component getFullname(int level) {
        Component name = super.getFullname(level);
        if (name instanceof MutableComponent mutableName) {
            mutableName.setStyle(Style.EMPTY.withColor(EnchUtils.gradualColor(16755200, 5635925, 120)));
        }
        return name;
    }

    public void hitExplosion(LivingHurtEvent e) {
        //todo
    }
}
