package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.PERMISSIONS;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandSpawn implements CommandCallable {

	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(args.length > 2) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("create")) { new CommandSpawnCreate(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("remove")) { new CommandSpawnRemove(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandSpawnRemove(sender, args); return CommandResult.success(); }
		else {
			new CommandSpawnTeleport(sender, args);
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/spawn [create|remove|list] [name]");
	private final Text help = Texts.of(TextColors.YELLOW, "Help: ", TextColors.GRAY, "/spawn [create|remove|list] [name]");
	private final Text description = Texts.builder("Core | Spawn Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
