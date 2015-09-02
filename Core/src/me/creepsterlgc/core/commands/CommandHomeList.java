package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.HOME;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandHomeList {

	public CommandHomeList(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.home.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/home list [keyword]")); return; }
		
		if(DATABASE.getWarps().isEmpty()) { sender.sendMessage(Texts.builder("There are currently no homes set.").color(TextColors.YELLOW).build()); return; }
		
		String name = ""; if(args.length > 1) name = args[1].toLowerCase();
		
		Player player = (Player) sender;
		PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
		
		List<HOME> homes = new ArrayList<HOME>();
		
		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, HOME> e : p.getHomes().entrySet()) {
				if(e.getValue().getName().contains(name)) homes.add(e.getValue());
			}
		}
		else {
			for(Entry<String, HOME> e : p.getHomes().entrySet()) {
				homes.add(e.getValue());
			}
		}
		
		if(homes.isEmpty()) {
			sender.sendMessage(Texts.builder("No homes found by using the specified keyword.").color(TextColors.YELLOW).build());
			return;
		}
		
		StringBuilder list = new StringBuilder();
		for(HOME home : homes) list.append(home.getName() + ", "); list.deleteCharAt(list.length() - 2);
		
		if(homes.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(homes.size()) + " Home found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Texts.builder(String.valueOf(homes.size()) + " Home found by using keyword: " + name).color(TextColors.GOLD).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(homes.size()) + " Homes found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Texts.builder(String.valueOf(homes.size()) + " Homes found by using keyword: " + name).color(TextColors.GOLD).build());	
		}
		
		sender.sendMessage(Texts.builder(list.toString()).color(TextColors.YELLOW).build());
		
	}

}
