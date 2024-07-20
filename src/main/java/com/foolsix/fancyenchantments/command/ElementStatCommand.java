package com.foolsix.fancyenchantments.command;

import com.foolsix.fancyenchantments.capability.ElementStatsCapabilityProvider;
import com.foolsix.fancyenchantments.enchantment.util.EnchUtils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;


public class ElementStatCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fancyenchantments")
                .then(Commands.literal("elementstats")
                        .executes((command) -> getElementStat(command.getSource()))));
    }

    private static int getElementStat(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player != null) {
            player.getCapability(ElementStatsCapabilityProvider.PLAYER_ELEMENT_STATS).ifPresent(stats -> {
                for (int i = 0; i < EnchUtils.ELEMENT_COUNT; ++i) {
                    EnchUtils.Element element = EnchUtils.Element.values()[i];
                    String name = element.toString().charAt(0) + element.toString().substring(1).toLowerCase();
                    player.displayClientMessage(Component.literal(EnchUtils.Element.getChatFormatting(element) + name + ": " + stats.getPoint(element)), false);
                }
            });
        }
        return Command.SINGLE_SUCCESS;
    }
}
