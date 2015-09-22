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
		
		sender.sendMessage(Texts.of(TextColors.WHITE, "Channel: ", TextColors.GRAY, TEXT.color(c.getName())));
		sender.sendMessage(Texts.of(TextColors.WHITE, "ID: ", TextColors.GRAY, c.getID()));
		sender.sendMessage(Texts.of(TextColors.WHITE, "Prefix: ", TextColors.GRAY, TEXT.color(c.getPrefix())));
		sender.sendMessage(Texts.of(TextColors.WHITE, "Suffix: ", TextColors.GRAY, TEXT.color(c.getSuffix())));
		sender.sendMessage(Texts.of(TextColors.WHITE, "Format: ", TextColors.GRAY, c.getFormat()));
		sender.sendMessage(Texts.of(TextColors.WHITE, "Range: ", TextColors.GRAY, c.getRange()));
		
	}

}
