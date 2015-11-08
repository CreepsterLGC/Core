package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

public class CommandMoneyAdd {

	public CommandMoneyAdd(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.money.add")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/money add <player> <amount>")); return; }
		
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
		
		player.addMoney(amount);
		player.update();
		
		if(Controller.getServer().getPlayer(player.getName()).isPresent()) {
			Player p = Controller.getServer().getPlayer(player.getName()).get();
			sender.sendMessage(Texts.of(TextColors.GRAY, "Added ", TextColors.GREEN, "$", amount, TextColors.GRAY, " to ", TextColors.YELLOW, p.getName(), TextColors.GRAY, "'s account."));
			p.sendMessage(Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has added ", TextColors.GREEN, "$", amount, TextColors.GRAY, " to your account."));
			return;
		}
	
		sender.sendMessage(Texts.of(TextColors.GRAY, "Added ", TextColors.GREEN, "$", amount, TextColors.GRAY, " to ", TextColors.YELLOW, player.getName(), TextColors.GRAY, "'s account."));
		
	}
	
}
