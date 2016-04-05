package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.customized.CoreBan;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TimeUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandBanlistCheck {

	public CommandBanlistCheck(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "core.banlist.check")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 2 || args.length > 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/banlist check <name>")); return; }

		String uuid = CoreDatabase.getUUID(args[1].toLowerCase());

		if(uuid == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Player not found!"));
			return;
		}

		CoreBan ban = CoreDatabase.getBan(uuid);

		if(ban == null) {
			sender.sendMessage(Text.of(TextColors.YELLOW, args[1].toLowerCase(), TextColors.GRAY, " is not banned."));
			return;
		}

		String time = TimeUtils.toString(ban.getDuration() - System.currentTimeMillis());
		String ago = TimeUtils.toString(System.currentTimeMillis() - ban.getTime());

		sender.sendMessage(Text.of(TextColors.YELLOW, args[1].toLowerCase(), TextColors.GRAY, " is banned for another ", TextColors.YELLOW, time));
		sender.sendMessage(Text.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, ban.getReason()));
		sender.sendMessage(Text.of(TextColors.GRAY, "Banned by ", TextColors.YELLOW, ban.getSender(), TextColors.GRAY, " | ", TextColors.YELLOW, ago, TextColors.GRAY, " ago"));

	}

}
