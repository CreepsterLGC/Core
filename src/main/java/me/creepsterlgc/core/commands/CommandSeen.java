package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandSeen implements CommandCallable {

	public Game game;

	public CommandSeen(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "core.seen")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/seen <player>")); return CommandResult.success(); }
		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/seen <player>")); return CommandResult.success(); }

		CorePlayer p = CoreDatabase.getPlayer(CoreDatabase.getUUID(args[0].toLowerCase()));
		if(p == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Player not found!"));
			return CommandResult.success();
		}

		sender.sendMessage(Text.of(TextColors.YELLOW, p.getName(), " has been first seen: ", TextColors.GRAY, TimeUtils.toString(System.currentTimeMillis() - p.getFirstseen()), " ago"));

		boolean online = Controller.getServer().getPlayer(args[0].toLowerCase()).isPresent();

		if(online) sender.sendMessage(Text.of(TextColors.YELLOW, p.getName(), " has been last seen: ", TextColors.GREEN, "Currently online!"));
		else sender.sendMessage(Text.of(TextColors.YELLOW, p.getName(), " has been last seen: ", TextColors.GRAY, TimeUtils.toString(System.currentTimeMillis() - p.getLastseen()), " ago"));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /seen <player>").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /seen <player>").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | Seen Command").color(TextColors.YELLOW).build();
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
