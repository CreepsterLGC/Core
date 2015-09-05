package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.PERMISSIONS;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandList implements CommandCallable {
	
	public Game game;
	
	public CommandList(Game game) {
		this.game = game;
	}
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if(!PERMISSIONS.has(sender, "core.list")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		StringBuilder list = new StringBuilder();
		for(Player p : Controller.getServer().getOnlinePlayers()) list.append(p.getName() + ", ");
		if(list.toString().contains(", ")) list.deleteCharAt(list.length() - 2);
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, "Currently online: ", TextColors.WHITE, list.toString()));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /afk").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /afk").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | AFK Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
