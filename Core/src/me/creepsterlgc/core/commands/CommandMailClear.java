package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandMailClear {

	public CommandMailClear(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PERMISSIONS.has(sender, "core.mail.clear")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/mail clear")); return; }
	
		Player player = (Player) sender;
		PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
		
		p.setMails("");
		p.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Your inbox has been cleared."));
		
	}

}
