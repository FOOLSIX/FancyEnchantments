package com.foolsix.fancyenchantments.block.table;

import com.foolsix.fancyenchantments.block.ModBlockReg;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ElementalEnchantmentMenu extends AbstractContainerMenu {
    public static final int MAX_STORED_UPGRADE_BONUS = 30;

    public ElementalEnchantmentMenu(int containerId, Inventory inventory) {
        super(ModBlockReg.ELEMENTAL_ENCHANTMENT_MENU.get(), containerId);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
