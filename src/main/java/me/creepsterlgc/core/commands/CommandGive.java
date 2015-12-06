package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.utils.CommandUtils;
import main.java.me.creepsterlgc.core.utils.ItemUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.ServerUtils;
import main.java.me.creepsterlgc.core.utils.TextUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandGive implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "core.give")) { sender.sendMessage(TextUtils.permissions()); return CommandResult.success(); }

		if(args.length < 2 || args.length > 3) { sender.sendMessage(TextUtils.usage("/give <player> <item> [amount]")); return CommandResult.success(); }

		Player player = ServerUtils.getPlayer(args[0].toLowerCase());
		if(player == null) {
			sender.sendMessage(TextUtils.error("Player not found!"));
			return CommandResult.success();
		}

		ItemType type = ItemUtils.getType(args[1]);
		if(type == null) {
			sender.sendMessage(TextUtils.error("Item not found!"));
			return CommandResult.success();
		}

		int amount = 0;

		if(args.length == 3) {
			if(!CommandUtils.isInt(args[2])) {
				sender.sendMessage(TextUtils.error("<amount> has to be a number!"));
				return CommandResult.success();
			}
			amount = CommandUtils.getInt(args[2]);
		}

		ItemStack item = ItemUtils.build(type, amount);

		ItemUtils.drop(item, player);

		player.sendMessage(Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has given you ", TextColors.YELLOW, amount, " ", args[1].toLowerCase(), "(s)"));
		sender.sendMessage(Texts.of(TextColors.GRAY, "You gave ", TextColors.YELLOW, amount, " ", args[1].toLowerCase(), "(s)", TextColors.GRAY, " to ", TextColors.YELLOW, player.getName()));

		return CommandResult.success();

	}

	private final Text usage = Texts.builder("Usage: /i <item> [amount]").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /i <item> [amount]").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Item Command").color(TextColors.YELLOW).build();
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
