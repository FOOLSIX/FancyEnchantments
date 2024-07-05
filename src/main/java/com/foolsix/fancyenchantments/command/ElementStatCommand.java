package com.foolsix.fancyenchantments.command;

import com.foolsix.fancyenchantments.capability.ElementStatsCapabilityProvider;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static com.foolsix.fancyenchantments.enchantment.util.EnchUtils.Element.*;


public class ElementStatCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fancyenchantments")
                .then(Commands.literal("elementstats")
                        .executes((command) -> getElementStat(command.getSource()))));
    }

    private static int getElementStat(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player != null) {
            player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).ifPresent(elementStats -> {
                player.displayClientMessage(Component.literal("Aer: " + elementStats.getPoint(AER)).withStyle(ChatFormatting.YELLOW), false);
                player.displayClientMessage(Component.literal("Aqua: " + elementStats.getPoint(AQUA)).withStyle(ChatFormatting.AQUA), false);
                player.displayClientMessage(Component.literal("Ignis: " + elementStats.getPoint(IGNIS)).withStyle(ChatFormatting.GOLD), false);
                player.displayClientMessage(Component.literal("Terra: " + elementStats.getPoint(TERRA)).withStyle(ChatFormatting.GREEN), false);
                player.displayClientMessage(Component.literal("Twisted: " + elementStats.getPoint(TWISTED)).withStyle(ChatFormatting.DARK_PURPLE), false);
                player.displayClientMessage(Component.literal("Holy: " + elementStats.getPoint(HOLY)).withStyle(ChatFormatting.WHITE), false);
            });
        }
        return Command.SINGLE_SUCCESS;
    }
}
