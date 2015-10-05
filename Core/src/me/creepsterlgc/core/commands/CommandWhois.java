package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreServer;
import me.creepsterlgc.core.files.FileConfig;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.TimeUtils;
import me.creepsterlgc.core.utils.TextUtils;

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


public class CommandWhois implements CommandCallable {
	
	public Game game;
	
	public CommandWhois(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PermissionsUtils.has(sender, "core.whois")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/whois <player>")); return CommandResult.success(); }
		if(args.length > 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/whois <player>")); return CommandResult.success(); }
		
		Player player = CoreServer.getPlayer(args[0].toLowerCase());
		
		if(player == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
			return CommandResult.success();
		}
		
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		
		Location<World> loc = player.getLocation();
		
		double x = Math.round(loc.getX() * 100); x /= 100;
		double y = Math.round(loc.getY() * 100); y /= 100;
		double z = Math.round(loc.getZ() * 100); z /= 100;
		
		String nick = "- none -";
		if(!p.getNick().equalsIgnoreCase("")) nick = p.getNick();
		
		String afk = "no";
		if(p.getAFK()) afk = "since " + TimeUtils.toString(System.currentTimeMillis() - p.getLastaction() - FileConfig.AFK_TIMER_IN_SECONDS() * 1000);
		
		sender.sendMessage(Texts.of(TextColors.GOLD, "Whois ", player.getName()));
		sender.sendMessage(Texts.of(TextColors.YELLOW, "Nick: ", TextColors.GRAY, TextUtils.color(nick)));
		sender.sendMessage(Texts.of(TextColors.YELLOW, "AFK: ", TextColors.WHITE, afk));
		sender.sendMessage(Texts.of(TextColors.YELLOW, "Location: ", TextColors.WHITE, "x:", x, " y:", y, " z:", z, " world:", loc.getExtent().getName()));
		if(PermissionsUtils.has(sender, "core.whois-ip")) sender.sendMessage(Texts.of(TextColors.YELLOW, "IP: ", TextColors.WHITE, player.getConnection().getAddress().getAddress().getHostAddress().toString()));
		
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
