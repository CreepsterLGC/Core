package me.creepsterlgc.core.customized;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

public class PERMISSIONS {

	public static boolean has(CommandSource sender, String permission) {
		return sender.hasPermission(permission);
	}
	
	public static boolean has(Player player, String permission) {
		return player.hasPermission(permission);
	}
	
}
