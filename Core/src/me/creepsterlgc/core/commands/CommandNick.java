package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.TEXT;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandNick implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PERMISSIONS.has(sender, "core.nick")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/nick [player] <name|clear>")); return CommandResult.success(); }
		
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/nick [player] <name|clear>")); return CommandResult.success(); }
		
		Player player = (Player) sender;
		PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("clear")) {
				
				p.setNick("");
				p.update();
				
				sender.sendMessage(Texts.of(TextColors.GRAY, "Your nickname has been cleared."));
				
				return CommandResult.success();
				
			}
			
			if(args[0].contains("&") && !PERMISSIONS.has(sender, "core.nick-color")) {
				sender.sendMessage(Texts.of(TextColors.RED, "You are not permitted to use color codes!"));
				return CommandResult.success();
			}
			
			p.setNick(args[0]);
			p.update();
			
			sender.sendMessage(Texts.of(TextColors.GRAY, "Your nickname has been set to: ", TEXT.color(args[0])));
			
			return CommandResult.success();
			
		}
		
		if(!PERMISSIONS.has(sender, "core.nick-others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You can only change your own nick!"));
			return CommandResult.success();
		}
		
		PLAYER t = DATABASE.getPlayer(DATABASE.getUUID(args[0].toLowerCase()));
		if(t == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
			return CommandResult.success();
		}
		
		if(args[1].equalsIgnoreCase("clear")) {
			
			t.setNick("");
			t.update();
			
			sender.sendMessage(Texts.of(TextColors.GRAY, t.getName(), "'s nickname has been cleared."));
			
			return CommandResult.success();
			
		}
		
		if(args[1].contains("&") && !PERMISSIONS.has(sender, "core.nick-color")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You are not permitted to use color codes!"));
			return CommandResult.success();
		}
		
		t.setNick(args[1]);
		t.update();
		
		sender.sendMessage(Texts.of(TextColors.GRAY, t.getName(), "'s nickname has been set to: ", TEXT.color(args[1])));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /nick [player] <nick|clear>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /nick [player] <nick|clear>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Nick Command").color(TextColors.YELLOW).build();
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
