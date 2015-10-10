package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePortal;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandPortalList {

	public CommandPortalList(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.portal.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/portal list [keyword]")); return; }
		
		if(CoreDatabase.getPortals().isEmpty()) { sender.sendMessage(Texts.builder("There are currently no portals set.").color(TextColors.YELLOW).build()); return; }
		
		String name = ""; if(args.length > 1) name = args[1].toLowerCase();
		
		List<CorePortal> portals = new ArrayList<CorePortal>();
		
		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, CorePortal> e : CoreDatabase.getPortals().entrySet()) {
				if(e.getValue().getName().contains(name)) portals.add(e.getValue());
			}
		}
		else {
			for(Entry<String, CorePortal> e : CoreDatabase.getPortals().entrySet()) {
				portals.add(e.getValue());
			}
		}
		
		if(portals.isEmpty()) {
			sender.sendMessage(Texts.builder("No portals found by using the specified keyword.").color(TextColors.YELLOW).build());
			return;
		}
		
		StringBuilder list = new StringBuilder();
		for(CorePortal portal : portals) list.append(portal.getName() + ", "); list.deleteCharAt(list.length() - 2);
		
		if(portals.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(portals.size()) + " Portal found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Texts.builder(String.valueOf(portals.size()) + " Portal found by using keyword: " + name).color(TextColors.GOLD).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(portals.size()) + " Portals found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Texts.builder(String.valueOf(portals.size()) + " Portals found by using keyword: " + name).color(TextColors.GOLD).build());	
		}
		
		sender.sendMessage(Texts.builder(list.toString()).color(TextColors.YELLOW).build());
		
	}

}
