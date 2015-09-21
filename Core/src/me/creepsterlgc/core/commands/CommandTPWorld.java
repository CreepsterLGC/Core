package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.PERMISSIONS;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Optional;


public class CommandTPWorld implements CommandCallable {
	
	public Game game;
	
	public CommandTPWorld(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String args[] = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PERMISSIONS.has(sender, "core.tpworld")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tpworld <world>")); return CommandResult.success(); }		
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tpworld <world>")); return CommandResult.success(); }		
		
		Player player = (Player)sender;
		String name = args[0];

		if(!game.getServer().getWorld(name).isPresent()) {
			sender.sendMessage(Texts.builder("World does not exist!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		if(!game.getServer().getWorld(name).get().getProperties().isEnabled()) {
			sender.sendMessage(Texts.builder("World does not exists!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		World world = game.getServer().getWorld(name).get();

		player.setLocation(world.getSpawnLocation());
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Teleported to world ", TextColors.YELLOW, name));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /tpworld <world>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /tpworld <world>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | TPWorld Command").color(TextColors.YELLOW).build();
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
