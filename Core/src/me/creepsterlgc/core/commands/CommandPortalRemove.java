package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePortal;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandPortalRemove {

	public CommandPortalRemove(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.portal.remove")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/portal remove <name>")); return; }
	
		String name = args[1].toLowerCase();
		
		CorePortal p = CoreDatabase.getPortal(name);
		
		if(p == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Portal not found!"));
			return;
		}
		
		p.delete();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Portal ", TextColors.YELLOW, name, TextColors.GRAY, " has been removed."));
	}

}
