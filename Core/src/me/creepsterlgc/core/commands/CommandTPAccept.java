package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
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


public class CommandTPAccept implements CommandCallable {
	
	public Game game;
	
	public CommandTPAccept(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PERMISSIONS.has(sender, "core.tpaccept")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tpaccept <player>")); return CommandResult.success(); }
		
		if(args.length < 1 || args.length > 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tpaccept <player>")); return CommandResult.success(); }
		
		Player player = (Player) sender;
		Player target = SERVER.getPlayer(args[0]);
		
		if(target == null) {
			sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		String uuid = target.getUniqueId().toString();
		
		PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
		HashMap<String, Double> tpa = p.getTPA();
		HashMap<String, Double> tpahere = p.getTPAHere();
		
		double duration = 0;
		if(tpa.containsKey(uuid)) duration = tpa.get(uuid);
		if(duration != 0) {
			if(duration <= System.currentTimeMillis()) {
				tpa.remove(uuid);
				p.setTPA(tpa);
			}
		}
		
		duration = 0;
		
		if(tpahere.containsKey(uuid)) duration = tpahere.get(uuid);
		if(duration != 0) {
			if(duration <= System.currentTimeMillis()) {
				tpahere.remove(uuid);
				p.setTPAHere(tpahere);
			}
		}
		
		if(!tpa.containsKey(uuid) && !tpahere.containsKey(uuid)) {
			sender.sendMessage(Texts.builder("There is no pending request from that player!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, target.getName(), "'s ", TextColors.GRAY, "teleport request has been accepted."));
		
		if(tpa.containsKey(uuid)) {
			
			target.transferToWorld(player.getWorld().getName(), new Vector3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
			
			tpa.remove(uuid);
			p.setTPA(tpa);
		}
		
		if(tpahere.containsKey(uuid)) {
			
			player.transferToWorld(target.getWorld().getName(), new Vector3d(target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ()));
			
			tpahere.remove(uuid);
			p.setTPA(tpahere);
		}
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /tpaccept <player>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /tpaccept <player>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | TPAccept Command").color(TextColors.YELLOW).build();
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
