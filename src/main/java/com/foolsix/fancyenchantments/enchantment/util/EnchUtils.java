package com.foolsix.fancyenchantments.enchantment.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class EnchUtils {
    public static final MutableComponent CURSE_PREFIX = Component.translatable("Curse:").withStyle(ChatFormatting.RED);

    public static List<BlockPos> getRandomValidPos(Entity entity, Level world, int tryTimes) {
        BlockPos originPos = entity.blockPosition();
        RandomSource random = RandomSource.create();
        List<BlockPos> blockPosList = new ArrayList<>();
        while (tryTimes-- > 0) {
            BlockPos pos = new BlockPos(originPos.getX() + random.nextInt(3) - 1, originPos.getY() + random.nextInt(3) - 1, originPos.getZ() + random.nextInt(2));
            if (world.isEmptyBlock(pos) && !world.isEmptyBlock(pos.below())) {
                blockPosList.add(pos);
            }
        }
        return blockPosList;
    }
}
