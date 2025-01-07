package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.damage.FEDamageSource;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

import static com.foolsix.fancyenchantments.enchantment.util.EnchantmentReg.DUELLISTS_PREROGATIVE;


public class FeintAttack extends FEBaseEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.FeintAttackOptions CONFIG = FancyEnchantments.getConfig().feintAttackOptions;
    public static final String NAME = "feint_attack";

    public FeintAttack() {
        super(CONFIG, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }


    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return super.checkCompatibility(pOther) && pOther != Enchantments.SWEEPING_EDGE && pOther != DUELLISTS_PREROGATIVE.get();
    }

    @Override
    public int getLivingHurtPriority() {
        return MULTIPLY;
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        attack(e);
    }

    public void attack(LivingHurtEvent e) {
        if (e.getSource().getEntity() instanceof Player player && e.getEntity().level() instanceof ServerLevel world) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, player);
            if (level > 0 && player.getMainHandItem().canPerformAction(ToolActions.SWORD_SWEEP)) {
                if (!e.getSource().type().equals(FEDamageSource.GENERAL_ENCHANTMENT_DAMAGE) && player.getAttackStrengthScale(0.5F) > 0.9) {
                    List<Entity> entities = world.getEntities(e.getEntity(), e.getEntity().getBoundingBox().inflate(3), EnchUtils::isHostileToPlayer);
                    DamageSource damageSource = FEDamageSource.enchantmentDamage(player);
                    for (Entity entity : entities) {
                        if (entity instanceof LivingEntity living) {
                            EnchUtils.generateSimpleParticleAroundEntity(living, ParticleTypes.SOUL);
                            living.hurt(damageSource, e.getAmount() * (1 + CONFIG.damageMultiplier * level));
                        }
                    }
                    e.setAmount(e.getAmount() * (1 - CONFIG.damageReducer));
                }
            }
        }
    }
}
