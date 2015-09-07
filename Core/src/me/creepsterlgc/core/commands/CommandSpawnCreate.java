package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.SPAWN;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandSpawnCreate {

	public CommandSpawnCreate(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.spawn.create")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/spawn create [name]")); return; }
		
		Player player = (Player)sender;
		
		String name = "default";
		if(args.length == 2) name = args[1].toLowerCase();
		
		if(DATABASE.getSpawn(name) != null) { sender.sendMessage(Texts.builder("Spawn already exists!").color(TextColors.RED).build()); return; }
		
		String world = player.getWorld().getName().toLowerCase();
		
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getRotation().getX();
		double pitch = player.getRotation().getY();
		String message = "";
		
		SPAWN spawn = new SPAWN(name, world, x, y, z, yaw, pitch, message);
		spawn.insert();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Spawn ", TextColors.YELLOW, spawn.getName(), TextColors.GRAY, " has been created."));
		
	}

}
