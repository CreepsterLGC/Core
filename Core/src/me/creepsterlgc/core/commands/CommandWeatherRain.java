package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.PERMISSIONS;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.weather.Weathers;


public class CommandWeatherRain {

	public CommandWeatherRain(CommandSource sender, String[] args, Game game) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.weather.rain")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		for(World world : Controller.getGame().getServer().getWorlds()) {
			world.forecast(Weathers.RAIN);
		}
		
		Text t1 = Texts.builder(sender.getName()).color(TextColors.YELLOW).build();
		Text t2 = Texts.builder(" has changed the weather to rain.").color(TextColors.GRAY).build();
		
		Text total = Texts.builder().append(t1).append(t2).build();
		
		game.getServer().getBroadcastSink().sendMessage(total);
		
	}

}
