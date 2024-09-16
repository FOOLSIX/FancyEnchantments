package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.UUID;


public class OceanCurrent extends AquaEnchantment {
    public static final String NAME = "ocean_current";
    public static final UUID OCEAN_CURRENT_UUID = UUID.fromString("ee024ab3-16b9-4b03-a31e-946d1811d0ec");
    private static final ModConfig.OceanCurrentOptions CONFIG = FancyEnchantments.getConfig().oceanCurrentOptions;

    public OceanCurrent() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 15 + pLevel * 5;
    }

    public void attackSpeedBoost(LivingEvent.LivingTickEvent e) {
        if (e.getEntity() != null) {
            LivingEntity living = e.getEntity();
            int level = EnchantmentHelper.getEnchantmentLevel(this, living);
            AttributeInstance attackSpeedAttr = living.getAttribute(Attributes.ATTACK_SPEED);
            if (attackSpeedAttr != null) {
                attackSpeedAttr.removeModifier(OCEAN_CURRENT_UUID);
            }
            if (CONFIG.ineffectiveWhenOnFire && living.isOnFire()) return;
            if (level > 0) {
                float addon = CONFIG.speedMultiplier * level;
                if (living.isInWater()) {
                    addon *= CONFIG.extraSpeedMultiplier;
                }
                if (attackSpeedAttr != null) {
                    attackSpeedAttr.addTransientModifier(new AttributeModifier(OCEAN_CURRENT_UUID, NAME, addon, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }
}
