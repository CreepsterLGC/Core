package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreTicket;
import me.creepsterlgc.core.utils.CommandUtils;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTicketCreate {

	public CommandTicketCreate(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket create <message>")); return; }
	
		Player player = (Player) sender;
		String message = CommandUtils.combineArgs(1, args);
		
		int tickets = 0;
		for(Entry<Integer, CoreTicket> e : CoreDatabase.getTickets().entrySet()) {
			CoreTicket ticket = e.getValue();
			if(!ticket.getStatus().equalsIgnoreCase("open")) continue;
			if(!ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) continue;
			tickets += 1;
		}
		
		int possible = 0;
		for(int i = 1; i <= 100; i++) {
			if(PermissionsUtils.has(player, "core.ticket.create." + i)) possible = i;
		}
		
		if(!PermissionsUtils.has(player, "core.ticket.create-unlimited") && possible <= tickets) {
			if(possible == 1) sender.sendMessage(Texts.builder("You are only allowed to own " + possible + " open ticket!").color(TextColors.RED).build());
			else sender.sendMessage(Texts.builder("You are only allowed to own " + possible + " open tickets!").color(TextColors.RED).build());
			return;
		}
		
		int id = CoreDatabase.getTickets().size() + 1;
		
		String world = player.getWorld().getName();
		
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		double yaw = player.getRotation().getX();
		double pitch = player.getRotation().getY();
		
		double time = System.currentTimeMillis();
		
		CoreTicket ticket = new CoreTicket(id, player.getUniqueId().toString(), message, time, new ArrayList<String>(), world, x, y, z, yaw, pitch, "", "medium", "open");
		ticket.insert();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Ticket ", TextColors.GREEN, "#", id, TextColors.GRAY, " has been created!"));
		
		ServerUtils.broadcast("core.ticket.notify", Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has submitted ticket ", TextColors.GREEN, "#", id));
		
	}

}
