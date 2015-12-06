package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandSearchitem implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "core.searchitem")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/searchitem <name>")); return CommandResult.success(); }

		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/searchitem <name>")); return CommandResult.success(); }

		String name = args[0].toLowerCase();

		StringBuilder sb = new StringBuilder();


		for(ItemType t : Controller.getGame().getRegistry().getAllOf(ItemType.class)) {
			if(!t.getName().contains(name)) continue;
			sb.append(t.getName() + ", ");
		}

		if(sb.toString().contains(", ")) {
			for(int i = 1; i <= 2; i++) sb.deleteCharAt(sb.length() - 1);
			sender.sendMessage(Texts.of(TextColors.YELLOW, "Found: ", TextColors.GRAY, sb.toString()));
		}
		else {
			sender.sendMessage(Texts.of(TextColors.RED, "No items found!"));
		}

		return CommandResult.success();

	}

	private final Text usage = Texts.builder("Usage: /searchitem <name>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /searchitem <name>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Searchitem Command").color(TextColors.YELLOW).build();
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
