package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class CommandButcher implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(!PermissionsUtils.has(sender, "core.butcher")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length < 1) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/butcher [radius]")); return CommandResult.success(); }

		Player player = (Player) sender;
		World world = player.getWorld();

		int radius = 0;

		if(!arguments.equalsIgnoreCase("")) {
			try { radius = Integer.parseInt(args[0]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Text.builder("[radius] has to be a number!").color(TextColors.RED).build());
				return CommandResult.success();
			}
		}

		if(radius <= 0) radius = 0;

		int c = 0;
		for(Entity e : world.getEntities()) {
			if(e instanceof Animal == false && e instanceof Monster == false && !e.getType().equals(EntityTypes.BAT)) continue;
			if(radius != 0) {
				Location<World> l = e.getLocation();
				double x = player.getLocation().getX();
				double z = player.getLocation().getZ();
				boolean hit_x = false;
				boolean hit_z = false;
				if(l.getX() <= x + radius && l.getX() >= x - radius) hit_x = true;
				if(l.getZ() <= z + radius && l.getZ() >= z - radius) hit_z = true;
				if(!hit_x || !hit_z) continue;
			}
			e.remove();
			c += 1;
		}

		sender.sendMessage(Text.of(TextColors.GRAY, "Removed ", TextColors.YELLOW, c, TextColors.GRAY, " entities."));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /butcher [radius]").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /butcher [radius]").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | Butcher Command").color(TextColors.YELLOW).build();
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
