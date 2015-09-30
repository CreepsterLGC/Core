package me.creepsterlgc.core.commands;

import java.util.List;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.CommandUtils;
import me.creepsterlgc.core.utils.DeserializeUtils;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.SerializeUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandMailSend {

	public CommandMailSend(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.mail.send")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/mail send <player> <message>")); return; }
	
		CorePlayer player = CoreDatabase.getPlayer(CoreDatabase.getUUID(args[1].toLowerCase()));
		if(player == null) { sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build()); return; }
		
		String message = CommandUtils.combineArgs(2, args);
		
		if(message.contains("-;;")) {
			sender.sendMessage(Texts.builder("'-;;' is not allowed in the message!").color(TextColors.RED).build());
			return;
		}
		
		List<String> mails = DeserializeUtils.messages(player.getMails());
		mails.add(String.valueOf(System.currentTimeMillis()) + ":" + sender.getName() + ":" + message);
		player.setMails(SerializeUtils.messages(mails));
		player.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Mail has been sent to ", TextColors.YELLOW, player.getName()));
		
	}

}
