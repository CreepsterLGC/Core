package me.creepsterlgc.core.commands;

import java.util.List;

import me.creepsterlgc.core.customized.CoreServer;
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

import com.google.common.base.Optional;


public class CommandForce implements CommandCallable {
	
	public Game game;
	
	public CommandForce(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PermissionsUtils.has(sender, "core.force")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(args.length < 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/force <player> <command>")); return CommandResult.success(); }
		
		Player player = CoreServer.getPlayer(args[0]);
		if(player == null) {
			sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		if(PermissionsUtils.has(player, "core.force.except") && !PermissionsUtils.has(sender, "core.force.override")) {
			sender.sendMessage(Texts.builder("You cannot force this player!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		String command = CommandUtils.combineArgs(1, args);

		game.getCommandDispatcher().process(player.getCommandSource().get(), command);
		sender.sendMessage(Texts.of(TextColors.GRAY, "Forcing ", TextColors.YELLOW, player.getName(), TextColors.GRAY, " to enter command: ", TextColors.YELLOW, command));
		
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
