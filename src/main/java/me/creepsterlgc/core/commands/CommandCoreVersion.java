package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.utils.PermissionsUtils;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandCoreVersion {

	public CommandCoreVersion(CommandSource sender, String[] args) {

		if(!PermissionsUtils.has(sender, "core.core.version")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		sender.sendMessage(Text.of(TextColors.WHITE, "Using Core ", TextColors.GOLD, "v2.8.1c"));

	}

}
