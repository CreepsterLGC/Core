package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandBanlist implements CommandCallable {
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(args.length > 4) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("check")) { new CommandBanlistCheck(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandBanlistList(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("rollback")) { new CommandBanlistRollback(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Banlist Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/banlist check <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/banlist list [keyword]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/banlist rollback <sender> <time> <unit>"));
		}
		else {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Banlist Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/banlist check <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/banlist list [keyword]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/banlist rollback <sender> <time> <unit>"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /banlist help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /banlist help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Banlist Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
