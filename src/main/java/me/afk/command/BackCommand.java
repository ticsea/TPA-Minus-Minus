package me.afk.command;


import com.mojang.brigadier.CommandDispatcher;
import me.afk.utils.Teleport;


import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.Commands;
        //? if FABRIC {
/*import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
        *///?} elif FORGE {
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

        //?} elif NEOFORGE {
/*import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
*///?}

public class BackCommand {

    //? if NEOFORGE {
    /*@SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("back")
                        .executes(Teleport::back)

        );
    }
    *///?} else if FORGE {
        @SubscribeEvent
        public static void onRegisterCommands(RegisterCommandsEvent event) {

            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

            dispatcher.register(Commands.literal("back").executes(Teleport::back));
        }
    //?} else if FABRIC {

    /*public static void register() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("back").executes(Teleport::back));
        });

    }

    *///?}

    private static void back() {

    }
}
