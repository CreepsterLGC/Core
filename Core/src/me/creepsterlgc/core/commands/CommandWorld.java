package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandWorld implements CommandCallable {
	
	public Game game;
	
	public CommandWorld(Game game) {
		this.game = game;
	}
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(arguments.equalsIgnoreCase("")) {
			sender.sendMessage(Texts.of(TextColors.GOLD, "World Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world create <name> <environment> <gamemode>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world remove <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world list"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world load <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world edit <world> <setting> <value>"));
			return CommandResult.success();
		}
		
		if(args.length == 0 || args.length > 4) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("create")) { new CommandWorldCreate(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("remove")) { new CommandWorldRemove(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandWorldList(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("load")) { new CommandWorldLoad(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("edit")) { new CommandWorldEdit(sender, args, game); return CommandResult.success(); }
		else {
			sender.sendMessage(Texts.of(TextColors.GOLD, "World Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world create <name> <environment> <gamemode>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world remove <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world list"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world load <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/world edit <world> <setting> <value>"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /world help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /world help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | World Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
