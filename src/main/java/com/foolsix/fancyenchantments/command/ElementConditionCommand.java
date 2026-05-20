package com.foolsix.fancyenchantments.command;

import com.foolsix.fancyenchantments.enchantment.util.ElementConditionManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static com.foolsix.fancyenchantments.enchantment.EssentiaEnch.Element.*;

public final class ElementConditionCommand {

    private ElementConditionCommand() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                Commands.literal("fancyenchantments")
                        .then(
                                Commands.literal("getcondition")
                                        .then(
                                                Commands.argument("enchantment", StringArgumentType.greedyString()
                                                        )
                                                        .suggests((ctx, builder) -> {

                                                            ElementConditionManager
                                                                    .getConditionalEnchantments()
                                                                    .forEach(id ->
                                                                            builder.suggest(id.toString())
                                                                    );

                                                            return builder.buildFuture();
                                                        })
                                                        .executes(ElementConditionCommand::run)
                                        )
                        )
        );
    }

    private static int run(CommandContext<CommandSourceStack> ctx) {

        String raw = StringArgumentType.getString(ctx, "enchantment");
        ResourceLocation id;

        try {
            id = ResourceLocation.parse(raw);
        } catch (Exception e) {
            ctx.getSource().sendFailure(
                    Component.literal("Invalid id: " + raw)
            );
            return 0;
        }

        int[] condition = ElementConditionManager.getCondition(id);

        ServerPlayer player =
                ctx.getSource().getPlayer();

        if (player == null) {
            return Command.SINGLE_SUCCESS;
        }

        player.displayClientMessage(
                Component.literal("Condition: ")
                        .withStyle(ChatFormatting.WHITE)

                        .append(Component.literal("Aer:" + condition[AER.ordinal()] + " ")
                                .withStyle(ChatFormatting.YELLOW))

                        .append(Component.literal("Aqua:" + condition[AQUA.ordinal()] + " ")
                                .withStyle(ChatFormatting.AQUA))

                        .append(Component.literal("Ignis:" + condition[IGNIS.ordinal()] + " ")
                                .withStyle(ChatFormatting.GOLD))

                        .append(Component.literal("Terra:" + condition[TERRA.ordinal()] + " ")
                                .withStyle(ChatFormatting.GREEN))

                        .append(Component.literal("Twisted:" + condition[TWISTED.ordinal()] + " ")
                                .withStyle(ChatFormatting.DARK_PURPLE))

                        .append(Component.literal("Holy:" + condition[HOLY.ordinal()])
                                .withStyle(ChatFormatting.WHITE)),
                false
        );

        return Command.SINGLE_SUCCESS;
    }
}