package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.WARP;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandWarpCreate {

	public CommandWarpCreate(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.warp.create")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp create <name>")); return; }
		
		Player player = (Player)sender;
		
		String name = args[1].toLowerCase();
		
		if(DATABASE.getWarp(name) != null) { sender.sendMessage(Texts.builder("Warp already exists!").color(TextColors.RED).build()); return; }
		
		int warps = 0;
		for(Entry<String, WARP> e : DATABASE.getWarps().entrySet()) {
			WARP warp = e.getValue();
			if(!warp.getOwner().equalsIgnoreCase(player.getUniqueId().toString())) continue;
			warps += 1;
		}
		
		int possible = 0;
		for(int i = 1; i <= 100; i++) {
			if(PERMISSIONS.has(player, "core.warp.create." + i)) possible = i;
		}
		
		if(!PERMISSIONS.has(player, "core.warp.create.unlimited") && possible <= warps) {
			if(possible == 1) sender.sendMessage(Texts.builder("You are only allowed to own " + possible + " warp!").color(TextColors.RED).build());
			else sender.sendMessage(Texts.builder("You are only allowed to own " + possible + " warps!").color(TextColors.RED).build());
			return;
		}
		
		String world = player.getWorld().getName().toLowerCase();
		
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getRotation().getX();
		double pitch = player.getRotation().getY();
		String owner = player.getUniqueId().toString();
		List<String> invited = new ArrayList<String>();
		String priv = "no";
		String message = "";
		
		WARP warp = new WARP(name, world, x, y, z, yaw, pitch, owner, invited, priv, message);
		warp.insert();
		
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Warp ", TextColors.YELLOW, name, TextColors.GRAY, " has been created."));
		if(!PERMISSIONS.has(player, "core.warp.create.unlimited")) sender.sendMessage(Texts.of(TextColors.GRAY, "You currently own ", TextColors.GOLD, warps + 1, TextColors.GRAY, " / ", TextColors.GOLD, possible, TextColors.GRAY, " possible warps."));
		else sender.sendMessage(Texts.of(TextColors.GRAY, "You currently own ", TextColors.GOLD, warps + 1, TextColors.GRAY, " / ", TextColors.GOLD, "oo", TextColors.GRAY, " possible warps."));
	}

}
