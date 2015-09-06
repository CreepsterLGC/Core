package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandWarp implements CommandCallable {
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(args.length == 0 || args.length > 3) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if(args[0].equalsIgnoreCase("create")) { new CommandWarpCreate(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("remove")) { new CommandWarpRemove(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("list")) { new CommandWarpList(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("info")) { new CommandWarpInfo(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("private")) { new CommandWarpPrivate(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("public")) { new CommandWarpPublic(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("invite")) { new CommandWarpInvite(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("uninvite")) { new CommandWarpUninvite(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Warp Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp create <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp remove <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp list [keyword]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp info <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp private <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp public <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp invite <name> <player>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp uninvite <name> <player>"));
		}
		else if(!arguments.equalsIgnoreCase("") && args.length == 1) {
			new CommandWarpTeleport(sender, args); return CommandResult.success();
		}
		else {
			sender.sendMessage(Texts.of(TextColors.GOLD, "Warp Help"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp create <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp remove <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp list [keyword]"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp info <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp private <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp public <name>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp invite <name> <player>"));
			sender.sendMessage(Texts.of(TextColors.YELLOW, "/warp uninvite <name> <player>"));
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /warp help").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /warp help").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Warp Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
