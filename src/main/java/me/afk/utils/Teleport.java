package me.afk.utils;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class Teleport {

    public static int tp(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        CommandSourceStack source = ctx.getSource();

        // Ensure the sender is a player (not console or command block)
        Entity from = source.getEntity();
        if (!(from instanceof ServerPlayer)) {
            source.sendSystemMessage(Component.literal("Only players can use this command!"));
            return 0;
        }

        ServerPlayer target = EntityArgument.getPlayer(ctx, "target");

        from.teleportTo(target.getX(), target.getY(), target.getZ());

        return 1;
    }
}
