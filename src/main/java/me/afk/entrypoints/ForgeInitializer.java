// Copyright 2020-2026 Mirsario & Contributors.
// Released under the GNU General Public License 3.0.
// See LICENSE.md for details.

//? if FORGE {
package me.afk.entrypoints;

import me.afk.Tpamm;
import me.afk.command.BackCommand;
import me.afk.command.TpaCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.*;
//? if >=1.18.0 {
import net.minecraftforge.client.*;
//?} else {
/*import net.minecraftforge.fmlclient.*;
*///?}

@Mod(Tpamm.MOD_ID)
@SuppressWarnings("unused")
public class ForgeInitializer {
	public ForgeInitializer() {
//		CameraOverhaul.onInitializeClient();

		//? if >=1.19.0 {
		ModLoadingContext.get().registerExtensionPoint(
			ConfigScreenHandler.ConfigScreenFactory.class,
			() -> new ConfigScreenHandler.ConfigScreenFactory((mc, parentScreen) -> null)
		);
		//?} else {
		/*ModLoadingContext.get().registerExtensionPoint(
			ConfigGuiHandler.ConfigGuiFactory.class,
			() -> new ConfigGuiHandler.ConfigGuiFactory((mc, parentScreen) -> ConfigScreen.getConfigScreen(parentScreen))
		);
		*///?}

		IEventBus eventBus = MinecraftForge.EVENT_BUS;
		eventBus.addListener(TpaCommand::onRegisterCommands);
		eventBus.addListener(BackCommand::onRegisterCommands);
	}
}
//?}
