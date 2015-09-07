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


public class CommandMemory implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if(!PERMISSIONS.has(sender, "core.memory")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		Runtime runtime = Runtime.getRuntime();
		
		double memory_max = runtime.maxMemory() / (1024 * 1024);
		double memory_allocated = runtime.totalMemory() / (1024 * 1024);
		double memory_free = runtime.freeMemory() / (1024 * 1024);
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Memory Usage: ", TextColors.YELLOW, memory_allocated, " MB / ", memory_max, " MB ", TextColors.GRAY, "Free: ", TextColors.YELLOW, memory_free, " MB"));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /memory").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /memory").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Memory Command").color(TextColors.YELLOW).build();
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
