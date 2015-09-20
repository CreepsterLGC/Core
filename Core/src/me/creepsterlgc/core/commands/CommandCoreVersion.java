package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.PERMISSIONS;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandCoreVersion {

	public CommandCoreVersion(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.core.version")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		sender.sendMessage(Texts.of(TextColors.WHITE, "Using Core ", TextColors.GOLD, "v2.3.0e"));
		
	}

}
