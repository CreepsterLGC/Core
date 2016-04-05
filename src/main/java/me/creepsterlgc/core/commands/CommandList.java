package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.files.FileConfig;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TextUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandList implements CommandCallable {

	public Game game;

	public CommandList(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		if(!PermissionsUtils.has(sender, "core.list")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		int online = Controller.getServer().getOnlinePlayers().size();
		int max = Controller.getServer().getMaxPlayers();

		sender.sendMessage(Text.of(TextColors.GRAY, "There are currently ", TextColors.GOLD, online, TextColors.GRAY, "/", TextColors.GOLD, max, TextColors.GRAY, " players online."));

		if(!FileConfig.LIST_ORDER_BY_GROUPS()) {

			StringBuilder list = new StringBuilder();

			for(Player p : Controller.getServer().getOnlinePlayers()) {
				if(FileConfig.LIST_SHOW_PREFIX()) list.append(PermissionsUtils.getPrefix(p));
				list.append(p.getName());
				if(FileConfig.LIST_SHOW_SUFFIX()) list.append(PermissionsUtils.getSuffix(p));
				list.append("&7, ");
			}

			if(list.toString().contains(", ")) list.deleteCharAt(list.length() - 2);
			Text list_final = TextUtils.color(list.toString());
			sender.sendMessage(list_final);
			return CommandResult.success();

		}
		else {

			HashMap<String, List<Player>> list = new HashMap<String, List<Player>>();

			for(Player p : Controller.getServer().getOnlinePlayers()) {

		    	Subject subject = p.getContainingCollection().get(p.getIdentifier());

				if(subject instanceof OptionSubject) {
					OptionSubject option = (OptionSubject) subject;
					for(Subject group : option.getParents()) {
						List<Player> players = new ArrayList<Player>();
						if(list.containsKey(group.getIdentifier())) players = list.get(group.getIdentifier());
						if(!players.contains(p)) players.add(p);
						list.put(group.getIdentifier(), players);
					}
				}
				else {
					List<Player> players = new ArrayList<Player>();
					if(list.containsKey("Others")) players = list.get("Others");
					if(!players.contains(p)) players.add(p);
					list.put("Others", players);
				}

			}

			for(Entry<String, List<Player>> e : list.entrySet()) {
				StringBuilder sb = new StringBuilder();
				sb.append("&e" + e.getKey() + "&7: ");
				for(Player p : e.getValue()) {
					if(FileConfig.LIST_SHOW_PREFIX()) sb.append(PermissionsUtils.getPrefix(p));
					sb.append(p.getName());
					if(FileConfig.LIST_SHOW_SUFFIX()) sb.append(PermissionsUtils.getSuffix(p));
					sb.append("&7, ");
				}
				if(sb.toString().contains(", ")) sb.deleteCharAt(sb.length() - 2);
				Text list_final = TextUtils.color(sb.toString());
				sender.sendMessage(list_final);
			}

		}

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /afk").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /afk").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | AFK Command").color(TextColors.YELLOW).build();
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
