package me.afk.utils;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.afk.IMixin.IServerPlayerDeathInfo;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.RelativeMovement;

import java.util.EnumSet;

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

        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();
        ServerLevel level = (ServerLevel) target.level();
        from.teleportTo(level, x, y, z, EnumSet.noneOf(RelativeMovement.class), from.getYRot(), from.getXRot());

        return 1;
    }

    public static int back(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack source = ctx.getSource();
        Entity player = source.getEntity();

        if (player instanceof ServerPlayer serverPlayer) {
            var deathInfo = ((IServerPlayerDeathInfo)serverPlayer).getDeathInfo$tpamm();

            if (deathInfo == null) return 0;

            player.teleportTo(deathInfo.level(), deathInfo.position().x, deathInfo.position().y, deathInfo.position().z, EnumSet.noneOf(RelativeMovement.class), player.getYRot(), player.getXRot());

            return 1;
        }

        return 0;
    }
}
