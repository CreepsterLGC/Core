package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreSpawn;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandSpawnRemove {

	public CommandSpawnRemove(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.spawn.remove")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/spawn remove [name]")); return; }
		
		String name = "default";
		if(args.length == 2) name = args[1].toLowerCase();
		
		CoreSpawn spawn = CoreDatabase.getSpawn(name);
		
		if(spawn == null) { sender.sendMessage(Texts.builder("Spawn does not exist!").color(TextColors.RED).build()); return; }
		
		spawn.delete();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Spawn ", TextColors.YELLOW, spawn.getName(), TextColors.GRAY, " has been removed."));
		
	}

}
