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


public class CommandTimeNight {

	public CommandTimeNight(CommandSource sender, String[] args, Game game) {

		if(!PermissionsUtils.has(sender, "core.time.night")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length == 2) {

			if(args[1].equalsIgnoreCase("*")) {
				for(World world : Controller.getServer().getWorlds()) world.getProperties().setWorldTime(14000);
				ServerUtils.broadcast(Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has changed the time to night."));
				return;
			}

			if(!Controller.getServer().getWorld(args[1]).isPresent()) {
				sender.sendMessage(Text.of(TextColors.RED, "World not found!"));
				return;
			}

			World world = Controller.getServer().getWorld(args[1]).get();
			world.getProperties().setWorldTime(14000);

			ServerUtils.broadcast(Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has changed the time to night on ", TextColors.YELLOW, world.getName()));

			return;

		}

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		Player player = (Player) sender;
		World world = player.getWorld();
		world.getProperties().setWorldTime(14000);

		ServerUtils.broadcast(Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has changed the time to night on ", TextColors.YELLOW, world.getName()));

	}

}
