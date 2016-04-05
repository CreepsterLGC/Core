package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreSpawn;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandSpawnList {

	public CommandSpawnList(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "core.spawn.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/spawn list [keyword]")); return; }

		if(CoreDatabase.getWarps().isEmpty()) { sender.sendMessage(Text.builder("There are currently no spawns set.").color(TextColors.YELLOW).build()); return; }

		String name = ""; if(args.length > 1) name = args[1].toLowerCase();

		List<CoreSpawn> spawns = new ArrayList<CoreSpawn>();

		if(!name.equalsIgnoreCase("")) {
			for(Entry<String, CoreSpawn> e : CoreDatabase.getSpawns().entrySet()) {
				if(e.getValue().getName().contains(name)) spawns.add(e.getValue());
			}
		}
		else {
			for(Entry<String, CoreSpawn> e : CoreDatabase.getSpawns().entrySet()) {
				spawns.add(e.getValue());
			}
		}

		if(spawns.isEmpty()) {
			sender.sendMessage(Text.builder("No spawns found by using the specified keyword.").color(TextColors.YELLOW).build());
			return;
		}

		StringBuilder list = new StringBuilder();
		for(CoreSpawn spawn : spawns) list.append(spawn.getName() + ", "); list.deleteCharAt(list.length() - 2);

		if(spawns.size() == 1) {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(spawns.size()) + " Spawn found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Text.builder(String.valueOf(spawns.size()) + " Spawn found by using keyword: " + name).color(TextColors.GOLD).build());
		}
		else {
			if(name.equalsIgnoreCase("")) sender.sendMessage(Text.builder(String.valueOf(spawns.size()) + " Spawns found:").color(TextColors.GOLD).build());
			else sender.sendMessage(Text.builder(String.valueOf(spawns.size()) + " Spawns found by using keyword: " + name).color(TextColors.GOLD).build());
		}

		sender.sendMessage(Text.builder(list.toString()).color(TextColors.YELLOW).build());

	}

}
