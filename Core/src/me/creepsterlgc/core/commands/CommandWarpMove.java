package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.WARP;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandWarpMove {

	public CommandWarpMove(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.warp.move")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp move <name>")); return; }
		
		Player player = (Player) sender;
		
		String name = args[1].toLowerCase();
		WARP warp = DATABASE.getWarp(name);
		
		if(warp == null) { sender.sendMessage(Texts.builder("Warp does not exist!").color(TextColors.RED).build()); return; }
		
		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PERMISSIONS.has(sender, "core.warp.move-others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not own this warp!"));
			return;
		}
		
		warp.setWorld(player.getWorld().getName().toLowerCase());
		warp.setX(player.getLocation().getX());
		warp.setY(player.getLocation().getY());
		warp.setZ(player.getLocation().getZ());
		warp.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Warp ", TextColors.YELLOW, name, TextColors.GRAY, " has been moved to your location."));
		
	}

}
