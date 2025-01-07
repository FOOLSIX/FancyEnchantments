package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.AquaEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.ItemAttributeModifierEventHandler;
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

import static com.foolsix.fancyenchantments.effect.EffectReg.ATTACK_SPEED_BOOST;

public class StackingWaves extends AquaEnchantment implements ItemAttributeModifierEventHandler {
    private static final ModConfig.StackingWavesOptions CONFIG = FancyEnchantments.getConfig().stackingWavesOptions;
    private static final UUID ID = UUID.fromString("4c7fc7c6-3b9d-a2e8-1d4a-d6dbc002b55d");
    public static final String NAME = "stacking_waves";

    public StackingWaves() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        MobEffectInstance instance = pAttacker.getEffect(ATTACK_SPEED_BOOST.get());
        if (instance == null) {
            pAttacker.addEffect(new MobEffectInstance(ATTACK_SPEED_BOOST.get(), CONFIG.duration * 20));
        } else {
            pAttacker.addEffect(new MobEffectInstance(ATTACK_SPEED_BOOST.get(), CONFIG.duration * 20, Math.min(pLevel - 1, instance.getAmplifier() + 1)));
        }
    }

    @Override
    public void handleItemAttributeModifier(ItemAttributeModifierEvent e) {
        attribute(e);
    }

    public void attribute(ItemAttributeModifierEvent e) {
        int level = e.getItemStack().getEnchantmentLevel(this);
        if (level > 0 && e.getSlotType() == EquipmentSlot.MAINHAND) {
            e.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(ID, NAME, Math.max(-0.9, -CONFIG.attackSpeedReducer * level), AttributeModifier.Operation.MULTIPLY_BASE));
       }
    }
}
