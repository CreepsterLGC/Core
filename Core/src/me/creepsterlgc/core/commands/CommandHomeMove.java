package me.creepsterlgc.core.commands;

import java.util.HashMap;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreHome;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandHomeMove {

	public CommandHomeMove(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.home.move")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/home move [name]")); return; }
		
		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		
		String name = "default"; if(args.length == 2) name = args[1].toLowerCase();
		CoreHome home = p.getHome(name);
		
		if(home == null) { sender.sendMessage(Texts.builder("Home does not exist!").color(TextColors.RED).build()); return; }
		
		home.setWorld(player.getWorld().getName());
		home.setX(player.getLocation().getX());
		home.setY(player.getLocation().getY());
		home.setZ(player.getLocation().getZ());
		home.update();
		
		HashMap<String, CoreHome> homes = p.getHomes();
		homes.put(name, home);
		p.setHomes(homes);
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Home ", TextColors.YELLOW, name, TextColors.GRAY, " has been moved to your location."));
		
	}

}
