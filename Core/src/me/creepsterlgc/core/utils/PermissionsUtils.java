package me.creepsterlgc.core.utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.command.CommandSource;

public class PermissionsUtils {

	public static boolean has(CommandSource sender, String permission) {
		return sender.hasPermission(permission);
	}
	
	public static boolean has(Player player, String permission) {
		return player.hasPermission(permission);
	}
	
}
