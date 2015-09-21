package me.creepsterlgc.core.commands;

import java.util.Map.Entry;

import me.creepsterlgc.core.customized.CHANNEL;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.TEXT;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandChannelList {

	public CommandChannelList(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.channel.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/channel list")); return; }

		sender.sendMessage(Texts.of(TextColors.GRAY, "Found ", TextColors.YELLOW, DATABASE.getChannels().size(), TextColors.GRAY, " channels:"));
		for(Entry<String, CHANNEL> e : DATABASE.getChannels().entrySet()) {
			sender.sendMessage(Texts.of(TextColors.GRAY, "- ", TextColors.WHITE, TEXT.color(e.getValue().getName()), TextColors.GRAY, " (", e.getKey(), ")"));
		}
		
	}

}
