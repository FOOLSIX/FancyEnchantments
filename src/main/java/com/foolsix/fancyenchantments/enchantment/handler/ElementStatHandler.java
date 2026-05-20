package com.foolsix.fancyenchantments.enchantment.handler;

import com.foolsix.fancyenchantments.Config;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.foolsix.fancyenchantments.FancyEnchantments.MODID;

@EventBusSubscriber(modid = MODID)
public final class ElementStatHandler {
    private static final Holder<MobEffect>[] DEBUFFS = new Holder[]{
            MobEffects.WEAKNESS,
            MobEffects.DARKNESS,
            MobEffects.MOVEMENT_SLOWDOWN
    };

    private ElementStatHandler() {
    }

    @SubscribeEvent
    public static void onPlayerTickPost(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide() || player.tickCount % 80 != 0) {
            return;
        }

        int[] elementStats = EnchUtils.getElementStatsFromEquipment(player);
        applyBuff(player, elementStats, Element.AER, 0, Config.ELEMENT_STAT_AER_CONDITION.get(), Config.ELEMENT_STAT_AER_MAX_EFFECT_LEVEL.get());
        applyBuff(player, elementStats, Element.AQUA, 1, Config.ELEMENT_STAT_AQUA_CONDITION.get(), Config.ELEMENT_STAT_AQUA_MAX_EFFECT_LEVEL.get());
        applyBuff(player, elementStats, Element.IGNIS, 2, Config.ELEMENT_STAT_IGNIS_CONDITION.get(), Config.ELEMENT_STAT_IGNIS_MAX_EFFECT_LEVEL.get());
        applyBuff(player, elementStats, Element.TERRA, 3, Config.ELEMENT_STAT_TERRA_CONDITION.get(), Config.ELEMENT_STAT_TERRA_MAX_EFFECT_LEVEL.get());

        if (elementStats[Element.IGNIS.ordinal()] >= Config.ELEMENT_STAT_IGNIS_FIRE_RESISTANCE_CONDITION.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0, false, false));
        }

        int twistedDifference = elementStats[Element.TWISTED.ordinal()] - elementStats[Element.HOLY.ordinal()];
        if (twistedDifference < Config.ELEMENT_STAT_TWISTED_DEBUFF_CONDITION.get()) {
            return;
        }
        if (player.getRandom().nextDouble() >= Config.ELEMENT_STAT_TWISTED_DEBUFF_PROBABILITY_PER_SECOND.get()) {
            return;
        }

        Holder<MobEffect> debuff = DEBUFFS[player.getRandom().nextInt(DEBUFFS.length)];
        player.addEffect(new MobEffectInstance(
                debuff,
                Config.ELEMENT_STAT_TWISTED_DEBUFF_DURATION_SECONDS.get() * 20,
                0,
                false,
                false
        ));
    }

    private static void applyBuff(Player player, int[] elementStats, Element element, int buffIndex, int condition, int maxEffectLevel) {
        if (condition <= 0) {
            return;
        }

        Holder<MobEffect> effect = getBuff(player, buffIndex);
        if (effect == null) {
            return;
        }

        int points = elementStats[element.ordinal()];
        if (points < condition) {
            return;
        }

        int amplifier = Math.min(points / condition, maxEffectLevel) - 1;
        if (amplifier < 0) {
            return;
        }

        player.addEffect(new MobEffectInstance(effect, 200, amplifier, false, false));
    }

    private static Holder<MobEffect> getBuff(Player player, int buffIndex) {
        ResourceLocation effectId = ResourceLocation.tryParse(buffId(buffIndex));
        if (effectId == null) {
            return null;
        }

        ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT, effectId);

        return player.registryAccess()
                .lookupOrThrow(Registries.MOB_EFFECT)
                .get(key)
                .orElse(null);
    }

    private static String buffId(int buffIndex) {
        return switch (buffIndex) {
            case 0 -> Config.ELEMENT_STAT_AER_BUFF.get();
            case 1 -> Config.ELEMENT_STAT_AQUA_BUFF.get();
            case 2 -> Config.ELEMENT_STAT_IGNIS_BUFF.get();
            case 3 -> Config.ELEMENT_STAT_TERRA_BUFF.get();
            default -> throw new IllegalArgumentException("Unknown element stat buff index: " + buffIndex);
        };
    }
}
