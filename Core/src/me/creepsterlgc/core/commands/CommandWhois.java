package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.CONFIG;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.SERVER;
import me.creepsterlgc.core.customized.TEXT;
import me.creepsterlgc.core.customized.TIME;

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

import com.google.common.base.Optional;


public class CommandWhois implements CommandCallable {
	
	public Game game;
	
	public CommandWhois(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PERMISSIONS.has(sender, "core.whois")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/whois <player>")); return CommandResult.success(); }
		if(args.length > 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/whois <player>")); return CommandResult.success(); }
		
		Player player = SERVER.getPlayer(args[0].toLowerCase());
		
		if(player == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
			return CommandResult.success();
		}
		
		PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
		
		Location<World> loc = player.getLocation();
		
		double x = Math.round(loc.getX() * 100); x /= 100;
		double y = Math.round(loc.getY() * 100); y /= 100;
		double z = Math.round(loc.getZ() * 100); z /= 100;
		
		String nick = "- none -";
		if(!p.getNick().equalsIgnoreCase("")) nick = p.getNick();
		
		String afk = "no";
		if(p.getAFK()) afk = "since " + TIME.toString(System.currentTimeMillis() - p.getLastaction() - CONFIG.AFK_TIMER_IN_SECONDS() * 1000);
		
		sender.sendMessage(Texts.of(TextColors.GOLD, "Whois ", player.getName()));
		sender.sendMessage(Texts.of(TextColors.YELLOW, "Nick: ", TextColors.GRAY, TEXT.color(nick)));
		sender.sendMessage(Texts.of(TextColors.YELLOW, "AFK: ", TextColors.WHITE, afk));
		sender.sendMessage(Texts.of(TextColors.YELLOW, "Location: ", TextColors.WHITE, "x:", x, " y:", y, " z:", z, " world:", loc.getExtent().getName()));
		if(PERMISSIONS.has(sender, "core.whois-ip")) sender.sendMessage(Texts.of(TextColors.YELLOW, "IP: ", TextColors.WHITE, player.getConnection().getAddress().getAddress().getHostAddress().toString()));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /whois <player>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /whois <player>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Whois Command").color(TextColors.YELLOW).build();
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
