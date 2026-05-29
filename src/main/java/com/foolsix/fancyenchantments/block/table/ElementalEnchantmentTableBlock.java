package com.foolsix.fancyenchantments.block.table;

import com.foolsix.fancyenchantments.block.BlockReg;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ElementalEnchantmentTableBlock extends BaseEntityBlock {
    public static final MapCodec<ElementalEnchantmentTableBlock> CODEC = simpleCodec(ignored -> new ElementalEnchantmentTableBlock());
    private static final Component TITLE = Component.translatable("block.fancyenchantments.elemental_enchanting_table");
    protected static final VoxelShape SHAPE = box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);

    public ElementalEnchantmentTableBlock() {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.ENCHANTING_TABLE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        for (BlockPos offset : EnchantingTableBlock.BOOKSHELF_OFFSETS) {
            if (random.nextInt(16) != 0 || !EnchantingTableBlock.isValidBookShelf(level, pos, offset)) {
                continue;
            }

            level.addParticle(
                    ParticleTypes.ENCHANT,
                    pos.getX() + 0.5D,
                    pos.getY() + 2.0D,
                    pos.getZ() + 0.5D,
                    offset.getX() + random.nextFloat() - 0.5D,
                    offset.getY() - random.nextFloat() - 1.0F,
                    offset.getZ() + random.nextFloat() - 0.5D
            );
        }
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ElementalEnchantmentTableBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        player.openMenu(state.getMenuProvider(level, pos), pos);
        return InteractionResult.CONSUME;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide
                ? createTickerHelper(blockEntityType, BlockReg.ELEMENTAL_ENCHANTMENT_TABLE_BLOCK_ENTITY.get(),
                ElementalEnchantmentTableBlockEntity::bookAnimationTick)
                : null;
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        Component title = blockEntity instanceof ElementalEnchantmentTableBlockEntity tableBlockEntity
                ? tableBlockEntity.getDisplayName()
                : TITLE;
        return new SimpleMenuProvider((windowId, inventory, player) ->
                new ElementalEnchantmentMenu(windowId, inventory, net.minecraft.world.inventory.ContainerLevelAccess.create(level, pos)), title);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!stack.has(DataComponents.CUSTOM_NAME)) {
            return;
        }

        if (level.getBlockEntity(pos) instanceof ElementalEnchantmentTableBlockEntity tableBlockEntity) {
            tableBlockEntity.setCustomName(stack.getHoverName());
        }
    }
}
