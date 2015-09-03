package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.TICKET;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

import com.flowpowered.math.vector.Vector3d;


public class CommandTicketTP {

	public CommandTicketTP(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.ticket.tp")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket tp <id>")); return; }
	
		Player player = (Player) sender;
		
		int id;
		try { id = Integer.parseInt(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.builder("<id> has to be a number!").color(TextColors.RED).build());
			return;
		}
		
		TICKET ticket = DATABASE.getTicket(id);
		
		if(ticket == null) {
			sender.sendMessage(Texts.builder("Ticket with that ID does not exist!").color(TextColors.RED).build());
			return;
		}
		
		if(!PERMISSIONS.has(sender, "core.ticket.tp.others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PERMISSIONS.has(sender, "core.ticket.tp.assigned")) {
				
			}
			else {
				sender.sendMessage(Texts.builder("You do not have permissions to teleport to this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		String world = ticket.getWorld();
		double x = ticket.getX();
		double y = ticket.getY();
		double z = ticket.getZ();
		
		if(!player.transferToWorld(world, new Vector3d(x, y, z))) { sender.sendMessage(Texts.builder("Target world does not exist anymore!").color(TextColors.RED).build()); return; }
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Teleported to ticket ", TextColors.GREEN, "#", id));
		
	}

}
