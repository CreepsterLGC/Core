package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreSpawn;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandSpawnMove {

	public CommandSpawnMove(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "core.spawn.move")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/spawn move [name]")); return; }

		Player player = (Player) sender;

		String name = "default";
		if(args.length == 2) name = args[1].toLowerCase();

		CoreSpawn spawn = CoreDatabase.getSpawn(name);

		if(spawn == null) { sender.sendMessage(Text.builder("Spawn does not exist!").color(TextColors.RED).build()); return; }

		spawn.setWorld(player.getWorld().getName());
		spawn.setX(player.getLocation().getX());
		spawn.setY(player.getLocation().getY());
		spawn.setZ(player.getLocation().getZ());
		spawn.update();

		sender.sendMessage(Text.of(TextColors.GRAY, "Spawn ", TextColors.YELLOW, spawn.getName(), TextColors.GRAY, " has been moved to your location."));

	}

}
