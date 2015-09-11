package me.creepsterlgc.core.commands;

import java.util.Map.Entry;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandCoreDatabase {

	public CommandCoreDatabase(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.core.database")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		int homes = 0;
		for(Entry<String, PLAYER> e : DATABASE.getPlayers().entrySet()) {
			PLAYER p = e.getValue(); homes += p.getHomes().size();
		}
		
		sender.sendMessage(Texts.of(TextColors.GOLD, "Core Database:"));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Players: ", TextColors.YELLOW, DATABASE.getPlayers().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Bans: ", TextColors.YELLOW, DATABASE.getBans().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Mutes: ", TextColors.YELLOW, DATABASE.getMutes().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Spawns: ", TextColors.YELLOW, DATABASE.getSpawns().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Homes: ", TextColors.YELLOW, homes));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Warps: ", TextColors.YELLOW, DATABASE.getWarps().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Tickets: ", TextColors.YELLOW, DATABASE.getTickets().size()));
		
	}

}
