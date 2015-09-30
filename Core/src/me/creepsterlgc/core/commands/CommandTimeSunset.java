package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTimeSunset {

	public CommandTimeSunset(CommandSource sender, String[] args, Game game) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.time.sunset")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		game.getCommandDispatcher().process(game.getServer().getConsole(), "minecraft:time set 12500");
		
		game.getServer().getBroadcastSink().sendMessage(Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has changed the time to sunset."));
		
	}

}
