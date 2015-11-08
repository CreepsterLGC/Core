package me.creepsterlgc.core.utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.command.CommandSource;

public class CommandUtils {
	
	public static String combineArgs(int start, String[] args) {
		if(args.length < start) return "";
        StringBuilder combined = new StringBuilder();
        for(int i = start; i < args.length; i++) combined.append(args[i] + " ");
        combined.deleteCharAt(combined.length()-1);
		return combined.toString();
	}
	
	public static boolean isPlayer(CommandSource source) {
		return source instanceof Player;
	}
	
	public static boolean isInt(String arg) {
		try { Integer.parseInt(arg); }
		catch(NumberFormatException e) { return false; }
		return true;
	}
	
	public static int getInt(String arg) {
		try { return Integer.parseInt(arg); }
		catch(NumberFormatException e) { return 0; }
	}
	
	public static boolean isDouble(String arg) {
		try { Double.parseDouble(arg); }
		catch(NumberFormatException e) { return false; }
		return true;
	}
	
	public static double getDouble(String arg) {
		try { return Double.parseDouble(arg); }
		catch(NumberFormatException e) { return 0; }
	}
	
}
