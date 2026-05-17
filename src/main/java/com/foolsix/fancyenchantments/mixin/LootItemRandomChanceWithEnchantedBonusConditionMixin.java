package com.foolsix.fancyenchantments.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.ADVANCED_LOOTING;
import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.GREED_SUPREME_LOOTING;

@Mixin(LootItemRandomChanceWithEnchantedBonusCondition.class)
abstract class LootItemRandomChanceWithEnchantedBonusConditionMixin {
    @Shadow
    public abstract Holder<Enchantment> enchantment();

    @Shadow
    public abstract LevelBasedValue enchantedChance();

    @Shadow
    @Final
    private float unenchantedChance;

    @Inject(method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", at = @At("HEAD"), cancellable = true)
    private void fancyenchantments$extraLooting(LootContext context, CallbackInfoReturnable<Boolean> cir) {
        if (!this.enchantment().is(Enchantments.LOOTING)) {
            //only
            return;
        }

        Entity attacker = context.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
        if (!(attacker instanceof LivingEntity livingEntity)) {
            return;
        }

        HolderLookup.RegistryLookup<Enchantment> enchantments = livingEntity.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        int advancedLevel = EnchantmentHelper.getEnchantmentLevel(enchantments.getOrThrow(ADVANCED_LOOTING), livingEntity);
        int greedyLevel = EnchantmentHelper.getEnchantmentLevel(enchantments.getOrThrow(GREED_SUPREME_LOOTING), livingEntity);
        int sumLevel = advancedLevel * 2 + greedyLevel * 3;
        if (sumLevel == 0) {
            return;
        }

        float chance = this.enchantedChance().calculate(sumLevel);
        cir.setReturnValue(context.getRandom().nextFloat() < chance);
    }
}
