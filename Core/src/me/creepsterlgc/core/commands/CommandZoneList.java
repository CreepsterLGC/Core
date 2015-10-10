package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreZone;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandZoneList {

	public CommandZoneList(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.zone.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/zone list [keyword]")); return; }
	
		if(CoreDatabase.getZones().isEmpty()) { sender.sendMessage(Texts.builder("There are currently no zones set.").color(TextColors.YELLOW).build()); return; }
		
		String name = ""; if(args.length > 1) name = args[1].toLowerCase();
		
		List<CoreZone> zones = new ArrayList<CoreZone>();
		
		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, CoreZone> e : CoreDatabase.getZones().entrySet()) {
				if(e.getValue().getName().contains(name)) zones.add(e.getValue());
			}
		}
		else {
			for(Entry<String, CoreZone> e : CoreDatabase.getZones().entrySet()) {
				zones.add(e.getValue());
			}
		}
		
		if(zones.isEmpty()) {
			sender.sendMessage(Texts.builder("No zones found by using the specified keyword.").color(TextColors.AQUA).build());
			return;
		}
		
		StringBuilder list = new StringBuilder();
		for(CoreZone zone : zones) list.append(zone.getName() + ", "); list.deleteCharAt(list.length() - 2);
		
		if(zones.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(zones.size()) + " Zone found:").color(TextColors.DARK_AQUA).build());
			else sender.sendMessage(Texts.builder(String.valueOf(zones.size()) + " Zone found by using keyword: " + name).color(TextColors.DARK_AQUA).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(zones.size()) + " Zones found:").color(TextColors.DARK_AQUA).build());
			else sender.sendMessage(Texts.builder(String.valueOf(zones.size()) + " Zones found by using keyword: " + name).color(TextColors.DARK_AQUA).build());	
		}
		
		sender.sendMessage(Texts.builder(list.toString()).color(TextColors.AQUA).build());
		
	}

}
