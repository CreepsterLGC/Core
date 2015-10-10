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


public class CommandSelection implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(args.length > 4) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("expand") || args[0].equalsIgnoreCase("e")) { new CommandSelectionExpand(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("expand")) { new CommandSelectionExpand(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Texts.of(TextColors.DARK_AQUA, "Selection Help"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/s e|expand <range> [direction]"));
		}
		else {
			sender.sendMessage(Texts.of(TextColors.DARK_AQUA, "Selection Help"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/s e|expand <range> [direction]"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /selection help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /selection help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Selection Command").color(TextColors.YELLOW).build();
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
