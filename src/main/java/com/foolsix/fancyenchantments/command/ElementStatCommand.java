package com.foolsix.fancyenchantments.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.AER;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.AQUA;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.HOLY;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.IGNIS;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.TERRA;
import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.TWISTED;
import static com.foolsix.fancyenchantments.enchantment.util.EnchUtils.getElementStatsFromEquipment;

public final class ElementStatCommand {
    private ElementStatCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fancyenchantments")
                .then(Commands.literal("elementstats")
                        .executes(command -> getElementStat(command.getSource()))));
    }

    private static int getElementStat(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            return Command.SINGLE_SUCCESS;
        }

        int[] stats = getElementStatsFromEquipment(player);
        player.displayClientMessage(Component.literal("Aer: " + stats[AER.ordinal()]).withStyle(ChatFormatting.YELLOW), false);
        player.displayClientMessage(Component.literal("Aqua: " + stats[AQUA.ordinal()]).withStyle(ChatFormatting.AQUA), false);
        player.displayClientMessage(Component.literal("Ignis: " + stats[IGNIS.ordinal()]).withStyle(ChatFormatting.GOLD), false);
        player.displayClientMessage(Component.literal("Terra: " + stats[TERRA.ordinal()]).withStyle(ChatFormatting.GREEN), false);
        player.displayClientMessage(Component.literal("Twisted: " + stats[TWISTED.ordinal()]).withStyle(ChatFormatting.DARK_PURPLE), false);
        player.displayClientMessage(Component.literal("Holy: " + stats[HOLY.ordinal()]).withStyle(ChatFormatting.WHITE), false);
        return Command.SINGLE_SUCCESS;
    }
}
