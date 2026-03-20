package com.foolsix.fancyenchantments.block;

import com.foolsix.fancyenchantments.menu.ElementalEnchantmentMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class ElementalEnchantmentTableBlock extends Block {
    private static final Component TITLE = Component.translatable("block.fancyenchantments.elemental_enchanting_table");

    public ElementalEnchantmentTableBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.ENCHANTING_TABLE).strength(5.0F));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, getMenuProvider(level, pos), pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    private MenuProvider getMenuProvider(Level level, BlockPos pos) {
        return new SimpleMenuProvider((windowId, inventory, player) ->
                new ElementalEnchantmentMenu(windowId, inventory,
                        net.minecraft.world.inventory.ContainerLevelAccess.create(level, pos)), TITLE);
    }
}
