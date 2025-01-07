package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.TerraEnchantment;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class MountainSupremeProtection extends TerraEnchantment {
    private static final ModConfig.MountainSupremeProtectionOptions CONFIG = FancyEnchantments.getConfig().mountainSupremeProtectionOptions;

    public MountainSupremeProtection() {
        super(CONFIG, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public int getDamageProtection(int pLevel, DamageSource pSource) {
        if (pSource.isBypassInvul()) {
            return 0;
        }
        return (int) (pLevel * CONFIG.EPFMultiplier);
    }

    @Override
    public boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && !(pOther instanceof ProtectionEnchantment) && pOther.isCompatibleWith(Enchantments.ALL_DAMAGE_PROTECTION);
    }

    public void reduceDamage(LivingDamageEvent e) {
        LivingEntity living = e.getEntity();
        int levelSum = 0;
        for (ItemStack armor : living.getArmorSlots()) {
            levelSum += armor.getEnchantmentLevel(this);
        }
        e.setAmount((float) Math.max(0, e.getAmount() - levelSum * CONFIG.reducer));
    }
}
