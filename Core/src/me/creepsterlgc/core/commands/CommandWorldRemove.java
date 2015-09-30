package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;


public class CommandWorldRemove {

	public CommandWorldRemove(CommandSource sender, String[] args, Game game) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.world.remove")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/world remove <world>")); return; }
		
		Player player = (Player)sender;
		String name = args[1];
		
		if(!game.getServer().getWorld(name).isPresent()) {
			sender.sendMessage(Texts.builder("World does not exists!").color(TextColors.RED).build());
			return;
		}
		
		if(!game.getServer().getWorld(name).get().getProperties().isEnabled()) {
			sender.sendMessage(Texts.builder("World does not exists!").color(TextColors.RED).build());
			return;
		}
		
		if(player.getWorld().getName().equalsIgnoreCase(name)) {
			sender.sendMessage(Texts.builder("You are standing inside this world!").color(TextColors.RED).build());
			return;
		}
		
		for(Player p : game.getServer().getOnlinePlayers()) {
			if(!p.getWorld().getName().equalsIgnoreCase(name)) continue;
			p.setLocation(player.getLocation());
			p.sendMessage(Texts.of(TextColors.GRAY, "You have been teleported to ", TextColors.YELLOW, sender.getName()));
		}
		
		World world = game.getServer().getWorld(name).get();
		
		game.getServer().unloadWorld(world);
		
		WorldProperties properties = world.getProperties();
		properties.setEnabled(false);
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "World ", TextColors.YELLOW, name, TextColors.GRAY, " has been removed."));
		sender.sendMessage(Texts.of(TextColors.GOLD, "Don't forget to remove the world folder from your disc!"));
		
	}

}
