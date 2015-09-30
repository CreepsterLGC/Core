package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

public class CommandMoneyRemove {

	public CommandMoneyRemove(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.money.remove")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/money remove <player> <amount>")); return; }
		
		CorePlayer player = CoreDatabase.getPlayer(CoreDatabase.getUUID(args[1].toLowerCase()));
		if(player == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
			return;
		}
		
		double amount;
		try { amount = Double.parseDouble(args[2]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.of(TextColors.RED, "<amount> has to be a number!"));
			return;
		}
		
		player.removeMoney(amount);
		player.update();
		
		if(Controller.getServer().getPlayer(player.getName()).isPresent()) {
			Player p = Controller.getServer().getPlayer(player.getName()).get();
			sender.sendMessage(Texts.of(TextColors.GRAY, "Removed ", TextColors.GREEN, "$", amount, TextColors.GRAY, " from ", TextColors.YELLOW, p.getName(), TextColors.GRAY, "'s account."));
			p.sendMessage(Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has removed ", TextColors.GREEN, "$", amount, TextColors.GRAY, " from your account."));
			return;
		}
	
		sender.sendMessage(Texts.of(TextColors.GRAY, "Removed ", TextColors.GREEN, "$", amount, TextColors.GRAY, " from ", TextColors.YELLOW, player.getName(), TextColors.GRAY, "'s account."));
		
	}
	
}
