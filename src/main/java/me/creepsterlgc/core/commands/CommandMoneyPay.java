package main.java.me.creepsterlgc.core.commands;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

public class CommandMoneyPay {

	public CommandMoneyPay(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.money.pay")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/money pay <player> <amount>")); return; }
		
		Player s = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(s.getUniqueId().toString());
		
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
		
		if(amount <= 0) {
			sender.sendMessage(Texts.of(TextColors.RED, "<amount> has to be a number greater than zero!"));
			return;
		}
		
		if(amount > player.getMoney()) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not got enough money!"));
			return;
		}
		
		p.removeMoney(amount);
		p.update();
		
		player.addMoney(amount);
		player.update();
		
		if(Controller.getServer().getPlayer(player.getName()).isPresent()) {
			Player target = Controller.getServer().getPlayer(player.getName()).get();
			sender.sendMessage(Texts.of(TextColors.GRAY, "You sent ", TextColors.GREEN, "$", amount, TextColors.GRAY, " to ", TextColors.YELLOW, p.getName()));
			target.sendMessage(Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has sent you ", TextColors.GREEN, "$", amount));
			return;
		}
	
		sender.sendMessage(Texts.of(TextColors.GRAY, "You sent ", TextColors.GREEN, "$", amount, TextColors.GRAY, " to ", TextColors.YELLOW, p.getName()));
		
	}
	
}
