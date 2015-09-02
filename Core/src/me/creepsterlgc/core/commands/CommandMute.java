package me.creepsterlgc.core.commands;

import java.util.List;

import me.creepsterlgc.core.customized.COMMAND;
import me.creepsterlgc.core.customized.CONFIG;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MUTE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.TIME;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandMute implements CommandCallable {
	
	public Game game;
	
	public CommandMute(Game game) {
		this.game = game;
	}
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PERMISSIONS.has(sender, "core.mute")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(args.length < 3) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/mute <player> <time> <unit> [reason]")); return CommandResult.success(); }
		
		PLAYER player = DATABASE.getPlayer(DATABASE.getUUID(args[0].toLowerCase()));
		if(player == null) { sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(DATABASE.getBan(player.getUUID()) != null) {
			sender.sendMessage(Texts.builder("Player is already banned!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		double duration = 0;
		
		try { duration = Double.parseDouble(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.builder("<time> has to be a number!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		duration = TIME.toMilliseconds(duration, args[2].toLowerCase());
		
		if(duration == 0) {
			sender.sendMessage(Texts.builder("<unit> has to be: seconds, minutes, hours or days").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		int limit = CONFIG.LIMITS_MAX_MUTE_TIME_IN_SECONDS();
		limit *= 1000;
		
		if(!PERMISSIONS.has(sender, "core.mute.unlimited") && duration > limit) {
			sender.sendMessage(Texts.of(TextColors.RED, "You can only mute for a maximum time of ", limit /= 1000, " seconds!"));
			return CommandResult.success();
		}
		
		duration += System.currentTimeMillis();
		
		String reason = "You got muted!"; if(args.length > 3) reason = COMMAND.combineArgs(3, args);;
		
		MUTE mute = new MUTE(player.getUUID(), duration, reason);
		mute.insert();
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " has been muted for ", TextColors.YELLOW, args[1], " ", args[2].toLowerCase()));
		sender.sendMessage(Texts.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, reason));
		
		return CommandResult.success();
		
	}
	
	 public Text getUsage(CommandSource sender) { return null; }
	 public Optional<Text> getHelp(CommandSource sender) { return null; }
	 public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return null; }
	 public boolean testPermission(CommandSource sender) { return false; }

}
