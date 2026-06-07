package me.afk.mixins;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.TeleportCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import java.util.function.Predicate;

@Mixin(TeleportCommand.class)
public abstract class MixinTeleportCommand  {

    @ModifyArg(method = "register", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/builder/LiteralArgumentBuilder;requires(Ljava/util/function/Predicate;)Lcom/mojang/brigadier/builder/ArgumentBuilder;"))
    private static Predicate<CommandSourceStack> mo(Predicate<CommandSourceStack> par1) {
        return source -> source.hasPermission(0);
    }
}
