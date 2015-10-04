package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreWorld;

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
		
		if(setting.equalsIgnoreCase("animals")
		|| setting.equalsIgnoreCase("monsters")
		|| setting.equalsIgnoreCase("pvp")
		|| setting.equalsIgnoreCase("build")
		|| setting.equalsIgnoreCase("interact")
		|| setting.equalsIgnoreCase("hunger")
		|| setting.equalsIgnoreCase("invulnerable")) {
			
			if(value.equalsIgnoreCase("allow") || value.equalsIgnoreCase("deny")) {
				sender.sendMessage(Texts.of(TextColors.GRAY, "Set ", TextColors.YELLOW, setting, TextColors.GRAY, " to ", TextColors.YELLOW, value, TextColors.GRAY, " on world ", TextColors.GOLD, args[1]));
				
				boolean state = true;
				if(value.equalsIgnoreCase("deny")) state = false;
				
				if(setting.equalsIgnoreCase("animals")) w.allowAnimalSpawning(state);
				else if(setting.equalsIgnoreCase("monsters")) w.allowMonsterSpawning(state);
				else if(setting.equalsIgnoreCase("pvp")) w.allowPVP(state);
				else if(setting.equalsIgnoreCase("build")) w.allowBuild(state);
				else if(setting.equalsIgnoreCase("interact")) w.allowInteract(state);
				else if(setting.equalsIgnoreCase("hunger")) w.allowHunger(state);
				else if(setting.equalsIgnoreCase("invulnerable")) w.allowInvulnerability(state);
				
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
