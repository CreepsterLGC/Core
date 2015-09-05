package me.creepsterlgc.core.commands;

import java.util.List;

import me.creepsterlgc.core.customized.COMMAND;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.TICKET;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTicketComment {

	public CommandTicketComment(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.ticket.comment")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket comment <id> <message>")); return; }
	
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
		
		String message = COMMAND.combineArgs(2, args);
		
		if(message.contains("-;;")) {
			sender.sendMessage(Texts.builder("'-;;' is not allowed in the message!").color(TextColors.RED).build());
			return;
		}
		
		if(!PERMISSIONS.has(sender, "core.ticket.comment-others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PERMISSIONS.has(sender, "core.ticket.comment-assigned")) {
				
			}
			else {
				sender.sendMessage(Texts.builder("You do not have permissions to comment this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		List<String> comments = ticket.getComments();
		comments.add(sender.getName() + ": " + message);
		ticket.setComments(comments);
		ticket.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Comment has been added to ", TextColors.GREEN, "#", id));
		
	}

}
