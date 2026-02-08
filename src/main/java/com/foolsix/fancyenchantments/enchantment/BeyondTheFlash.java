package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BeyondTheFlash extends FEBaseEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.BeyondTheFleshOptions CONFIG = FancyEnchantments.getConfig().beyondTheFleshOptions;

    public BeyondTheFlash() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{});
    }

    @Override
    public int getMinCost(int pLevel) {
        return 30;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof Player player) {
            if (player.level().isClientSide) return;
            ItemStack weapon = null;

            for (int i = 0; i < 9; i++) {
                ItemStack stack = player.getInventory().items.get(i);
                if (!stack.isEmpty() && stack.getEnchantmentLevel(this) > 0) {
                    weapon = stack;
                    break;
                }
            }
            if (weapon != null) {
                double damage = getItemTooltipDamage(weapon, player);
                int consumption = (int) Math.max(damage / 4 * CONFIG.durabilityConsumptionMultiplier, 2.0);
                if (weapon.getMaxDamage() > weapon.getDamageValue() + consumption) {
                    weapon.hurtAndBreak(consumption, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
                    e.setAmount(e.getAmount() + (float) damage);
                }
            }
        }
    }

    private double getItemTooltipDamage(ItemStack stack, Player player) {
        double base = player.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);

        double add = 0;
        double mulBase = 0;
        double mulTotal = 0;

        var mods = stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(Attributes.ATTACK_DAMAGE);

        for (AttributeModifier mod : mods) {
            switch (mod.getOperation()) {
                case ADDITION -> add += mod.getAmount();
                case MULTIPLY_BASE -> mulBase += mod.getAmount();
                case MULTIPLY_TOTAL -> mulTotal += mod.getAmount();
            }
        }

        double value = base;
        value += add;
        value += base * mulBase;
        value *= 1 + mulTotal;

        return value;
    }
}
