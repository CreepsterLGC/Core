package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreBan;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandBanlistList {

	public CommandBanlistList(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.banlist.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/banlist list [keyword]")); return; }
		
		if(CoreDatabase.getWarps().isEmpty()) { sender.sendMessage(Texts.builder("The banlist is empty.").color(TextColors.YELLOW).build()); return; }
		
		String name = ""; if(args.length > 1) name = args[1].toLowerCase();
		
		List<CoreBan> bans = new ArrayList<CoreBan>();
		
		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, CoreBan> e : CoreDatabase.getBans().entrySet()) {
				if(CoreDatabase.getPlayer(e.getValue().getUUID()).getName().contains(name)) bans.add(e.getValue());
			}
		}
		else {
			for(Entry<String, CoreBan> e : CoreDatabase.getBans().entrySet()) {
				bans.add(e.getValue());
			}
		}
		
		if(bans.isEmpty()) {
			sender.sendMessage(Texts.builder("No bans found by using the specified keyword.").color(TextColors.YELLOW).build());
			return;
		}
		
		StringBuilder list = new StringBuilder();
		for(CoreBan ban : bans) list.append(CoreDatabase.getPlayer(ban.getUUID()).getName() + ", "); list.deleteCharAt(list.length() - 2);
		
		if(bans.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(bans.size()) + " Ban found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Texts.builder(String.valueOf(bans.size()) + " Ban found by using keyword: " + name).color(TextColors.GOLD).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Texts.builder(String.valueOf(bans.size()) + " Bans found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Texts.builder(String.valueOf(bans.size()) + " Bans found by using keyword: " + name).color(TextColors.GOLD).build());	
		}
		
		sender.sendMessage(Texts.builder(list.toString()).color(TextColors.YELLOW).build());
		
	}

}
