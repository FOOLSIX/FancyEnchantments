package com.foolsix.fancyenchantments.block.table;

import com.foolsix.fancyenchantments.block.ModBlockReg;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ElementalEnchantmentTableBlockEntity extends BlockEntity implements Nameable {
    private static final RandomSource RANDOM = RandomSource.create();
    public static final int MAX_STORED_UPGRADE_BONUS = 30;

    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    public float rot;
    public float oRot;
    public float tRot;
    private int storedUpgradeBonus;
    @Nullable
    private Component name;

    public ElementalEnchantmentTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockReg.ELEMENTAL_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.hasCustomName()) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
        tag.putInt("StoredUpgradeBonus", this.storedUpgradeBonus);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
        this.storedUpgradeBonus = Mth.clamp(tag.getInt("StoredUpgradeBonus"), 0, MAX_STORED_UPGRADE_BONUS);
    }

    public static void bookAnimationTick(Level level, BlockPos pos, BlockState state, ElementalEnchantmentTableBlockEntity blockEntity) {
        blockEntity.oOpen = blockEntity.open;
        blockEntity.oRot = blockEntity.rot;
        Player player = level.getNearestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 3.0D, false);
        if (player != null) {
            double offsetX = player.getX() - (pos.getX() + 0.5D);
            double offsetZ = player.getZ() - (pos.getZ() + 0.5D);
            blockEntity.tRot = (float) Mth.atan2(offsetZ, offsetX);
            blockEntity.open += 0.1F;
            if (blockEntity.open < 0.5F || RANDOM.nextInt(40) == 0) {
                float previousFlipTarget = blockEntity.flipT;

                do {
                    blockEntity.flipT += RANDOM.nextInt(4) - RANDOM.nextInt(4);
                } while (previousFlipTarget == blockEntity.flipT);
            }
        } else {
            blockEntity.tRot += 0.02F;
            blockEntity.open -= 0.1F;
        }

        while (blockEntity.rot >= (float) Math.PI) {
            blockEntity.rot -= ((float) Math.PI * 2F);
        }

        while (blockEntity.rot < -(float) Math.PI) {
            blockEntity.rot += ((float) Math.PI * 2F);
        }

        while (blockEntity.tRot >= (float) Math.PI) {
            blockEntity.tRot -= ((float) Math.PI * 2F);
        }

        while (blockEntity.tRot < -(float) Math.PI) {
            blockEntity.tRot += ((float) Math.PI * 2F);
        }

        float rotDelta = blockEntity.tRot - blockEntity.rot;
        while (rotDelta >= (float) Math.PI) {
            rotDelta -= ((float) Math.PI * 2F);
        }

        while (rotDelta < -(float) Math.PI) {
            rotDelta += ((float) Math.PI * 2F);
        }

        blockEntity.rot += rotDelta * 0.4F;
        blockEntity.open = Mth.clamp(blockEntity.open, 0.0F, 1.0F);
        ++blockEntity.time;
        blockEntity.oFlip = blockEntity.flip;
        float flipDelta = (blockEntity.flipT - blockEntity.flip) * 0.4F;
        flipDelta = Mth.clamp(flipDelta, -0.2F, 0.2F);
        blockEntity.flipA += (flipDelta - blockEntity.flipA) * 0.9F;
        blockEntity.flip += blockEntity.flipA;
    }

    @Override
    public Component getName() {
        return this.name != null ? this.name : Component.translatable("block.fancyenchantments.elemental_enchanting_table");
    }

    public void setCustomName(Component name) {
        this.name = name;
        this.setChanged();
    }

    public int getStoredUpgradeBonus() {
        return this.storedUpgradeBonus;
    }

    public void setStoredUpgradeBonus(int storedUpgradeBonus) {
        int clamped = Mth.clamp(storedUpgradeBonus, 0, MAX_STORED_UPGRADE_BONUS);
        if (this.storedUpgradeBonus == clamped) {
            return;
        }
        this.storedUpgradeBonus = clamped;
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    public void clearStoredUpgradeBonus() {
        this.setStoredUpgradeBonus(0);
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }
}
