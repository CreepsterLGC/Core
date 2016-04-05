package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandWarp implements CommandCallable {

	@Override
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
		else if(args[0].equalsIgnoreCase("move")) { new CommandWarpMove(sender, args); return CommandResult.success(); }
		else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Text.of(TextColors.GOLD, "Warp Help"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp create <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp remove <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp list [keyword]"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp info <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp <private|public> <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp <invite|uninvite> <name> <player>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp move <name>"));
		}
		else if(!arguments.equalsIgnoreCase("") && args.length == 1) {
			new CommandWarpTeleport(sender, args); return CommandResult.success();
		}
		else {
			sender.sendMessage(Text.of(TextColors.GOLD, "Warp Help"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp create <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp remove <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp list [keyword]"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp info <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp <private|public> <name>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp <invite|uninvite> <name> <player>"));
			sender.sendMessage(Text.of(TextColors.YELLOW, "/warp move <name>"));
		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /warp help").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /warp help").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | Warp Command").color(TextColors.YELLOW).build();
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
