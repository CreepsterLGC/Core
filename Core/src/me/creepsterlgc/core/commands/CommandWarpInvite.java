package me.creepsterlgc.core.commands;

import java.util.List;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.WARP;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandWarpInvite {

	public CommandWarpInvite(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.warp.invite")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 3 || args.length > 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp invite <name> <player>")); return; }
		
		String name = args[1].toLowerCase();
		WARP warp = DATABASE.getWarp(name);
		String player = args[2].toLowerCase();
		
		if(warp == null) { sender.sendMessage(Texts.builder("Warp does not exist!").color(TextColors.RED).build()); return; }
		
		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PERMISSIONS.has(sender, "core.warp.invite-others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not own this warp!"));
			return;
		}
		
		if(warp.getInvited().contains(player)) {
			sender.sendMessage(Texts.of(TextColors.RED, "This player is already invited!"));
			return;
		}
		
		List<String> invited = warp.getInvited(); invited.add(player); warp.setInvited(invited);
		warp.update();
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, player, TextColors.GRAY, " is now invited to warp ", TextColors.YELLOW, warp.getName()));
		
	}

}
