package main.java.me.creepsterlgc.core.commands;

import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreBan;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.files.FileConfig;
import main.java.me.creepsterlgc.core.utils.CommandUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandTempban implements CommandCallable {

	public Game game;

	public CommandTempban(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "core.tempban")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length < 4) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tempban <player> <time> <unit> <reason>")); return CommandResult.success(); }

		CorePlayer player = CoreDatabase.getPlayer(CoreDatabase.getUUID(args[0].toLowerCase()));
		if(player == null) { sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(CoreDatabase.getBan(player.getUUID()) != null) {
			sender.sendMessage(Text.builder("Player is already banned!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		double duration = 0;

		try { duration = Double.parseDouble(args[1]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Text.builder("<time> has to be a number!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		duration = TimeUtils.toMilliseconds(duration, args[2].toLowerCase());

		if(duration == 0) {
			sender.sendMessage(Text.builder("<unit> has to be: seconds, minutes, hours or days").color(TextColors.RED).build());
			return CommandResult.success();
		}

		int limit = FileConfig.LIMITS_MAX_TEMPBAN_TIME_IN_SECONDS();
		limit *= 1000;

		if(!PermissionsUtils.has(sender, "core.tempban-unlimited") && duration > limit) {
			sender.sendMessage(Text.of(TextColors.RED, "You can only ban for a maximum time of ", limit /= 1000, " seconds!"));
			return CommandResult.success();
		}

		duration += System.currentTimeMillis();

		String reason = CommandUtils.combineArgs(3, args);

		CoreBan ban = new CoreBan(player.getUUID(), sender.getName().toLowerCase(), reason, System.currentTimeMillis(), duration);
		ban.insert();

		if(Controller.getServer().getPlayer(player.getName()).isPresent()) {
			Player p = Controller.getServer().getPlayer(player.getName()).get();
			p.kick(Text.of(TextColors.RED, "Banned: ", TextColors.GRAY, reason));
			Controller.broadcast(Text.of(TextColors.YELLOW, p.getName(), TextColors.GRAY, " has been temporary banned by ", TextColors.YELLOW, sender.getName()));
			Controller.broadcast(Text.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, reason));
			Controller.broadcast(Text.of(TextColors.YELLOW, "Time: ", TextColors.GRAY, args[1], " ", args[2].toLowerCase()));
			return CommandResult.success();
		}

		Controller.broadcast(Text.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " has been temporary banned by ", TextColors.YELLOW, sender.getName()));
		Controller.broadcast(Text.of(TextColors.YELLOW, "Reason: ", TextColors.GRAY, reason));
		Controller.broadcast(Text.of(TextColors.YELLOW, "Time: ", TextColors.GRAY, args[1], " ", args[2].toLowerCase()));

		return CommandResult.success();

	}

	 @Override
	public Text getUsage(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getHelp(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 @Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return null; }
	 @Override
	public boolean testPermission(CommandSource sender) { return false; }

}
