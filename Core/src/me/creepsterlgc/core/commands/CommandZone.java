package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.creepsterlgc.core.utils.TextUtils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;


public class CommandZone implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(args.length > 6) { sender.sendMessage(Texts.of(TextUtils.usage("/zone help"))); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("create")) { new CommandZoneCreate(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("claim")) { new CommandZoneRemove(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("remove")) { new CommandZoneRemove(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandZoneList(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("info")) { new CommandZoneInfo(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("priority")) { new CommandZonePriority(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("member")) { new CommandZoneMember(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("edit")) { new CommandZoneEdit(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Texts.of(TextColors.DARK_AQUA, "Zone Help"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone create <name> [priority]"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone claim <name>"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone remove <name>"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone list [keyword]"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone info <zone>"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone priority <zone> <priority>"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone member <add|remove> <zone> <player> [time] [unit]"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone edit <zone> <setting> <value>"));
		}
		else {
			sender.sendMessage(Texts.of(TextColors.DARK_AQUA, "Zone Help"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone create <name> [priority]"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone claim <name>"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone remove <name>"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone list [keyword]"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone info <zone>"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone priority <zone> <priority>"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone member <add|remove> <zone> <player> [time] [unit]"));
			sender.sendMessage(Texts.of(TextColors.AQUA, "/zone edit <zone> <setting> <value>"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /zone help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /zone help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Zone Command").color(TextColors.YELLOW).build();
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
