package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreWarp;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.SerializeUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandWarpInfo {

	public CommandWarpInfo(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "core.warp.info")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp info <name>")); return; }

		String name = args[1].toLowerCase();
		CoreWarp warp = CoreDatabase.getWarp(name);

		if(warp == null) { sender.sendMessage(Text.builder("Warp does not exist!").color(TextColors.RED).build()); return; }

		if(!warp.getOwner().equalsIgnoreCase(sender.getName()) && !PermissionsUtils.has(sender, "core.warp.info-others")) {
			sender.sendMessage(Text.of(TextColors.RED, "You do not own this warp!"));
			return;
		}

		String invited = SerializeUtils.list(warp.getInvited());
		invited = invited.replaceAll(",", ", ");
		if(invited.equalsIgnoreCase("")) invited = "- none -";

		double x = warp.getX(); x *= 100; x = Math.round(x); x /= 100;
		double y = warp.getY(); y *= 100; y = Math.round(y); y /= 100;
		double z = warp.getZ(); z *= 100; z = Math.round(z); z /= 100;

		sender.sendMessage(Text.of(TextColors.GRAY, "Information on Warp: ", TextColors.GOLD, warp.getName()));
		sender.sendMessage(Text.of(TextColors.GRAY, "Owner: ", TextColors.YELLOW, CoreDatabase.getPlayer(warp.getOwner()).getName()));
		sender.sendMessage(Text.of(TextColors.GRAY, "Location: ", TextColors.YELLOW, warp.getWorld(), " | x:", x, " y:", y, " z:", z));
		sender.sendMessage(Text.of(TextColors.GRAY, "Private: ", TextColors.YELLOW, warp.getPrivate()));
		if(invited.equalsIgnoreCase("- none -")) sender.sendMessage(Text.of(TextColors.GRAY, "Invited: ", TextColors.YELLOW, invited));
		else sender.sendMessage(Text.of(TextColors.GRAY, "Invited: ", TextColors.YELLOW, "(", warp.getInvited().size(), ") ", invited));

	}

}
