package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreZone;
import me.creepsterlgc.core.customized.CoreSelection;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandZoneClaim {

	public CommandZoneClaim(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.zone.claim")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/zone claim <name>")); return; }
	
		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		CoreSelection s = p.getSelection();
		
		if(!s.isSet() || !s.isValid()) {
			sender.sendMessage(Texts.builder("You have to make a selection first!").color(TextColors.RED).build());
			return;
		}
				
		String name = args[1].toLowerCase();
		double priority = 0;
		
		if(CoreDatabase.getZone(name) != null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Zone already exist!"));
			return;
		}
		
		double b = 0;
		
		List<CoreZone> zones = new ArrayList<CoreZone>();
		for(Entry<String, CoreZone> e : CoreDatabase.getZones().entrySet()) {
			CoreZone temp = e.getValue();
			if(!temp.getOwner().equalsIgnoreCase(p.getUUID())) continue;
			zones.add(temp);
			b += temp.getLength() * temp.getHeight() * temp.getWidth();
		}
		
		int possiblezones = PermissionsUtils.getClaimZonesLimit(player);
		int possibleblocks = PermissionsUtils.getClaimBlocksLimit(player);
		int possibletotal = PermissionsUtils.getClaimTotalLimit(player);
		
		if(possiblezones != -1 && possiblezones <= zones.size()) {
			if(possiblezones == 1) sender.sendMessage(Texts.builder("You are only allowed to claim " + possiblezones + " zone!").color(TextColors.RED).build());
			else sender.sendMessage(Texts.builder("You are only allowed to claim " + possiblezones + " zones!").color(TextColors.RED).build());
			return;
		}
		
		double blocks = s.getLength() * s.getHeight() * s.getWidth();
		
		if(possibletotal != -1 && b + blocks > possibletotal) {
			sender.sendMessage(Texts.builder("You are only allowed to claim " + possiblezones + " blocks in total!").color(TextColors.RED).build());
			return;
		}
		
		if(possibleblocks != -1 && blocks > possibleblocks) {
			sender.sendMessage(Texts.builder("You are only allowed to claim " + possiblezones + " blocks per zone!").color(TextColors.RED).build());
			return;
		}
		
		CoreZone z = new CoreZone(name, s.getWorld().getName(), s.getX1(), s.getY1(), s.getZ1(), s.getX2(), s.getY2(), s.getZ2(), priority, p.getUUID(), new HashMap<String, Double>(), new HashMap<String, String>());
		z.insert();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Zone ", TextColors.AQUA, name, TextColors.GRAY, " has been claimed."));
	}

}
