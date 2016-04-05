package main.java.me.creepsterlgc.core.commands;

import java.util.List;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreWarp;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandWarpInvite {

	public CommandWarpInvite(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "core.warp.invite")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 3 || args.length > 3) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp invite <name> <player>")); return; }

		String name = args[1].toLowerCase();
		CoreWarp warp = CoreDatabase.getWarp(name);
		String player = args[2].toLowerCase();

		if(warp == null) { sender.sendMessage(Text.builder("Warp does not exist!").color(TextColors.RED).build()); return; }

		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PermissionsUtils.has(sender, "core.warp.invite-others")) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not own this warp!"));
			return;
		}

		if(warp.getInvited().contains(player)) {
			sender.sendMessage(Text.of(TextColors.RED, "This player is already invited!"));
			return;
		}

		List<String> invited = warp.getInvited(); invited.add(player); warp.setInvited(invited);
		warp.update();

		sender.sendMessage(Text.of(TextColors.YELLOW, player, TextColors.GRAY, " is now invited to warp ", TextColors.YELLOW, warp.getName()));

	}

}
