package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.utils.CommandUtils;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;


public class CommandKick implements CommandCallable {
	
	public Game game;
	
	public CommandKick(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PermissionsUtils.has(sender, "core.kick")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(!game.getServer().getPlayer(args[0]).isPresent()) { sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		String reason = "You have been kicked!"; if(args.length > 1) reason = CommandUtils.combineArgs(1, args);
		
		Player player = game.getServer().getPlayer(args[0]).get();
		player.kick(Texts.of(TextColors.RED, reason));
		
		Controller.broadcast(Texts.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " has been kicked by ", TextColors.YELLOW, sender.getName()));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /kick <player> [reason]").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /kick <player> [reason]").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Kick Command").color(TextColors.YELLOW).build();
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
