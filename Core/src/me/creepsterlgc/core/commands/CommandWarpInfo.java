package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.SERIALIZE;
import me.creepsterlgc.core.customized.WARP;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandWarpInfo {

	public CommandWarpInfo(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.warp.info")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp info <name>")); return; }
		
		String name = args[1].toLowerCase();
		WARP warp = DATABASE.getWarp(name);
		
		if(warp == null) { sender.sendMessage(Texts.builder("Warp does not exist!").color(TextColors.RED).build()); return; }
		
		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PERMISSIONS.has(sender, "core.warp.info.others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not own this warp!"));
			return;
		}
		
		String invited = SERIALIZE.list(warp.getInvited());
		invited = invited.replaceAll(",", ", ");
		if(invited.equalsIgnoreCase("")) invited = "- none -";
		
		double x = warp.getX(); x *= 100; x = Math.round(x); x /= 100;
		double y = warp.getY(); y *= 100; y = Math.round(y); y /= 100;
		double z = warp.getZ(); z *= 100; z = Math.round(z); z /= 100;
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Information on Warp: ", TextColors.GOLD, warp.getName()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Owner: ", TextColors.YELLOW, DATABASE.getPlayer(warp.getOwner()).getName()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Location: ", TextColors.YELLOW, warp.getWorld(), " | x:", x, " y:", y, " z:", z));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Private: ", TextColors.YELLOW, warp.getPrivate()));
		if(invited.equalsIgnoreCase("- none -")) sender.sendMessage(Texts.of(TextColors.GRAY, "Invited: ", TextColors.YELLOW, invited));
		else sender.sendMessage(Texts.of(TextColors.GRAY, "Invited: ", TextColors.YELLOW, "(", warp.getInvited().size(), ") ", invited));
		
	}

}
