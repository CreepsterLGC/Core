package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreSelection;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.PlayerUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandSelectionExpand {

	public CommandSelectionExpand(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.selection.expand")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/s e <range> [direction]")); return; }
	
		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		CoreSelection s = p.getSelection();
		
		if(!s.isSet() || !s.isValid()) {
			sender.sendMessage(Texts.builder("You have to make a selection first!").color(TextColors.RED).build());
			return;
		}
		
		double range = 0;
		
		try { range = Double.parseDouble(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.builder("<range> has to be a number!").color(TextColors.RED).build());
			return;
		}
		
		String facing = PlayerUtils.getFacing(player);
		if(args.length == 3) facing = args[2].toLowerCase();
		
		if(!facing.equalsIgnoreCase("down")
		&& !facing.equalsIgnoreCase("up")
		&& !facing.equalsIgnoreCase("south")
		&& !facing.equalsIgnoreCase("east")
		&& !facing.equalsIgnoreCase("north")
		&& !facing.equalsIgnoreCase("west")) {
			sender.sendMessage(Texts.builder("Invalid direction!").color(TextColors.RED).build());
			return;
		}
		
		s.expand(facing, range);
		p.setSelection(s);
		CoreDatabase.addPlayer(p.getUUID(), p);
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Selection expanded by ", TextColors.AQUA, range, TextColors.GRAY, " blocks."));
	}

}
