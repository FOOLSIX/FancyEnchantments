package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AerEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.UUID;

public class WindBlade extends AerEnchantment {
    public static final String NAME = "wind_blade";
    public static final UUID WIND_BLADE_UUID = UUID.fromString("5daf4e17-1012-411b-bb59-7f50d6d26c66");
    private static final ModConfig.WindBladeOptions CONFIG = FancyEnchantments.getConfig().windBladeOptions;

    public WindBlade() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 10 + pLevel * 5;
    }

    public void damageBoost(LivingHurtEvent e) {
        //Not applicable on monsters
        if (e.getSource().getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0) {
                AttributeInstance movementAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
                double attributeValue = 0.1;
                if (movementAttr != null)
                    attributeValue = Math.max(attributeValue, movementAttr.getValue());
                double extraSpeed = Math.max(0.0, (attributeValue - 0.1) / 0.1);
                e.setAmount((float) (e.getAmount() * (1 + extraSpeed * CONFIG.baseDamageMultiplier * level)));
            }
        }
    }
}
