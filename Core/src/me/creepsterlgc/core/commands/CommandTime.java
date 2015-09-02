package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.PERMISSIONS;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandTime implements CommandCallable {
	
	private Game game;
	
	public CommandTime(Game game) {
		this.game = game;
	}
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PERMISSIONS.has(sender, "core.time")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(args.length != 1) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("day")) { new CommandTimeDay(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("night")) { new CommandTimeNight(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("sunrise")) { new CommandTimeSunrise(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("sunset")) { new CommandTimeSunset(sender, args, game); return CommandResult.success(); }
		else {
			sender.sendMessage(usage);
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /time <day|night|sunrise|sunset>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /time <day|night|sunrise|sunset>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Time Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
