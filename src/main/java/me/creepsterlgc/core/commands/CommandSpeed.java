package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.utils.CommandUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.ServerUtils;
import main.java.me.creepsterlgc.core.utils.TextUtils;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandSpeed implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "core.speed")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/speed [player] <multiplier>")); return CommandResult.success(); }
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/speed [player] <multiplier>")); return CommandResult.success(); }

		if(args.length == 1) {

			if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

			Player p = (Player) sender;

			if(!CommandUtils.isDouble(args[0])) {
				sender.sendMessage(TextUtils.error("<multiplier> has to be a number between 1 and 10!"));
				return CommandResult.success();
			}

			double m = CommandUtils.getDouble(args[0]);

			if(m < 1 || m > 10) {
				sender.sendMessage(TextUtils.error("<multiplier> has to be a number between 1 and 10!"));
				return CommandResult.success();
			}

			p.offer(Keys.WALKING_SPEED, 0.1 * m);
			p.offer(Keys.FLYING_SPEED, 0.05 * m);

			sender.sendMessage(Text.of(TextColors.GRAY, "Speed has been set to: ", TextColors.GOLD, m));

		}
		else if(args.length == 2) {

			if(!PermissionsUtils.has(sender, "core.speed-others")) {
				sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			Player p = ServerUtils.getPlayer(args[0]);
			if(p == null) {
				sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			if(!CommandUtils.isDouble(args[1])) {
				sender.sendMessage(TextUtils.error("<multiplier> has to be a number between 1 and 10!"));
				return CommandResult.success();
			}

			double m = CommandUtils.getDouble(args[1]);

			if(m < 1 || m > 10) {
				sender.sendMessage(TextUtils.error("<multiplier> has to be a number between 1 and 10!"));
				return CommandResult.success();
			}

			p.offer(Keys.WALKING_SPEED, 0.1 * m);
			p.offer(Keys.FLYING_SPEED, 0.05 * m);

			p.sendMessage(Text.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " has set your speed to: ", TextColors.GOLD, m));
			sender.sendMessage(Text.of(TextColors.YELLOW, p.getName(), "'s", TextColors.GRAY, " speed has been set to: ", TextColors.GOLD, m));

		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /speed [player] <multiplier>").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /speed [player] <multiplier>").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | Speed Command").color(TextColors.YELLOW).build();
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
