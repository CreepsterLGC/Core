package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import me.creepsterlgc.core.Controller;
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


public class CommandRealname implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PERMISSIONS.has(sender, "core.realname")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/realname <player>")); return CommandResult.success(); }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/realname <player>")); return CommandResult.success(); }
		
		String s = args[0].toLowerCase();
		
		boolean found = false;
		
		for(Player player : Controller.getServer().getOnlinePlayers())	{
			PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
			if(p.getNick().equalsIgnoreCase("")) continue;
			
			String f = p.getNick();
			f = f
			.replaceAll("&a", "")
			.replaceAll("&b", "")
			.replaceAll("&c", "")
			.replaceAll("&d", "")
			.replaceAll("&e", "")
			.replaceAll("&f", "")
			.replaceAll("&0", "")
			.replaceAll("&1", "")
			.replaceAll("&2", "")
			.replaceAll("&3", "")
			.replaceAll("&4", "")
			.replaceAll("&5", "")
			.replaceAll("&6", "")
			.replaceAll("&7", "")
			.replaceAll("&8", "")
			.replaceAll("&9", "")
			.replaceAll("&l", "")
			.replaceAll("&o", "")
			.replaceAll("&m", "")
			.replaceAll("&n", "")
			.replaceAll("&k", "")
			.toLowerCase();
			
			if(s.equalsIgnoreCase(f)) {
				sender.sendMessage(Texts.of(TextColors.YELLOW, TEXT.color(p.getNick()), TextColors.GRAY, " is ", player.getName()));
				found = true;
			}
			
		}
		
		if(found == false) sender.sendMessage(Texts.of(TextColors.RED, "No match found!"));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /realname <player>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /realname <player>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Realname Command").color(TextColors.YELLOW).build();
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
