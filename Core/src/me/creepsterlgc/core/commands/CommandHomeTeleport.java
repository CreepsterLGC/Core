package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.HOME;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class CommandHomeTeleport {

	public CommandHomeTeleport(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.home.teleport")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length > 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/home [name]")); return; }
		
		Player player = (Player)sender;
		PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
		
		String name = "default"; if(!args[0].equalsIgnoreCase("")) name = args[0].toLowerCase();
		HOME home = p.getHome(name);
		
		if(home == null) { sender.sendMessage(Texts.builder("Home does not exist!").color(TextColors.RED).build()); return; }
		
		Location<World> loc = new Location<World>(Controller.getServer().getWorld(home.getWorld()).get(), home.getX(), home.getY(), home.getZ());
		player.setLocation(loc);
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Teleported to home: ", TextColors.YELLOW, name));
		
	}

}
