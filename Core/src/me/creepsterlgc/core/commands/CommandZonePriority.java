package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreZone;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandZonePriority {

	public CommandZonePriority(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.zone.priority")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 3 || args.length > 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/zone priority <zone> <priority>")); return; }
	
		Player player = (Player) sender;
		
		String name = args[1].toLowerCase();
		
		if(CoreDatabase.getZone(name) == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Zone not found!"));
			return;
		}
		
		CoreZone z = CoreDatabase.getZone(name);
		
		if(!z.isOwner(player) && !PermissionsUtils.has(player, "core.zone.priority-others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions!"));
			return;
		}
		
		double priority = 0;
		
		try { priority = Double.parseDouble(args[2]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.builder("<priority> has to be a number!").color(TextColors.RED).build());
			return;
		}
		
		z.setPriority(priority);
		z.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Priority of zone ", TextColors.AQUA, name, TextColors.GRAY, " has been set to ", TextColors.AQUA, priority));
	}

}
