package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreWarp;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;


public class CommandWarpTeleport {

	public CommandWarpTeleport(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.warp.teleport")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/warp <name>")); return; }
		
		Player player = (Player)sender;

		String name = args[0].toLowerCase();
		CoreWarp warp = CoreDatabase.getWarp(name);
		
		if(warp == null) { sender.sendMessage(Texts.builder("Warp does not exist!").color(TextColors.RED).build()); return; }
		
		if(PermissionsUtils.has(sender, "core.warp.teleport-unlimited")) {
			if(!player.transferToWorld(warp.getWorld(), new Vector3d(warp.getX(), warp.getY(), warp.getZ()))) { sender.sendMessage(Texts.builder("Target world does not exist anymore!").color(TextColors.RED).build()); return; }
			sender.sendMessage(Texts.of(TextColors.GRAY, "Teleported to warp: ", TextColors.YELLOW, name));
			return;
		}
		
		if(!warp.getOwner().equalsIgnoreCase(sender.getName().toLowerCase())) {
			
			if(warp.getPrivate().equalsIgnoreCase("yes") && !PermissionsUtils.has(sender, "core.warp.teleport-invited")) {
				sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to teleport to private warps!"));
				return;
			}
			
			if(warp.getPrivate().equalsIgnoreCase("yes") && !warp.getInvited().contains(sender.getName().toLowerCase())) {
				sender.sendMessage(Texts.of(TextColors.RED, "You are not invited to this warp!"));
				return;
			}
			
		}
		
		Location<World> loc = new Location<World>(Controller.getServer().getWorld(warp.getWorld()).get(), warp.getX(), warp.getY(), warp.getZ());
		player.setLocation(loc);
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Teleported to warp: ", TextColors.YELLOW, name));
		
	}

}
