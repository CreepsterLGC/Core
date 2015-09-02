package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.BAN;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.TIME;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandBanlistCheck {

	public CommandBanlistCheck(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.banlist.check")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/banlist check <name>")); return; }
	
		String uuid = DATABASE.getUUID(args[1].toLowerCase());
		
		if(uuid == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
			return;
		}
		
		BAN ban = DATABASE.getBan(uuid);
		
		if(ban == null) {
			sender.sendMessage(Texts.of(TextColors.YELLOW, args[1].toLowerCase(), TextColors.GRAY, " is not banned."));
			return;
		}
		
		String time = TIME.toString(ban.getDuration() - System.currentTimeMillis());
		String ago = TIME.toString(System.currentTimeMillis() - ban.getTime());
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, args[1].toLowerCase(), TextColors.GRAY, " is banned for another ", TextColors.YELLOW, time));
		sender.sendMessage(Texts.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, ban.getReason()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Banned by ", TextColors.YELLOW, ban.getSender(), TextColors.GRAY, " | ", TextColors.YELLOW, ago, TextColors.GRAY, " ago"));
		
	}

}
