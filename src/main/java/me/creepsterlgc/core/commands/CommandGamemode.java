package main.java.me.creepsterlgc.core.commands;

import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandGamemode implements CommandCallable {

	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "core.gamemode")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/gm <mode> [player]")); return CommandResult.success(); }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/gm <mode> [player]")); return CommandResult.success(); }

		int mode;

		try { mode = Integer.parseInt(args[0]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.of(TextColors.RED, "Invalid mode! Please use: 0, 1, 2 or 3"));
			return CommandResult.success();
		}

		GameMode gm;

		if(mode == 0) gm = GameModes.SURVIVAL;
		else if(mode == 1) gm = GameModes.CREATIVE;
		else if(mode == 2) gm = GameModes.ADVENTURE;
		else if(mode == 3) gm = GameModes.SPECTATOR;
		else {
			sender.sendMessage(Texts.of(TextColors.RED, "Invalid mode! Please use: 0, 1, 2 or 3"));
			return CommandResult.success();
		}

		if(args.length == 1) {

			if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

			Player player = (Player)sender;
			player.offer(Keys.GAME_MODE, gm);
			sender.sendMessage(Texts.of(TextColors.GRAY, "Your gamemode has been set to ", TextColors.YELLOW, gm.getName()));

		}

		if(args.length == 2) {

			if(!PermissionsUtils.has(sender, "core.gamemode-others")) {
				sender.sendMessage(Texts.of(TextColors.RED, "You can only change your own gamemode!"));
				return CommandResult.success();
			}

			Player player = ServerUtils.getPlayer(args[1].toLowerCase());
			if(player == null) {
				sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
				return CommandResult.success();
			}

			player.offer(Keys.GAME_MODE, gm);
			player.sendMessage(Texts.of(TextColors.YELLOW, sender.getName(), Texts.of(TextColors.GRAY, " has set your gamemode to ", TextColors.YELLOW, gm.getName())));
			sender.sendMessage(Texts.of(TextColors.YELLOW, player.getName(), "'s", TextColors.GRAY, " gamemode has been set to ", TextColors.YELLOW, gm.getName()));

		}


		return CommandResult.success();

	}

	 public Text getUsage(CommandSource sender) { return null; }
	 public Optional<Text> getHelp(CommandSource sender) { return null; }
	 public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return null; }
	 public boolean testPermission(CommandSource sender) { return false; }

}
