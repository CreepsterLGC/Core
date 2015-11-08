package main.java.me.creepsterlgc.core.commands;

import java.util.HashMap;
import java.util.Map.Entry;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreBan;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandBanlistRollback {

	public CommandBanlistRollback(CommandSource sender, String[] args) {
		
		if(!PermissionsUtils.has(sender, "core.banlist.rollback")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 4 || args.length > 4) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/banlist rollback <sender> <time> <unit>")); return; }
		
		if(CoreDatabase.getWarps().isEmpty()) { sender.sendMessage(Texts.builder("The banlist is empty.").color(TextColors.YELLOW).build()); return; }
		
		String name = args[1].toLowerCase();
		
		double duration = 0;
		
		try { duration = Double.parseDouble(args[2]); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.builder("<time> has to be a number!").color(TextColors.RED).build());
			return;
		}
		
		duration = TimeUtils.toMilliseconds(duration, args[3].toLowerCase());
		
		if(duration == 0) {
			sender.sendMessage(Texts.builder("<unit> has to be: seconds, minutes, hours or days").color(TextColors.RED).build());
			return;
		}
		
		double time = System.currentTimeMillis() - duration;
		
		int counter = 0;
		HashMap<String, CoreBan> bans = CoreDatabase.getBans();
		
		for(Entry<String, CoreBan> e : bans.entrySet()) {
			CoreBan ban = e.getValue();
			if(ban.getSender().equalsIgnoreCase(name) && ban.getTime() >= time) { ban.delete(); CoreDatabase.removeBan(ban.getUUID()); counter += 1; }
		}
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, counter, TextColors.GRAY, " bans have been removed."));
		
	}

}
