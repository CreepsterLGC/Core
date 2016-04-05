package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandBanlist implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(args.length > 4) { sender.sendMessage(usage); return CommandResult.success(); }

		if(args[0].equalsIgnoreCase("check")) { new CommandBanlistCheck(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandBanlistList(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("rollback")) { new CommandBanlistRollback(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Text.of(TextColors.GOLD, "Banlist Help"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/banlist check <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/banlist list [keyword]"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/banlist rollback <sender> <time> <unit>"));
		}
		else {
			sender.sendMessage(Text.of(TextColors.GOLD, "Banlist Help"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/banlist check <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/banlist list [keyword]"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/banlist rollback <sender> <time> <unit>"));
		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /banlist help").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /banlist help").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | Banlist Command").color(TextColors.YELLOW).build();
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
