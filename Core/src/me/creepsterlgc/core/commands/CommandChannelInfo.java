package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CHANNEL;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.TEXT;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandChannelInfo {

	public CommandChannelInfo(CommandSource sender, String[] args) {
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/channel info <channel>")); return; }
	
		String channel = args[1].toLowerCase();
		CHANNEL c = DATABASE.getChannel(channel);
		
		if(c == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Channel not found!"));
			return;
		}
		
		if(!PERMISSIONS.has(sender, "core.channel.info." + channel)) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to view this channel!"));
			return;
		}
		
		sender.sendMessage(Texts.of(TextColors.WHITE, "Channel: ", TEXT.color(c.getName())));
		sender.sendMessage(Texts.of(TextColors.WHITE, "ID: ", c.getID()));
		sender.sendMessage(Texts.of(TextColors.WHITE, "Prefix: ", TEXT.color(c.getPrefix())));
		sender.sendMessage(Texts.of(TextColors.WHITE, "Suffix: ", TEXT.color(c.getSuffix())));
		sender.sendMessage(Texts.of(TextColors.WHITE, "Format: ", c.getFormat()));
		sender.sendMessage(Texts.of(TextColors.WHITE, "Range: ", c.getRange()));
		
	}

}
