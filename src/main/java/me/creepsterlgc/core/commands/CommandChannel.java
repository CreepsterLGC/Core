package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;


public class CommandChannel implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(arguments.equalsIgnoreCase("")) {
			sender.sendMessage(Text.of(TextColors.GOLD, "Channel Help"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/c <channel> <message>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/c join <channel>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/c leave <channel>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/c info <channel>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/c list"));
			return CommandResult.success();
		}
		
		if(args[0].equalsIgnoreCase("join")) { new CommandChannelJoin(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("leave")) { new CommandChannelLeave(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("info")) { new CommandChannelInfo(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandChannelList(sender, args); return CommandResult.success(); }
		else {
			new CommandChannelSpeak(sender, args); return CommandResult.success();
		}
		
	}

	private final Text usage = Text.builder("Usage: /channel help").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /channel help").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | Channel Command").color(TextColors.YELLOW).build();
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
