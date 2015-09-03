package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTicketClear {

	public CommandTicketClear(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.ticket.clear")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket clear")); return; }
	
		int size = DATABASE.getTickets().size();
		
		DATABASE.queue("TRUNCATE TABLE tickets");
		DATABASE.clearTickets();
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, size, TextColors.GRAY," tickets have been removed!"));
		
	}

}
