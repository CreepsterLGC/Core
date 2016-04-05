package main.java.me.creepsterlgc.core.commands;

import java.util.List;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreTicket;
import main.java.me.creepsterlgc.core.utils.CommandUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandTicketComment {

	public CommandTicketComment(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.ticket.comment")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 3) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket comment <id> <message>")); return; }
	
		Player player = (Player) sender;
		
		int id;
		try { id = Integer.parseInt(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Text.builder("<id> has to be a number!").color(TextColors.RED).build());
			return;
		}
		
		CoreTicket ticket = CoreDatabase.getTicket(id);
		
		if(ticket == null) {
			sender.sendMessage(Text.builder("Ticket with that ID does not exist!").color(TextColors.RED).build());
			return;
		}
		
		String message = CommandUtils.combineArgs(2, args);
		
		if(message.contains("-;;")) {
			sender.sendMessage(Text.builder("'-;;' is not allowed in the message!").color(TextColors.RED).build());
			return;
		}
		
		if(!PermissionsUtils.has(sender, "core.ticket.comment-others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PermissionsUtils.has(sender, "core.ticket.comment-assigned")) {
				
			}
			else {
				sender.sendMessage(Text.builder("You do not have permissions to comment this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		List<String> comments = ticket.getComments();
		comments.add(sender.getName() + ": " + message);
		ticket.setComments(comments);
		ticket.update();
		
		sender.sendMessage(Text.of(TextColors.GRAY, "Comment has been added to ", TextColors.GREEN, "#", id));
		
		ServerUtils.broadcast("core.ticket.notify", Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has commented on ticket ", TextColors.GREEN, "#", id));
		
	}

}
