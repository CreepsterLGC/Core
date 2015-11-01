package me.creepsterlgc.core.commands;

import java.util.Map.Entry;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandCoreDatabase {

	public CommandCoreDatabase(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.core.database")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		int homes = 0;
		for(Entry<String, CorePlayer> e : CoreDatabase.getPlayers().entrySet()) {
			CorePlayer p = e.getValue(); homes += p.getHomes().size();
		}
		
		sender.sendMessage(Texts.of(TextColors.GOLD, "Core Database:"));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Players: ", TextColors.YELLOW, CoreDatabase.getPlayers().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Bans: ", TextColors.YELLOW, CoreDatabase.getBans().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Mutes: ", TextColors.YELLOW, CoreDatabase.getMutes().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Spawns: ", TextColors.YELLOW, CoreDatabase.getSpawns().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Homes: ", TextColors.YELLOW, homes));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Warps: ", TextColors.YELLOW, CoreDatabase.getWarps().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Tickets: ", TextColors.YELLOW, CoreDatabase.getTickets().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Zones: ", TextColors.YELLOW, CoreDatabase.getZones().size()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Portals: ", TextColors.YELLOW, CoreDatabase.getPortals().size()));
		
	}

}
