package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.PYROMANIAC;

@EventBusSubscriber(modid = MODID)
public final class PyromaniacHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)
                || !event.getSource().is(DamageTypes.EXPLOSION)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(PYROMANIAC, player);
        if (level <= 0) {
            return;
        }

        float damage = event.getAmount();
        float healValue = Config.EXPLOSION_HEAL_MULTIPLIER.get().floatValue() * damage * level;
        float damageBonus = Math.min(player.getMaxHealth() - player.getHealth(), healValue);

        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        chest.hurtAndBreak(
                Config.EXPLOSION_ARMOR_BASE_DAMAGE.get() + (int) (Config.EXPLOSION_DAMAGE_MULTIPLIER.get() * damageBonus),
                player,
                EquipmentSlot.CHEST
        );

        player.heal(healValue);
        event.setAmount(0);
    }
}
