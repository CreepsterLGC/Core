package me.creepsterlgc.core.commands;

import java.util.List;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.BAN;
import me.creepsterlgc.core.customized.COMMAND;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
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


public class CommandBan implements CommandCallable {
	
	public Game game;
	
	public CommandBan(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PERMISSIONS.has(sender, "core.ban")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(args.length < 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/ban <player> <reason>")); return CommandResult.success(); }
		
		PLAYER player = DATABASE.getPlayer(DATABASE.getUUID(args[0].toLowerCase()));
		if(player == null) { sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(DATABASE.getBan(player.getUUID()) != null) {
			sender.sendMessage(Texts.builder("Player is already banned!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		double duration = 0;
		
		String reason = COMMAND.combineArgs(1, args);
		
		BAN ban = new BAN(player.getUUID(), sender.getName().toLowerCase(), reason, System.currentTimeMillis(), duration);
		ban.insert();
		
		if(Controller.getServer().getPlayer(player.getName()).isPresent()) {
			Player p = Controller.getServer().getPlayer(player.getName()).get();
			p.kick(Texts.of(TextColors.RED, "Banned: ", TextColors.GRAY, reason));
			Controller.broadcast(Texts.of(TextColors.YELLOW, p.getName(), TextColors.GRAY, " has been banned by ", TextColors.YELLOW, sender.getName()));
			Controller.broadcast(Texts.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, reason));
			return CommandResult.success();
		}
		
		Controller.broadcast(Texts.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " has been banned by ", TextColors.YELLOW, sender.getName()));
		Controller.broadcast(Texts.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, reason));
		
		return CommandResult.success();
		
	}
	
	 @Override
	public Text getUsage(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getHelp(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 @Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return null; }
	 @Override
	public boolean testPermission(CommandSource sender) { return false; }

}
