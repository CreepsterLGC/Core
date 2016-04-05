package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;


public class CommandTPDeath implements CommandCallable {

	public Game game;

	public CommandTPDeath(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(!PermissionsUtils.has(sender, "core.tpdeath")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length > 1) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tpdeath [player]")); return CommandResult.success(); }

		Player player = (Player) sender;

		if(arguments.equalsIgnoreCase("")) {

			CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

			if(p == null) {
				sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			if(p.getLastdeath().equalsIgnoreCase("")) {
				sender.sendMessage(Text.builder("No death tracked!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			String world = p.getLastdeath().split(":")[0];
			double x = Double.parseDouble(p.getLastdeath().split(":")[1]);
			double y = Double.parseDouble(p.getLastdeath().split(":")[2]);
			double z = Double.parseDouble(p.getLastdeath().split(":")[3]);

			Location<World> loc = new Location<World>(Controller.getServer().getWorld(world).get(), x, y, z);

			player.setLocation(loc);

			sender.sendMessage(Text.of(TextColors.GRAY, "Teleported to ", TextColors.YELLOW, "your", TextColors.GRAY, " last death location."));

		}
		else if(args.length == 1) {

			if(!PermissionsUtils.has(sender, "core.tpdeath-others")) {
				sender.sendMessage(Text.builder("You do not have permissions to teleport to other deaths!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			String name = args[0].toLowerCase();
			CorePlayer p = CoreDatabase.getPlayer(CoreDatabase.getUUID(name));

			if(p == null) {
				sender.sendMessage(Text.builder("Player not found!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			if(p.getLastdeath().equalsIgnoreCase("")) {
				sender.sendMessage(Text.builder("No death tracked for that player!").color(TextColors.RED).build());
				return CommandResult.success();
			}

			String world = p.getLastdeath().split(":")[0];
			double x = Double.parseDouble(p.getLastdeath().split(":")[1]);
			double y = Double.parseDouble(p.getLastdeath().split(":")[2]);
			double z = Double.parseDouble(p.getLastdeath().split(":")[3]);

			if(!player.transferToWorld(world, new Vector3d(x, y, z))) { sender.sendMessage(Text.builder("Target world does not exist anymore!").color(TextColors.RED).build()); return CommandResult.success(); }

			sender.sendMessage(Text.of(TextColors.GRAY, "Teleported to ", TextColors.YELLOW, p.getName(), "'s", TextColors.GRAY, " last death location."));

		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /tpdeath [player]").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /tpdeath [player]").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | TPDeath Command").color(TextColors.YELLOW).build();
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
