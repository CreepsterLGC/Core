package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandTicketClear {

	public CommandTicketClear(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.ticket.clear")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ticket clear")); return; }
	
		int size = CoreDatabase.getTickets().size();
		
		CoreDatabase.queue("DELETE FROM tickets WHERE id != 0");
		CoreDatabase.clearTickets();
		
		if(size == 1) {
			sender.sendMessage(Texts.of(TextColors.YELLOW, size, TextColors.GRAY," ticket has been removed!"));
		}
		else {
			sender.sendMessage(Texts.of(TextColors.YELLOW, size, TextColors.GRAY," tickets have been removed!"));
		}
		
		ServerUtils.broadcast("core.ticket.notify", Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has cleared all tickets."));
		
	}

}
