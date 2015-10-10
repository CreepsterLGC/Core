package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreZone;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.SerializeUtils;
import me.creepsterlgc.core.utils.TextUtils;
import me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandZoneInfo {

	public CommandZoneInfo(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(!PermissionsUtils.has(sender, "core.zone.info")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2 || args.length > 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/zone info <name>")); return; }
	
		Player player = (Player) sender;
		
		String name = args[1].toLowerCase();
		
		if(CoreDatabase.getZone(name) == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Zone not found!"));
			return;
		}
		
		CoreZone z = CoreDatabase.getZone(name);
		
		if(!z.isOwner(player) && !PermissionsUtils.has(player, "core.zone.info-others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to view this zone!"));
			return;
		}
		
		StringBuilder m = new StringBuilder();
		String members = SerializeUtils.members(z.getMembers());
		if(members.equalsIgnoreCase("")) m.append("&7- none -");
		else {
			for(String member : members.split("-;;")) {
				CorePlayer p = CoreDatabase.getPlayer(members.split(":")[0]); if(p == null) continue;
				if(!z.isMember(p.getUUID())) continue;
				double time = Double.parseDouble(member.split(":")[1]);
				String a = "oo"; if(time > 0) a = TimeUtils.toString(time - System.currentTimeMillis());
				m.append("&e" + p.getName() + " &7(" + a + ")&f, ");
			}
			for(int i = 1; i <= 2; i++) m.deleteCharAt(m.length() - 1);
		}
		
		String settings = SerializeUtils.settings(z.getSettings());
		settings = settings.replaceAll("-;;", ", ").replaceAll(":", ": ");
		if(settings.equalsIgnoreCase("")) settings = "- none -";
		
		sender.sendMessage(Texts.of(TextColors.GRAY, "Information on Zone: ", TextColors.DARK_AQUA, z.getName()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "World: ", TextColors.BLUE, z.getWorld()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "x1: ", TextColors.BLUE, z.getX1(), TextColors.GRAY, " y1: ", TextColors.BLUE, z.getY1(), TextColors.GRAY, " z1: ", TextColors.BLUE, z.getZ1()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "x2: ", TextColors.BLUE, z.getX2(), TextColors.GRAY, " y2: ", TextColors.BLUE, z.getY2(), TextColors.GRAY, " z2: ", TextColors.BLUE, z.getZ2()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Priority: ", TextColors.AQUA, z.getPriority()));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Owner: ", TextColors.YELLOW, CoreDatabase.getPlayer(z.getOwner()).getName()));
		if(members.equalsIgnoreCase("- none -")) sender.sendMessage(Texts.of(TextColors.GRAY, "Members: ", TextColors.AQUA, TextUtils.color(m.toString())));
		else sender.sendMessage(Texts.of(TextColors.GRAY, "Members: ", TextColors.WHITE, "(", z.getMembers().size(), ") ", TextUtils.color(m.toString())));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Settings: ", TextColors.WHITE, settings));
		
	}

}
