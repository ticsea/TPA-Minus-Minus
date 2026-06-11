package me.afk.IMixin;

import me.afk.DeathInfo;

public interface IServerPlayerDeathInfo {
    default DeathInfo getDeathInfo$tpamm() {
        return null;
    }
}
