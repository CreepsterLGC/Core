package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.WARP;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandWarpList {

	public CommandWarpList(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.warp.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp list [keyword]")); return; }
		
		if(DATABASE.getWarps().isEmpty()) { sender.sendMessage(Texts.builder("There are currently no warps set.").color(TextColors.YELLOW).build()); return; }
		
		String name = ""; if(args.length > 1) name = args[1].toLowerCase();
		
		List<WARP> warps = new ArrayList<WARP>();
		
		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, WARP> e : DATABASE.getWarps().entrySet()) {
				if(e.getValue().getName().contains(name)) warps.add(e.getValue());
			}
		}
		else {
			for(Entry<String, WARP> e : DATABASE.getWarps().entrySet()) {
				warps.add(e.getValue());
			}
		}
		
		if(warps.isEmpty()) {
			sender.sendMessage(Texts.builder("No warps found by using the specified keyword.").color(TextColors.YELLOW).build());
			return;
		}
		
		StringBuilder list = new StringBuilder();
		for(WARP warp : warps) list.append(warp.getName() + ", "); list.deleteCharAt(list.length() - 2);
		
		if(warps.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(warps.size()) + " Warp found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Texts.builder(String.valueOf(warps.size()) + " Warp found by using keyword: " + name).color(TextColors.GOLD).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(warps.size()) + " Warps found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Texts.builder(String.valueOf(warps.size()) + " Warps found by using keyword: " + name).color(TextColors.GOLD).build());	
		}
		
		sender.sendMessage(Texts.builder(list.toString()).color(TextColors.YELLOW).build());
		
	}

}
