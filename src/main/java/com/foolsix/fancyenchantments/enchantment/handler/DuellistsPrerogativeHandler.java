package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DUELLISTS_PREROGATIVE;

@EventBusSubscriber(modid = MODID)
public final class DuellistsPrerogativeHandler {
    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(DUELLISTS_PREROGATIVE, player);
        if (level <= 0) {
            return;
        }

        LivingEntity victim = event.getEntity();
        List<LivingEntity> nearVictim = victim.level().getEntitiesOfClass(LivingEntity.class, victim.getBoundingBox().inflate(2.0D));
        List<LivingEntity> nearPlayer = player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(2.0D));
        if (nearVictim.size() <= 2 && nearPlayer.size() <= 2) {
            event.setAmount(event.getAmount() * (1.0F + level * (float) Config.DUELLISTS_PREROGATIVE_DAMAGE_MULTIPLIER.get().doubleValue()));
        }
    }
}
