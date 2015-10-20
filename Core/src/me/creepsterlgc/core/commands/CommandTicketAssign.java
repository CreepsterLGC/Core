package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreTicket;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTicketAssign {

	public CommandTicketAssign(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.ticket.assign")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket assign <id> <player>")); return; }
	
		Player player = (Player) sender;
		
		int id;
		try { id = Integer.parseInt(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.builder("<id> has to be a number!").color(TextColors.RED).build());
			return;
		}
		
		CoreTicket ticket = CoreDatabase.getTicket(id);
		
		if(ticket == null) {
			sender.sendMessage(Texts.builder("Ticket with that ID does not exist!").color(TextColors.RED).build());
			return;
		}
		
		String uuid = CoreDatabase.getUUID(args[2].toLowerCase());
		
		if(uuid == null) {
			sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
			return;
		}
		
		if(!PermissionsUtils.has(sender, "core.ticket.assign-others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PermissionsUtils.has(sender, "core.ticket.assign-assigned")) {
				
			}
			else {
				sender.sendMessage(Texts.builder("You do not have permissions to assign this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		ticket.setAssigned(uuid);
		ticket.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Ticket ", TextColors.GREEN, "#", id, TextColors.GRAY, " has been assigned to ", TextColors.YELLOW, CoreDatabase.getPlayer(uuid).getName()));
		
		ServerUtils.broadcast("core.ticket.notify", Texts.of(TextColors.YELLOW, args[2].toLowerCase(), TextColors.GRAY, " has been assigned to ticket ", TextColors.GREEN, "#", id));
		
	}

}
