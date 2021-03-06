package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.weather.Weathers;


public class CommandWeatherStorm {

	public CommandWeatherStorm(CommandSource sender, String[] args, Game game) {

		if(!PermissionsUtils.has(sender, "core.weather.storm")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length == 2) {

			if(args[1].equalsIgnoreCase("*")) {
				for(World world : Controller.getServer().getWorlds()) world.setWeather(Weathers.THUNDER_STORM);
				ServerUtils.broadcast(Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has changed the weather to storm."));
				return;
			}

			if(!Controller.getServer().getWorld(args[1]).isPresent()) {
				sender.sendMessage(Text.of(TextColors.RED, "World not found!"));
				return;
			}

			World world = Controller.getServer().getWorld(args[1]).get();
			world.setWeather(Weathers.THUNDER_STORM);

			ServerUtils.broadcast(Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has changed the weather to storm on ", TextColors.YELLOW, world.getName()));

			return;

		}

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		Player player = (Player) sender;
		World world = player.getWorld();
		world.setWeather(Weathers.THUNDER_STORM);

		ServerUtils.broadcast(Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has changed the weather to storm on ", TextColors.YELLOW, world.getName()));

	}

}
