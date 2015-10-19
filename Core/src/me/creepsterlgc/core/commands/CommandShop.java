package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;


public class CommandShop implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(arguments.equalsIgnoreCase("")) {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Shop Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/shop create <item> <amount> <buyprice> <sellprice> [player]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/shop remove"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/shop list [player]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/shop rollback <player> <time> <unit>"));
			return CommandResult.success();
		}
		
		if(args[0].equalsIgnoreCase("create")) { new CommandChannelJoin(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("remove")) { new CommandChannelLeave(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandChannelInfo(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("rollback")) { new CommandChannelList(sender, args); return CommandResult.success(); }
		else {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Shop Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/shop create <item> <amount> <buyprice> <sellprice> [player]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/shop remove"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/shop list [player]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/shop rollback <player> <time> <unit>"));
			return CommandResult.success();
		}
		
	}

	private final Text usage = Texts.builder("Usage: /shop help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /shop help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Shop Command").color(TextColors.YELLOW).build();
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
