package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.utils.CommandUtils;
import main.java.me.creepsterlgc.core.utils.ItemUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TextUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandItem implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!CommandUtils.isPlayer(sender)) { sender.sendMessage(TextUtils.error("Cannot be run by the console!")); return CommandResult.success(); }

		if(!PermissionsUtils.has(sender, "core.item")) { sender.sendMessage(TextUtils.permissions()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(TextUtils.usage("/i <item> [amount]")); return CommandResult.success(); }
		if(args.length < 1 || args.length > 2) { sender.sendMessage(TextUtils.usage("/i <item> [amount]")); return CommandResult.success(); }

		Player player = (Player) sender;

		ItemType type = ItemUtils.getType(args[0]);
		if(type == null) {
			sender.sendMessage(TextUtils.error("Item not found!"));
			return CommandResult.success();
		}

		int amount = 1;

		if(args.length == 2) {
			if(!CommandUtils.isInt(args[1])) {
				sender.sendMessage(TextUtils.error("<amount> has to be a number!"));
				return CommandResult.success();
			}
			amount = CommandUtils.getInt(args[1]);
		}

		ItemStack item = ItemUtils.build(type, amount);

		ItemUtils.drop(item, player);

		sender.sendMessage(Text.of(TextColors.GRAY, "Added ", TextColors.YELLOW, amount, " ", args[0].toLowerCase(), "(s)"));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /i <item> [amount]").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /i <item> [amount]").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | Item Command").color(TextColors.YELLOW).build();
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
