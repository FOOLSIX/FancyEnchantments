package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.HEAVY_BLOW;

@EventBusSubscriber(modid = MODID)
public final class HeavyBlowHandler {
    private static final ResourceLocation ATTACK_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(MODID, "heavy_blow/attack_speed");

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        Entity source = event.getSource().getEntity();
        if (!(source instanceof LivingEntity attacker)) {
            return;
        }

        int level = EnchUtils.getEnchantmentLevel(HEAVY_BLOW, attacker);
        if (level <= 0) {
            return;
        }

        if (attacker.getRandom().nextDouble() >= level * Config.HEAVY_BLOW_BASE_RATE.get()) {
            return;
        }

        EnchUtils.generateSimpleParticleAroundEntity(event.getEntity(), ParticleTypes.CRIT);
        event.setAmount((float) (event.getAmount() * (1.0D + Config.HEAVY_BLOW_DAMAGE_MULTIPLIER.get() * level)));
    }

    @SubscribeEvent
    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        int level = EnchUtils.getEnchantmentLevel(HEAVY_BLOW, event.getItemStack(), null);
        if (level <= 0) {
            return;
        }

        event.addModifier(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(
                        ATTACK_SPEED_MODIFIER_ID,
                        -Config.HEAVY_BLOW_SPEED_REDUCER.get(),
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ),
                EquipmentSlotGroup.MAINHAND
        );
    }
}
