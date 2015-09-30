package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreChannel;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.TextUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandChannelLeave {

	public CommandChannelLeave(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/channel leave <channel>")); return; }
	
		String channel = args[1].toLowerCase();
		CoreChannel c = CoreDatabase.getChannel(channel);
		
		if(c == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Channel not found!"));
			return;
		}
		
		if(!PermissionsUtils.has(sender, "core.channel.leave." + channel)) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to leave this channel!"));
			return;
		}
		
		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		
		if(!p.getChannel().equalsIgnoreCase(channel)) {
			sender.sendMessage(Texts.of(TextColors.RED, "You are not talking in this channel!"));
			return;
		}
		
		p.setChannel("");
		p.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Left channel: ", TextColors.WHITE, TextUtils.color(c.getName())));
		
	}

}
