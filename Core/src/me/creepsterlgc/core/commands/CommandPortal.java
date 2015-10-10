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


public class CommandPortal implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(args[0].equalsIgnoreCase("create")) { new CommandPortalCreate(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("remove")) { new CommandPortalRemove(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandPortalList(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Portal Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/portal create <name> <zone> <warp>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/portal remove <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/portal list [keyword]"));
		}
		else {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Portal Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/portal create <name> <zone> <warp>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/portal remove <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/portal list [keyword]"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /portal help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /portal help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Portal Command").color(TextColors.YELLOW).build();
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
