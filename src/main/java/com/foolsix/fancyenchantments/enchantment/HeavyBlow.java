package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.UUID;

import static com.foolsix.fancyenchantments.enchantment.util.EnchUtils.MOD_NAME_PREFIX;


public class HeavyBlow extends TerraEnchantment {
    public static final String NAME = "heavy_blow";
    public static final UUID HEAVY_BLOW_UUID = UUID.fromString("939ddda2-45df-4946-9ab6-82ffb11ede86");
    private static final ModConfig.HeavyBlowOptions CONFIG = FancyEnchantments.getConfig().heavyBlowOptions;

    public HeavyBlow() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 10 + pLevel * 5;
    }

    @Override
    public int getMaxCost(int pLevel) {
        return getMinCost(pLevel) + 50;
    }

    public void criticalHit(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof LivingEntity attacker) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, attacker);
            if (level > 0) {
                if (Math.random() < level * CONFIG.baseRate) {
                    Entity victim = e.getEntity();
                    ((ServerLevel) attacker.level).sendParticles(ParticleTypes.CRIT, victim.getX(), victim.getY(), victim.getZ(), 30, 0.2D, 0.7D, 0.1D, 0);
                    e.setAmount((float) (e.getAmount() * (1 + CONFIG.damageMultiplier * level)));
                }
            }
        }
    }

    public void attackSpeedReduce(ItemAttributeModifierEvent e) {
        if (e.getItemStack().getEnchantmentLevel(this) > 0 && e.getSlotType() == EquipmentSlot.MAINHAND) {
            e.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(HEAVY_BLOW_UUID, MOD_NAME_PREFIX + NAME, -CONFIG.speedReducer, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }
}
