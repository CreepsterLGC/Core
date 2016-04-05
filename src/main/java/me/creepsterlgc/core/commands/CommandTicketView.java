package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreTicket;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandTicketView {

	public CommandTicketView(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.ticket.view")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket view <id>")); return; }
	
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
		
		if(!PermissionsUtils.has(sender, "core.ticket.view-others")) {
			if(ticket.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
				
			}
			else if(ticket.getAssigned().equalsIgnoreCase(player.getUniqueId().toString()) && PermissionsUtils.has(sender, "core.ticket.view-assigned")) {
				
			}
			else {
				sender.sendMessage(Text.builder("You do not have permissions to view this ticket!").color(TextColors.RED).build());
				return;
			}
		}
		
		Text p = Text.of(TextColors.DARK_GREEN, "Low");
		if(ticket.getPriority().equalsIgnoreCase("medium")) p = Text.of(TextColors.YELLOW, "Medium");
		else if(ticket.getPriority().equalsIgnoreCase("high")) p = Text.of(TextColors.RED, "High");
		
		sender.sendMessage(Text.of(TextColors.GOLD, "Information for Ticket ", TextColors.GREEN, "#", id));
		sender.sendMessage(Text.of(TextColors.GRAY, "By: ", TextColors.YELLOW, CoreDatabase.getPlayer(ticket.getUUID()).getName(), TextColors.GRAY, " | ", TextColors.GRAY, TimeUtils.toString(System.currentTimeMillis() - ticket.getTime()), TextColors.YELLOW, " ago", TextColors.GRAY, " | Priority: ", p));
		String assigned = "- no -"; if(!ticket.getAssigned().equalsIgnoreCase("")) assigned = CoreDatabase.getPlayer(ticket.getAssigned()).getName();
		sender.sendMessage(Text.of(TextColors.GRAY, "Assigned: ", TextColors.YELLOW, assigned, TextColors.GRAY, " | Status: ", TextColors.YELLOW, ticket.getStatus()));
		sender.sendMessage(Text.of(TextColors.GRAY, "Location: ", TextColors.YELLOW, "world: ", ticket.getWorld(), " x:", Math.round(ticket.getX()), " y:", Math.round(ticket.getY()), " z:", Math.round(ticket.getZ())));
		sender.sendMessage(Text.of(TextColors.GRAY, "Message: ", TextColors.WHITE, ticket.getMessage()));
		if(ticket.getComments().isEmpty()) { sender.sendMessage(Text.of(TextColors.GREEN, "Comments: ", TextColors.GRAY, "- none -")); }
		else {
			sender.sendMessage(Text.of(TextColors.GREEN, "Comments:"));
			for(String comment : ticket.getComments()) sender.sendMessage(Text.of(TextColors.GREEN, "- ", TextColors.WHITE, comment));
		}
		
	}

}
