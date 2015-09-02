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


public class CommandWeather implements CommandCallable {
	
	private Game game;
	
	public CommandWeather(Game game) {
		this.game = game;
	}
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PERMISSIONS.has(sender, "core.weather")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(args.length != 1) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("sun")) { new CommandWeatherSun(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("rain")) { new CommandWeatherRain(sender, args, game); return CommandResult.success(); }
		else {
			sender.sendMessage(usage);
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /weather <sun|rain>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /weather <sun|rain>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Time Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
