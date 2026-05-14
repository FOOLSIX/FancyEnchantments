package com.foolsix.fancyenchantments.enchantment.handler;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.BEYOND_THE_FLASH;

@EventBusSubscriber(modid = MODID)
public final class BeyondTheFlashHandler {
    private static final double DURABILITY_CONSUMPTION_MULTIPLIER = 1.0D;

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player) || !(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack weapon = getFirstWeapon(player);
        if (weapon.isEmpty()) {
            return;
        }

        double bonusDamage = getTooltipDamage(weapon, player);
        int consumption = (int) Math.max(bonusDamage / 4.0D * DURABILITY_CONSUMPTION_MULTIPLIER, 2.0D);
        if (weapon.getMaxDamage() <= weapon.getDamageValue() + consumption) {
            return;
        }

        weapon.hurtAndBreak(consumption, serverLevel, player, item -> {
        });
        event.setAmount(event.getAmount() + (float) bonusDamage);
    }

    private static ItemStack getFirstWeapon(Player player) {
        for (int index = 0; index < 9; ++index) {
            ItemStack stack = player.getInventory().items.get(index);
            if (player.getWeaponItem() == stack) continue;

            Holder<Enchantment> enchantment = player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(BEYOND_THE_FLASH);
            int level = stack.getEnchantmentLevel(enchantment);

            if (level > 0) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static double getTooltipDamage(ItemStack stack, Player player) {
        double base = player.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
        DamageAccumulator damage = new DamageAccumulator();

        stack.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> {
            if (!attribute.equals(Attributes.ATTACK_DAMAGE)) {
                return;
            }

            switch (modifier.operation()) {
                case ADD_VALUE -> damage.add += modifier.amount();
                case ADD_MULTIPLIED_BASE -> damage.multiplyBase += modifier.amount();
                case ADD_MULTIPLIED_TOTAL -> damage.multiplyTotal += modifier.amount();
            }
        });

        double damageValue = base + damage.add + base * damage.multiplyBase;
        return damageValue * (1.0D + damage.multiplyTotal);
    }

    private static final class DamageAccumulator {
        private double add;
        private double multiplyBase;
        private double multiplyTotal;
    }
}
