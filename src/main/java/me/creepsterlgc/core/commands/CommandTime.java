package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandTime implements CommandCallable {

	private Game game;

	public CommandTime(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(usage); return CommandResult.success(); }

		if(args[0].equalsIgnoreCase("day")) { new CommandTimeDay(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("night")) { new CommandTimeNight(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("sunrise")) { new CommandTimeSunrise(sender, args, game); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("sunset")) { new CommandTimeSunset(sender, args, game); return CommandResult.success(); }
		else {
			sender.sendMessage(usage);
		}

		return CommandResult.success();

	}

	private final Text usage = Texts.builder("Usage: /time <day|night|sunrise|sunset> [world]").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /time <day|night|sunrise|sunset> [world]").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Time Command").color(TextColors.YELLOW).build();
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
