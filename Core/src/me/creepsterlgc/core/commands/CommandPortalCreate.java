package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePortal;
import me.creepsterlgc.core.customized.CoreWarp;
import me.creepsterlgc.core.customized.CoreZone;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandPortalCreate {

	public CommandPortalCreate(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.portal.create")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 4 || args.length > 4) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/portal create <name> <zone> <warp>")); return; }
	
		String name = args[1].toLowerCase();
		
		CoreZone z = CoreDatabase.getZone(args[2].toLowerCase());
		
		if(z == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Zone not found!"));
			return;
		}
		
		CoreWarp w = CoreDatabase.getWarp(args[3].toLowerCase());
		
		if(w == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Warp not found!"));
			return;
		}
		
		CorePortal p = new CorePortal(name, z.getName(), w.getName(), "&6Whooosh!");
		p.insert();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Portal ", TextColors.YELLOW, name, TextColors.GRAY, " has been created."));
	}

}
