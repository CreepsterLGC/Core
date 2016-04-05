package main.java.me.creepsterlgc.core.commands;

import java.util.HashMap;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreHome;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandHomeDelete {

	public CommandHomeDelete(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "core.home.delete")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/home delete [name]")); return; }

		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

		String name = "default"; if(args.length == 2) name = args[1].toLowerCase();
		CoreHome home = p.getHome(name);

		if(home == null) { sender.sendMessage(Text.builder("Home does not exist!").color(TextColors.RED).build()); return; }

		home.delete();

		HashMap<String, CoreHome> homes = p.getHomes();
		homes.remove(name);
		p.setHomes(homes);

		sender.sendMessage(Text.of(TextColors.GRAY, "Home ", TextColors.YELLOW, name, TextColors.GRAY, " has been removed."));

	}

}
