package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.World;


public class CommandWorldList {

	public CommandWorldList(CommandSource sender, String[] args, Game game) {
		
		if(!PermissionsUtils.has(sender, "core.world.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/world list")); return; }
		
		List<Text> list = new ArrayList<Text>();
		
		for(World world : Controller.getServer().getWorlds()) {
			if(world.getProperties().isEnabled()) list.add(Texts.of(TextColors.WHITE, world.getName(), TextColors.GRAY, " (", TextColors.GREEN, "active", TextColors.GRAY, ")"));
			else list.add(Texts.of(TextColors.WHITE, world.getName(), TextColors.GRAY, " (", TextColors.RED, "inactive", TextColors.GRAY, ")"));
		}
		
		sender.sendMessage(Texts.of(TextColors.GOLD, "Worlds:"));
		for(Text t : list) sender.sendMessage(t);
		
	}

}
