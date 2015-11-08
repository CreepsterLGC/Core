package main.java.me.creepsterlgc.core.commands;

import java.util.HashMap;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreHome;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandHomeSet {

	public CommandHomeSet(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.builder("Usage: /home set [name]").color(TextColors.YELLOW).build()); return; }
		
		Player player = (Player)sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		
		String name = "default"; if(args.length == 2) name = args[1].toLowerCase();
		
		HashMap<String, CoreHome> homes = p.getHomes();
		if(homes.containsKey(name)) {
			sender.sendMessage(Texts.builder("Home does already exist!").color(TextColors.RED).build());
			return;
		}
		
		int possible = 0;
		for(int i = 1; i <= 100; i++) {
			if(PermissionsUtils.has(player, "core.home.set." + i)) possible = i;
		}
		
		if(!PermissionsUtils.has(player, "core.home.set-unlimited") && possible <= homes.size()) {
			if(possible == 1) sender.sendMessage(Texts.builder("You are only allowed to own " + possible + " home!").color(TextColors.RED).build());
			else sender.sendMessage(Texts.builder("You are only allowed to own " + possible + " homes!").color(TextColors.RED).build());
			return;
		}
		
		String world = player.getWorld().getName();

		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getRotation().getX();
		double pitch = player.getRotation().getY();
		
		CoreHome home = new CoreHome(p.getUUID(), name, world, x, y, z, yaw, pitch);
		home.insert();
		
		p.setHome(name, home);
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Home ", TextColors.YELLOW, name, TextColors.GRAY, " has been set."));
		if(!PermissionsUtils.has(player, "core.home.set-unlimited")) sender.sendMessage(Texts.of(TextColors.GRAY, "You currently own ", TextColors.GOLD, homes.size(), TextColors.GRAY, " / ", TextColors.GOLD, possible, TextColors.GRAY, " possible homes."));
		else sender.sendMessage(Texts.of(TextColors.GRAY, "You currently own ", TextColors.GOLD, homes.size(), TextColors.GRAY, " / ", TextColors.GOLD, "oo", TextColors.GRAY, " possible homes."));
		
	}

}
