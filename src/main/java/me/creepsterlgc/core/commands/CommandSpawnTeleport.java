package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreSpawn;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class CommandSpawnTeleport {

	public CommandSpawnTeleport(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "core.spawn.teleport")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/spawn [name]")); return; }

		Player player = (Player)sender;

		String name = "default"; if(!args[0].equalsIgnoreCase("")) name = args[0].toLowerCase();
		CoreSpawn spawn = CoreDatabase.getSpawn(name);

		if(spawn == null) { sender.sendMessage(Text.builder("Spawn does not exist!").color(TextColors.RED).build()); return; }

		Location<World> loc = new Location<World>(Controller.getServer().getWorld(spawn.getWorld()).get(), spawn.getX(), spawn.getY(), spawn.getZ());
		player.setLocation(loc);

		sender.sendMessage(Text.of(TextColors.GRAY, "Teleported to spawn: ", TextColors.YELLOW, name));

	}

}
