// Copyright 2020-2026 Mirsario & Contributors.
// Released under the GNU General Public License 3.0.
// See LICENSE.md for details.

//? if NEOFORGE {
package me.afk.entrypoints;

import me.afk.Tpamm;

import me.afk.command.TpaCommand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.gui.*;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Tpamm.MOD_ID)
@SuppressWarnings("unused")
public class NeoForgeInitializer {
	public NeoForgeInitializer() {
//		CameraOverhaul.onInitializeClient();

		ModLoadingContext modLoadingContext = ModLoadingContext.get();
		IEventBus eventBus = NeoForge.EVENT_BUS;

		modLoadingContext.registerExtensionPoint(IConfigScreenFactory.class, () -> (mc, p) -> null);

		eventBus.addListener(TpaCommand::onRegisterCommands);
	}
}
//?}
