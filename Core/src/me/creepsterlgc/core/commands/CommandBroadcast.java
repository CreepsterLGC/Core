package me.creepsterlgc.core.commands;

import java.util.List;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.COMMAND;
import me.creepsterlgc.core.customized.PERMISSIONS;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandBroadcast implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PERMISSIONS.has(sender, "core.broadcast")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/broadcast <message>")); return CommandResult.success(); }
		if(args.length < 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/broadcast <message>")); return CommandResult.success(); }
		
		String message = COMMAND.combineArgs(0, args);
		
		Controller.broadcast(Texts.of(TextColors.GOLD, "Server", TextColors.GRAY, ": ", TextColors.WHITE, message));
		
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
