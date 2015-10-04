package me.creepsterlgc.core.commands;


import java.io.File;
import java.util.ArrayList;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreWorld;
import me.creepsterlgc.core.files.FileWorlds;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.DimensionType;
import org.spongepowered.api.world.DimensionTypes;
import org.spongepowered.api.world.GeneratorType;
import org.spongepowered.api.world.GeneratorTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.difficulty.Difficulties;


public class CommandWorldLoad {

	public CommandWorldLoad(CommandSource sender, String[] args, Game game) {
		
		if(!PermissionsUtils.has(sender, "core.world.load")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 4) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/world load <name> <environment> <gamemode>")); return; }
		
		String name = args[1];
		String environment = args[2].toLowerCase();
		String mode = args[3].toLowerCase();
		
    	File folder = new File(Controller.getServer().getDefaultWorld().get().getWorldName() + "/" + name);
    	if(!folder.exists()) {
			sender.sendMessage(Texts.builder("World does not exists!").color(TextColors.RED).build());
			return;
    	}
	
		DimensionType dimension;
		GeneratorType generator;
		GameMode gamemode;
		
		if(environment.equalsIgnoreCase("overworld")) {
			dimension = DimensionTypes.OVERWORLD;
			generator = GeneratorTypes.OVERWORLD;
		}
		else if(environment.equalsIgnoreCase("nether")) {
			dimension = DimensionTypes.NETHER;
			generator = GeneratorTypes.NETHER;
		}
		else if(environment.equalsIgnoreCase("end")) {
			dimension = DimensionTypes.END;
			generator = GeneratorTypes.END;
		}
		else {
			sender.sendMessage(Texts.builder("<environment> has to be: overworld, nether or end").color(TextColors.RED).build());
			return;
		}
		
		if(mode.equalsIgnoreCase("survival")) {
			gamemode = GameModes.SURVIVAL;
		}
		else if(mode.equalsIgnoreCase("creative")) {
			gamemode = GameModes.CREATIVE;
		}
		else if(mode.equalsIgnoreCase("adventure")) {
			gamemode = GameModes.ADVENTURE;
		}
		else if(mode.equalsIgnoreCase("spectator")) {
			gamemode = GameModes.SPECTATOR;
		}
		else {
			sender.sendMessage(Texts.builder("<gamemode> has to be: survival, creative, adventure or spectator").color(TextColors.RED).build());
			return;
		}
	
		sender.sendMessage(Texts.of(TextColors.GRAY, "Loading world ", TextColors.YELLOW, name, TextColors.GRAY, ".."));
		
		game.getRegistry().createWorldBuilder()
		.name(name)
		.enabled(true)
		.loadsOnStartup(true)
		.keepsSpawnLoaded(true)
		.dimensionType(dimension)
		.generator(generator)
		.gameMode(gamemode)
		.build();
		
		if(CoreDatabase.getWorld(name) == null) {
		
			World world = Controller.getServer().getWorld(name).get();
			CoreWorld w = new CoreWorld(name, false, new ArrayList<String>(), Difficulties.EASY, gamemode, true, true, true, true, true, world.getSpawnLocation(), true, false, "normal", "normal", 0, 2);
			CoreDatabase.addWorld(name, w);
			FileWorlds.save(w);
			
		}
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "World ", TextColors.YELLOW, name, TextColors.GRAY, " has been loaded."));
		
	}

}
