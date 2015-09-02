package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.WARP;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandWarpPrivate {

	public CommandWarpPrivate(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.warp.private")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp private <name>")); return; }
		
		String name = args[1].toLowerCase();
		WARP warp = DATABASE.getWarp(name);
		
		if(warp == null) { sender.sendMessage(Texts.builder("Warp does not exist!").color(TextColors.RED).build()); return; }
		
		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PERMISSIONS.has(sender, "core.warp.private.others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not own this warp!"));
			return;
		}
		
		warp.setPrivate("yes");
		warp.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Warp ", TextColors.YELLOW, name, TextColors.GRAY, " is now private."));
		
	}

}
