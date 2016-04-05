package main.java.me.creepsterlgc.core.commands;

import java.util.Map.Entry;

import main.java.me.creepsterlgc.core.customized.CoreChannel;
import main.java.me.creepsterlgc.core.customized.CoreChannels;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TextUtils;

import org.spongepowered.api.command.CommandSource;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class CommandChannelList {

	public CommandChannelList(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.channel.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/channel list")); return; }

		sender.sendMessage(Text.of(TextColors.GRAY, "Found ", TextColors.YELLOW, CoreChannels.all().size(), TextColors.GRAY, " channels:"));
		for(Entry<String, CoreChannel> e : CoreChannels.all().entrySet()) {
			sender.sendMessage(Text.of(TextColors.GRAY, "- ", TextColors.WHITE, TextUtils.color(e.getValue().getName()), TextColors.GRAY, " (", e.getKey(), ")"));
		}
		
	}

}
