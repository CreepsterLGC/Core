package me.creepsterlgc.core.commands;

import java.util.HashMap;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreZone;
import me.creepsterlgc.core.customized.CoreSelection;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandZoneCreate {

	public CommandZoneCreate(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.zone.create")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/zone create <name> [priority]")); return; }
	
		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		CoreSelection s = p.getSelection();
		
		if(!s.isSet() || !s.isValid()) {
			sender.sendMessage(Texts.builder("You have to make a selection first!").color(TextColors.RED).build());
			return;
		}
				
		String name = args[1].toLowerCase();
		double priority = 0;
		
		if(args.length == 3) {
			try { priority = Double.parseDouble(args[2]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Texts.builder("[priority] has to be a number!").color(TextColors.RED).build());
				return;
			}
		}
		
		if(CoreDatabase.getZone(name) != null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Zone already exist!"));
			return;
		}
		
		CoreZone z = new CoreZone(name, s.getWorld().getName(), s.getX1(), s.getY1(), s.getZ1(), s.getX2(), s.getY2(), s.getZ2(), priority, p.getUUID(), new HashMap<String, Double>(), new HashMap<String, String>());
		z.insert();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Zone ", TextColors.AQUA, name, TextColors.GRAY, " has been created."));
	}

}
