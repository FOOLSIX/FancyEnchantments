package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.HolyEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;
import java.util.Objects;

public class PurificationSlash extends HolyEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.PurificationSlashOptions CONFIG = FancyEnchantments.getConfig().purificationSlashOptions;
    public PurificationSlash() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getLivingHurtPriority() {
        return MULTIPLY;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        Entity attacker = e.getSource().getEntity();
        if (!(attacker instanceof Player p) || EnchantmentHelper.getEnchantmentLevel(this, p) <= 0) {
            return;
        }
        LivingEntity victim = e.getEntity();
        if (victim instanceof Player || (victim instanceof OwnableEntity ownable && Objects.equals(ownable.getOwnerUUID(), attacker.getUUID()))) {
            victim.clearFire();
            List<MobEffect> effectInstances = victim.getActiveEffects().stream().map(MobEffectInstance::getEffect).filter(effect -> effect.getCategory() == MobEffectCategory.HARMFUL).toList();
            effectInstances.forEach(victim::removeEffect);
            EnchUtils.generateSimpleParticleAroundEntity(victim, ParticleTypes.HAPPY_VILLAGER);
        }
    }
}
