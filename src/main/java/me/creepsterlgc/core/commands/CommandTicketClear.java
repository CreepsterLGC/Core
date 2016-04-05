package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.ServerUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandTicketClear {

	public CommandTicketClear(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.ticket.clear")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket clear")); return; }
	
		int size = CoreDatabase.getTickets().size();
		
		CoreDatabase.queue("DELETE FROM tickets WHERE id != 0");
		CoreDatabase.clearTickets();
		
		if(size == 1) {
			sender.sendMessage(Text.of(TextColors.YELLOW, size, TextColors.GRAY," ticket has been removed!"));
		}
		else {
			sender.sendMessage(Text.of(TextColors.YELLOW, size, TextColors.GRAY," tickets have been removed!"));
		}
		
		ServerUtils.broadcast("core.ticket.notify", Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has cleared all tickets."));
		
	}

}
