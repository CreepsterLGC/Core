package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreZone;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;


public class CommandZoneEdit {

	public CommandZoneEdit(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 4 || args.length > 4) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/zone remove <name> <setting> <value>")); return; }
	
		Player player = (Player) sender;
		
		String name = args[1].toLowerCase();
		
		if(CoreDatabase.getZone(name) == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Zone not found!"));
			return;
		}
		
		CoreZone z = CoreDatabase.getZone(name);
		
		if(!z.isOwner(player) && !PermissionsUtils.has(player, "core.zone.edit-others")) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to edit this zone!"));
			return;
		}
		
		String setting = args[2].toLowerCase();
		String value = args[3].toLowerCase();
		
		if(setting.equalsIgnoreCase("animals")
		|| setting.equalsIgnoreCase("monsters")
		|| setting.equalsIgnoreCase("pvp")
		|| setting.equalsIgnoreCase("build")
		|| setting.equalsIgnoreCase("interact")
		|| setting.equalsIgnoreCase("hunger")
		|| setting.equalsIgnoreCase("invulnerability")) {
			
			if(value.equalsIgnoreCase("allow") || value.equalsIgnoreCase("deny")) {
				
				if(!PermissionsUtils.has(sender, "core.zone.edit." + setting)) {
					sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to modify this setting!"));
					return;
				}
				
				sender.sendMessage(Texts.of(TextColors.GRAY, "Set setting ", TextColors.AQUA, setting, TextColors.GRAY, " to ", TextColors.AQUA, value, TextColors.GRAY, " on zone ", TextColors.DARK_AQUA, args[1]));
				
				boolean state = true;
				if(value.equalsIgnoreCase("deny")) state = false;
				
				if(setting.equalsIgnoreCase("animals")) z.allowAnimalSpawning(state);
				else if(setting.equalsIgnoreCase("monsters")) z.allowMonsterSpawning(state);
				else if(setting.equalsIgnoreCase("pvp")) z.allowPVP(state);
				else if(setting.equalsIgnoreCase("build")) z.allowBuild(state);
				else if(setting.equalsIgnoreCase("interact")) z.allowInteract(state);
				else if(setting.equalsIgnoreCase("hunger")) z.allowHunger(state);
				else if(setting.equalsIgnoreCase("invulnerability")) z.allowInvulnerability(state);
				
				z.update();
				
				return;
			}
			else {
				sender.sendMessage(Texts.of(TextColors.RED, "Allowed values for ", setting, ": allow or deny"));
				return;
			}
			
		}
		else {
			sender.sendMessage(Texts.of(TextColors.RED, "Available settings: animals, monsters, pvp, build, interact, hunger, invulnerability"));
			return;
		}	
		
	}

}
