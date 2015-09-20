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

import com.google.common.base.Optional;


public class CommandTPAHere implements CommandCallable {
	
	public Game game;
	
	public CommandTPAHere(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PERMISSIONS.has(sender, "core.tpahere")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tpahere <player>")); return CommandResult.success(); }
		
		if(args.length < 1 || args.length > 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/tpahere <player>")); return CommandResult.success(); }
		
		Player s = (Player) sender;
		String uuid = s.getUniqueId().toString();
		
		Player player = SERVER.getPlayer(args[0]);
		
		if(player == null) {
			sender.sendMessage(Texts.builder("Player not found!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
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
			else {
				sender.sendMessage(Texts.builder("You already requested a teleport from that player!").color(TextColors.RED).build());
				return CommandResult.success();
			}
		}
		
		duration = 0;
		
		if(tpahere.containsKey(uuid)) duration = tpahere.get(uuid);
		if(duration != 0) {
			if(duration <= System.currentTimeMillis()) {
				tpahere.remove(uuid);
				p.setTPAHere(tpahere);
			}
			else {
				sender.sendMessage(Texts.builder("You already requested a teleport from that player!").color(TextColors.RED).build());
				return CommandResult.success();
			}
		}
		
		duration = System.currentTimeMillis() + 30 * 1000;
		
		tpahere.put(uuid, duration);
		p.setTPAHere(tpahere);
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Teleport request has been sent to ", TextColors.YELLOW, player.getName()));
		player.sendMessage(Texts.of(TextColors.YELLOW, sender.getName(), TextColors.GRAY, " is aking you to teleport to him/her."));
		player.sendMessage(Texts.of(TextColors.GRAY, "Type ", TextColors.YELLOW, "/tpaccept ", sender.getName(), TextColors.GRAY, " or", TextColors.YELLOW, " /tpdeny ", sender.getName()));
		player.sendMessage(Texts.of(TextColors.GRAY, "to accept/decline the request."));		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /tpahere <player>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /tpahere <player>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | TPAHere Command").color(TextColors.YELLOW).build();
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
