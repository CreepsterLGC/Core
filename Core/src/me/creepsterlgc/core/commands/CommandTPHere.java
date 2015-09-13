package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.SERVER;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;


public class CommandTPHere implements CommandCallable {
	
	public Game game;
	
	public CommandTPHere(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PERMISSIONS.has(sender, "core.tphere")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tphere <player>")); return CommandResult.success(); }
		if(args.length > 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tphere <player>")); return CommandResult.success(); }
		
		Player player = (Player)sender;
		Player target = SERVER.getPlayer(args[0]);
		
		if(target == null) {
			sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		target.transferToWorld(player.getWorld().getName(), new Vector3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Teleported ", TextColors.YELLOW, target.getName(), TextColors.GRAY, " to ", TextColors.YELLOW, "you"));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /tphere <player>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /tphere <player>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | TPHere Command").color(TextColors.YELLOW).build();
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
