package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.creepsterlgc.core.customized.CoreBan;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;


public class CommandUnban implements CommandCallable {
	
	public Game game;
	
	public CommandUnban(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PermissionsUtils.has(sender, "core.unban")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(usage); return CommandResult.success(); }
		if(args.length != 1) { sender.sendMessage(usage); return CommandResult.success(); }
		
		CorePlayer player = CoreDatabase.getPlayer(CoreDatabase.getUUID(args[0].toLowerCase()));
		if(player == null) { sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		CoreBan ban = CoreDatabase.getBan(player.getUUID());
		
		if(ban == null) {
			sender.sendMessage(Texts.builder("Player is not banned!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		ban.delete();
		CoreDatabase.removeBan(player.getUUID());
		
		Text t1 = Texts.builder(player.getName()).color(TextColors.YELLOW).build();
		Text t2 = Texts.builder(" has been unbanned by ").color(TextColors.GRAY).build();
		Text t3 = Texts.builder(sender.getName()).color(TextColors.YELLOW).build();
		
		Text total = Texts.builder().append(t1).append(t2).append(t3).build();
		
		game.getServer().getBroadcastSink().sendMessage(total);
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /unban <player>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /unban <player>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Unban Command").color(TextColors.YELLOW).build();
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
