package main.java.me.creepsterlgc.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.command.CommandSource;

public class PermissionsUtils {

	public static boolean has(CommandSource sender, String permission) {
		return sender.hasPermission(permission);
	}

	public static boolean has(Player player, String permission) {
		return player.hasPermission(permission);
	}

	public static String getPrefix(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			return optionSubject.getOption("prefix").orElse("");
		}
		return "";
	}

	public static List<String> getGroups(Player player) {

		List<String> groups = new ArrayList<String>();
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());

		if(subject instanceof OptionSubject) {
			OptionSubject option = (OptionSubject) subject;
			for(Subject group : option.getParents()) {
				if(!groups.contains(group.getIdentifier())) groups.add(group.getIdentifier());
			}
		}
		return groups;
	}

	public static String getSuffix(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			return optionSubject.getOption("suffix").orElse("");
		}
		return "";
	}

	public static int getHomeLimit(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			String o = optionSubject.getOption("homes").orElse("");
			if(o.equalsIgnoreCase("") || o.equalsIgnoreCase("unlimited")) return -1;
			int h;
			try { h = Integer.parseInt(o); }
			catch(NumberFormatException e) { return 0; }
			return h;
		}
		return 0;
	}

	public static int getWarpLimit(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			String o = optionSubject.getOption("warps").orElse("");
			if(o.equalsIgnoreCase("") || o.equalsIgnoreCase("unlimited")) return -1;
			int w;
			try { w = Integer.parseInt(o); }
			catch(NumberFormatException e) { return 0; }
			return w;
		}
		return 0;
	}

	public static int getTempbanLimit(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			String o = optionSubject.getOption("tempban").orElse("");
			if(o.equalsIgnoreCase("") || o.equalsIgnoreCase("unlimited")) return -1;
			int w;
			try { w = Integer.parseInt(o); }
			catch(NumberFormatException e) { return 0; }
			return w;
		}
		return 0;
	}

	public static int getMuteLimit(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			String o = optionSubject.getOption("mute").orElse("");
			if(o.equalsIgnoreCase("") || o.equalsIgnoreCase("unlimited")) return -1;
			int w;
			try { w = Integer.parseInt(o); }
			catch(NumberFormatException e) { return 0; }
			return w;
		}
		return 0;
	}

	public static int getClaimZonesLimit(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			String o = optionSubject.getOption("claim-zones").orElse("");
			if(o.equalsIgnoreCase("") || o.equalsIgnoreCase("unlimited")) return -1;
			int w;
			try { w = Integer.parseInt(o); }
			catch(NumberFormatException e) { return 0; }
			return w;
		}
		return 0;
	}

	public static int getClaimBlocksLimit(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			String o = optionSubject.getOption("claim-blocks").orElse("");
			if(o.equalsIgnoreCase("") || o.equalsIgnoreCase("unlimited")) return -1;
			int w;
			try { w = Integer.parseInt(o); }
			catch(NumberFormatException e) { return 0; }
			return w;
		}
		return 0;
	}

	public static int getClaimTotalLimit(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			String o = optionSubject.getOption("claim-total").orElse("");
			if(o.equalsIgnoreCase("") || o.equalsIgnoreCase("unlimited")) return -1;
			int w;
			try { w = Integer.parseInt(o); }
			catch(NumberFormatException e) { return 0; }
			return w;
		}
		return 0;
	}

}
