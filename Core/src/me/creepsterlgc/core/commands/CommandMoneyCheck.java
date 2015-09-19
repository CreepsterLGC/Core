package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

public class CommandMoneyCheck {

	public CommandMoneyCheck(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.money.check-others")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/money [player]")); return; }
		
		PLAYER player = DATABASE.getPlayer(DATABASE.getUUID(args[0].toLowerCase()));
		if(player == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
			return;
		}
	
		sender.sendMessage(Texts.of(TextColors.GRAY, player.getName(), "'s money: ", TextColors.GREEN, "$", player.getMoney()));
		
	}
	
}
