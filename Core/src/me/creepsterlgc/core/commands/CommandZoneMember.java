package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreZone;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandZoneMember {

	public CommandZoneMember(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 4 || args.length > 6) { sender.sendMessage(Texts.of(TextColors.AQUA, "Usage: ", TextColors.GRAY, "/zone member <add|remove> <zone> <member>")); return; }
	
		if(!args[1].equalsIgnoreCase("add") && args[2].equalsIgnoreCase("remove")) { sender.sendMessage(Texts.of(TextColors.AQUA, "Usage: ", TextColors.GRAY, "/zone member <add|remove> <zone> <member> [time] [unit]")); return; }
		
		if(args.length == 5) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/zone member <add|remove> <zone> <member> [time] [unit]")); return; }
		
		Player player = (Player) sender;
	
		CoreZone zone = CoreDatabase.getZone(args[2].toLowerCase());
		
		if(zone == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Zone not found!"));
			return;
		}
		
		CorePlayer member = CoreDatabase.getPlayer(CoreDatabase.getUUID(args[3].toLowerCase()));
		
		if(member == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
			return;
		}

		double duration = 0;
		
		if(args.length == 6) {

			try { duration = Double.parseDouble(args[4]); }
			catch(NumberFormatException e) {
				sender.sendMessage(Texts.builder("<time> has to be a number!").color(TextColors.RED).build());
				return;
			}
			
			duration = TimeUtils.toMilliseconds(duration, args[5].toLowerCase());
			
			if(duration == 0) {
				sender.sendMessage(Texts.builder("<unit> has to be: seconds, minutes, hours or days").color(TextColors.RED).build());
				return;
			}
			
			duration += System.currentTimeMillis();
			
		}
		
		if(args[1].equalsIgnoreCase("add")) {
			
			if(!PermissionsUtils.has(player, "core.zone.member.add")) {
				sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions!"));
				return;
			}
			
			if(!zone.isOwner(player) && !PermissionsUtils.has(player, "core.zone.member.add-others")) {
				sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions"));
				return;
			}
			
			if(zone.isMember(member.getUUID())) {
				sender.sendMessage(Texts.of(TextColors.RED, member.getName() + " is already a member!"));
				return;
			}
			
			zone.addMember(member.getUUID(), duration);
			zone.update();
			
			sender.sendMessage(Texts.of(TextColors.AQUA, member.getName(), TextColors.GRAY, " is now a member of zone ", TextColors.AQUA, zone.getName()));
			
		}
		
		else if(args[1].equalsIgnoreCase("remove")) {
			
			if(!PermissionsUtils.has(player, "core.zone.member.remove")) {
				sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions!"));
				return;
			}
			
			if(!zone.isOwner(player) && !PermissionsUtils.has(player, "core.zone.member.remove-others")) {
				sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions"));
				return;
			}
			
			if(!zone.isMember(member.getUUID())) {
				sender.sendMessage(Texts.of(TextColors.RED, member.getName() + " is not a member!"));
				return;
			}
			
			zone.removeMember(member.getUUID());
			zone.update();
			
			sender.sendMessage(Texts.of(TextColors.AQUA, member.getName(), TextColors.GRAY, " is no longer a member of zone ", TextColors.AQUA, zone.getName()));
			
		}
		
		
	}

}
