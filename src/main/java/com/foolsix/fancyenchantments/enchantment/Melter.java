package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.effect.EffectReg;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.IgnisEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class Melter extends IgnisEnchantment {
    public static final String NAME = "melter";
    private static final ModConfig.MelterOptions CONFIG = FancyEnchantments.getConfig().melterOptions;
    private static final java.util.UUID ID = UUID.fromString("252d00fc-2176-a4d6-94bc-55a539753d45");

    public Melter() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public void attribute(ItemAttributeModifierEvent e) {
        if (e.getItemStack().getEnchantmentLevel(this) > 0 && e.getSlotType() == EquipmentSlot.MAINHAND) {
            e.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(ID, NAME, -CONFIG.damageReducer, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        if (pTarget instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(EffectReg.MELTING.get(), CONFIG.duration * pLevel, pLevel - 1));
        }
    }
}
