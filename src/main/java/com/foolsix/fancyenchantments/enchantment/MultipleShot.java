package com.foolsix.fancyenchantments.enchantment;

import com.foolsix.fancyenchantments.FancyEnchantments;
import com.foolsix.fancyenchantments.enchantment.EssentiaEnch.FEBaseEnchantment;
import com.foolsix.fancyenchantments.enchantment.handler.LivingHurtEventHandler;
import com.foolsix.fancyenchantments.util.ModConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MultipleShot extends FEBaseEnchantment implements LivingHurtEventHandler {
    private static final ModConfig.MultipleShotOptions CONFIG = FancyEnchantments.getConfig().multipleShotOptions;
    private final String TAG_NAME = "fe_multiple";

    public MultipleShot() {
        super(CONFIG, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    public void multiShot(EntityJoinLevelEvent e) {
        if (e.getEntity() instanceof AbstractArrow arrow &&
                !arrow.getPersistentData().contains(TAG_NAME) &&
                !arrow.getPersistentData().contains("apoth.generated") && //a rough way to make apotheosis compatible...
                arrow.getOwner() instanceof LivingEntity shooter &&
                shooter.level() instanceof ServerLevel world) {
            int level = EnchantmentHelper.getEnchantmentLevel(this, shooter);
            ArrowItem arrowitem = (ArrowItem) Items.ARROW;
            for (int i = 1; i <= level; ++i) {
                AbstractArrow arrow1 = arrowitem.createArrow(world, shooter.getUseItem(), shooter);
                arrow1.setPos(shooter.getX(), shooter.getY() + shooter.getEyeHeight(), shooter.getZ());
                arrow1.setBaseDamage(arrow.getBaseDamage());
                arrow1.setKnockback(arrow.getKnockback());
                arrow1.setSecondsOnFire(arrow.getRemainingFireTicks() / 20);
                arrow1.setCritArrow(arrow.isCritArrow());
                arrow1.setPierceLevel(arrow.getPierceLevel());
                arrow1.addAdditionalSaveData(arrow.getPersistentData());
                float xOffset = (float) ((Math.random() - 0.5) * 10);
                float yOffset = (float) ((Math.random() - 0.5) * 10);
                arrow1.shootFromRotation(shooter, shooter.getXRot() - xOffset, shooter.getYRot() - yOffset, 0.0F, 3, 1);
                arrow1.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                arrow1.getPersistentData().putBoolean(TAG_NAME, true);
                world.addFreshEntity(arrow1);
            }
        }
    }

    @Override
    public void handleLivingHurtEvent(LivingHurtEvent e) {
        if (e.getSource().getDirectEntity() instanceof AbstractArrow arrow && arrow.getPersistentData().contains(TAG_NAME)) {
            e.getEntity().invulnerableTime = 0;
        }
    }
}
