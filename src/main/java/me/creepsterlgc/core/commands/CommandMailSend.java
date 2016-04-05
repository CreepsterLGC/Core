package main.java.me.creepsterlgc.core.commands;

import java.util.List;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.CommandUtils;
import main.java.me.creepsterlgc.core.utils.DeserializeUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.SerializeUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandMailSend {

	public CommandMailSend(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "core.mail.send")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 3) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/mail send <player> <message>")); return; }

		CorePlayer player = CoreDatabase.getPlayer(CoreDatabase.getUUID(args[1].toLowerCase()));
		if(player == null) { sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build()); return; }

		String message = CommandUtils.combineArgs(2, args);

		if(message.contains("-;;")) {
			sender.sendMessage(Text.builder("'-;;' is not allowed in the message!").color(TextColors.RED).build());
			return;
		}

		List<String> mails = DeserializeUtils.messages(player.getMails());
		mails.add(String.valueOf(System.currentTimeMillis()) + ":" + sender.getName() + ":" + message);
		player.setMails(SerializeUtils.messages(mails));
		player.update();

		sender.sendMessage(Text.of(TextColors.GRAY, "Mail has been sent to ", TextColors.YELLOW, player.getName()));

	}

}
