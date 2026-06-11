package me.afk;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public record DeathInfo(
        ServerLevel level,
        Vec3 position
) {
}
