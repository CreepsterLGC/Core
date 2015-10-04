package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreWorld;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandWorldEdit {

	public CommandWorldEdit(CommandSource sender, String[] args, Game game) {
		
		if(args.length != 4) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/world edit <world> <setting> <value>")); return; }
		
		CoreWorld w = CoreDatabase.getWorld(args[1]);
		
		if(w == null) {
			sender.sendMessage(Texts.builder("World not found!").color(TextColors.RED).build());
			return;
		}
		
		String setting = args[2].toLowerCase();
		String value = args[3].toLowerCase();
		
		if(setting.equalsIgnoreCase("animals")) {
			
			if(!PermissionsUtils.has(sender, "core.world.edit." + w.getName().toLowerCase() + "." + setting)) {
				sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to edit this setting!"));
				return;
			}
			
			if(value.equalsIgnoreCase("allow")) {
				w.setAnimalSpawning(true);
				sender.sendMessage(Texts.of(TextColors.GRAY, "Set ", TextColors.YELLOW, setting, TextColors.GRAY, " to ", TextColors.YELLOW, value, TextColors.GRAY, " on world ", TextColors.GOLD, args[1]));
				return;
			}
			else if(value.equalsIgnoreCase("deny")) {
				w.setAnimalSpawning(true);
				sender.sendMessage(Texts.of(TextColors.GRAY, "Set ", TextColors.YELLOW, setting, TextColors.GRAY, " to ", TextColors.YELLOW, value, TextColors.GRAY, " on world ", TextColors.GOLD, args[1]));
				return;
			}
			else {
				sender.sendMessage(Texts.of(TextColors.RED, "Allowed values for ", setting, ": allow or deny"));
				return;
			}
			
		}
		else if(setting.equalsIgnoreCase("monsters")) {
			
			if(!PermissionsUtils.has(sender, "core.world.edit." + w.getName().toLowerCase() + "." + setting)) {
				sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to edit this setting!"));
				return;
			}
			
			if(value.equalsIgnoreCase("allow")) {
				w.setMonsterSpawning(true);
				sender.sendMessage(Texts.of(TextColors.GRAY, "Set ", TextColors.YELLOW, setting, TextColors.GRAY, " to ", TextColors.YELLOW, value, TextColors.GRAY, " on world ", TextColors.GOLD, args[1]));
				return;
			}
			else if(value.equalsIgnoreCase("deny")) {
				w.setMonsterSpawning(true);
				sender.sendMessage(Texts.of(TextColors.GRAY, "Set ", TextColors.YELLOW, setting, TextColors.GRAY, " to ", TextColors.YELLOW, value, TextColors.GRAY, " on world ", TextColors.GOLD, args[1]));
				return;
			}
			else {
				sender.sendMessage(Texts.of(TextColors.RED, "Allowed values for ", setting, ": allow or deny"));
				return;
			}
			
		}
		else {
			sender.sendMessage(Texts.of(TextColors.RED, "Available settings: animals, monsters"));
			return;
		}	
		
	}

}
