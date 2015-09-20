package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.SERVER;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import com.google.common.base.Optional;


public class CommandTPPos implements CommandCallable {
	
	public Game game;
	
	public CommandTPPos(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PERMISSIONS.has(sender, "core.tppos")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tppos [player] <x> <y> <z>")); return CommandResult.success(); }
		
		if(args.length < 3 || args.length > 4) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tppos [player] <x> <y> <z>")); return CommandResult.success(); }
		
		if(args.length == 3) {
			
			if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
			
			Player player = (Player) sender;
			
			double x;
			double y;
			double z;
			
			try { x = Double.parseDouble(args[0]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Texts.builder("<x> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			try { y = Double.parseDouble(args[1]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Texts.builder("<y> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			try { z = Double.parseDouble(args[2]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Texts.builder("<z> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			game.getCommandDispatcher().process(game.getServer().getConsole(), "minecraft:tp " + player.getName() + " " + String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(z));
			player.sendMessage(Texts.of(TextColors.GRAY, "Teleported to ", TextColors.YELLOW, "x:", x, " y:", y, " z:", z));
			
		}
		else if(args.length == 4) {
			
			if(!PERMISSIONS.has(sender, "core.tppos-others")) {
				sender.sendMessage(Texts.builder("You do not have permissions to teleport others!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			Player player = SERVER.getPlayer(args[0].toLowerCase());
			if(player == null) {
				sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			double x;
			double y;
			double z;
			
			try { x = Double.parseDouble(args[1]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Texts.builder("<x> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			try { y = Double.parseDouble(args[2]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Texts.builder("<y> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			try { z = Double.parseDouble(args[3]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Texts.builder("<z> has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			game.getCommandDispatcher().process(game.getServer().getConsole(), "minecraft:tp " + player.getName() + " " + String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(z));
			player.sendMessage(Texts.of(TextColors.GRAY, "Teleported ", TextColors.YELLOW, player.getName(), TextColors.GRAY, " to ", TextColors.YELLOW, "x:", x, " y:", y, " z:", z));
			
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /tppos [player] <x> <y> <z>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /tppos [player] <x> <y> <z>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | TPPos Command").color(TextColors.YELLOW).build();
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
