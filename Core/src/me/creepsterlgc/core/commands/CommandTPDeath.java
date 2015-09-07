package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
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


public class CommandTPDeath implements CommandCallable {
	
	public Game game;
	
	public CommandTPDeath(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PERMISSIONS.has(sender, "core.tpdeath")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(args.length > 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tpdeath [player]")); return CommandResult.success(); }
		
		Player player = (Player) sender;
		
		if(arguments.equalsIgnoreCase("")) {
			
			PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
			
			if(p == null) {
				sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			if(p.getLastdeath().equalsIgnoreCase("")) {
				sender.sendMessage(Texts.builder("No death tracked!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			String world = p.getLastdeath().split(":")[0];
			double x = Double.parseDouble(p.getLastdeath().split(":")[1]);
			double y = Double.parseDouble(p.getLastdeath().split(":")[2]);
			double z = Double.parseDouble(p.getLastdeath().split(":")[3]);
			
			if(!player.transferToWorld(world, new Vector3d(x, y, z))) { sender.sendMessage(Texts.builder("Target world does not exist anymore!").color(TextColors.RED).build()); return CommandResult.success(); }
			
			sender.sendMessage(Texts.of(TextColors.GRAY, "Teleported to ", TextColors.YELLOW, "your", TextColors.GRAY, " last death location."));
			
		}
		else if(args.length == 1) {
			
			if(!PERMISSIONS.has(sender, "core.tpdeath-others")) {
				sender.sendMessage(Texts.builder("You do not have permissions to teleport to other deaths!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			String name = args[0].toLowerCase();
			PLAYER p = DATABASE.getPlayer(DATABASE.getUUID(name));
			
			if(p == null) {
				sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			if(p.getLastdeath().equalsIgnoreCase("")) {
				sender.sendMessage(Texts.builder("No death tracked for that player!").color(TextColors.RED).build());
				return CommandResult.success();
			}
			
			String world = p.getLastdeath().split(":")[0];
			double x = Double.parseDouble(p.getLastdeath().split(":")[1]);
			double y = Double.parseDouble(p.getLastdeath().split(":")[2]);
			double z = Double.parseDouble(p.getLastdeath().split(":")[3]);
			
			if(!player.transferToWorld(world, new Vector3d(x, y, z))) { sender.sendMessage(Texts.builder("Target world does not exist anymore!").color(TextColors.RED).build()); return CommandResult.success(); }
			
			sender.sendMessage(Texts.of(TextColors.GRAY, "Teleported to ", TextColors.YELLOW, p.getName(), "'s", TextColors.GRAY, " last death location."));
			
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /tpdeath [player]").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /tpdeath [player]").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | TPDeath Command").color(TextColors.YELLOW).build();
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
