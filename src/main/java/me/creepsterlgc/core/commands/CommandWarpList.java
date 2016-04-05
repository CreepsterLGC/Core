package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreWarp;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandWarpList {

	public CommandWarpList(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "core.warp.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp list [keyword]")); return; }

		if(CoreDatabase.getWarps().isEmpty()) { sender.sendMessage(Text.builder("There are currently no warps set.").color(TextColors.YELLOW).build()); return; }

		String name = ""; if(args.length > 1) name = args[1].toLowerCase();

		List<CoreWarp> warps = new ArrayList<CoreWarp>();

		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, CoreWarp> e : CoreDatabase.getWarps().entrySet()) {
				if(e.getValue().getName().contains(name)) warps.add(e.getValue());
			}
		}
		else {
			for(Entry<String, CoreWarp> e : CoreDatabase.getWarps().entrySet()) {
				warps.add(e.getValue());
			}
		}

		if(warps.isEmpty()) {
			sender.sendMessage(Text.builder("No warps found by using the specified keyword.").color(TextColors.YELLOW).build());
			return;
		}

		StringBuilder list = new StringBuilder();
		for(CoreWarp warp : warps) list.append(warp.getName() + ", "); list.deleteCharAt(list.length() - 2);

		if(warps.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(warps.size()) + " Warp found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Text.builder(String.valueOf(warps.size()) + " Warp found by using keyword: " + name).color(TextColors.GOLD).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(warps.size()) + " Warps found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Text.builder(String.valueOf(warps.size()) + " Warps found by using keyword: " + name).color(TextColors.GOLD).build());
		}

		sender.sendMessage(Text.builder(list.toString()).color(TextColors.YELLOW).build());

	}

}
