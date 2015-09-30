package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandMoney implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(args.length > 3) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) {
			if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
			if(!PermissionsUtils.has(sender, "core.money.check")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
			Player player = (Player)sender; CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
			player.sendMessage(Texts.of(TextColors.GRAY, "Money: ", TextColors.GREEN, "$", p.getMoney()));
			return CommandResult.success();
		}
		
		if(args[0].equalsIgnoreCase("top")) { new CommandMoneyTop(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("pay")) { new CommandMoneyPay(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("add")) { new CommandMoneyAdd(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("remove")) { new CommandMoneyRemove(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("set")) { new CommandMoneySet(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Money Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money [name]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money top"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money pay <player> <amount>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money add <player> <amount>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money remove <player> <amount>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money set <player> <amount>"));
		}
		else if(!arguments.equalsIgnoreCase("") && args.length == 1) {
			new CommandMoneyCheck(sender, args); return CommandResult.success();
		}
		else {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Money Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money [name]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money top"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money pay <player> <amount>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money add <player> <amount>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money remove <player> <amount>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/money set <player> <amount>"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /money help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /money help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Money Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
