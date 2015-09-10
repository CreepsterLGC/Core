package me.creepsterlgc.core.commands;

import java.util.List;

import me.creepsterlgc.core.customized.COMMAND;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.DESERIALIZE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.SERIALIZE;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandMailSend {

	public CommandMailSend(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.mail.send")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/mail send <player> <message>")); return; }
	
		PLAYER player = DATABASE.getPlayer(DATABASE.getUUID(args[1].toLowerCase()));
		if(player == null) { sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build()); return; }
		
		String message = COMMAND.combineArgs(2, args);
		
		if(message.contains("-;;")) {
			sender.sendMessage(Texts.builder("'-;;' is not allowed in the message!").color(TextColors.RED).build());
			return;
		}
		
		List<String> mails = DESERIALIZE.messages(player.getMails());
		mails.add(String.valueOf(System.currentTimeMillis()) + ":" + sender.getName() + ":" + message);
		player.setMails(SERIALIZE.messages(mails));
		player.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Mail has been sent to ", TextColors.YELLOW, player.getName()));
		
	}

}
