package me.creepsterlgc.core.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;

public class TextUtils {
	
	public static Text color(String message) {
		Text result = Texts.of();
    	try {
    		result = Texts.legacy('&').from(message);
		} catch (TextMessageException e) {
			System.out.println("Core: Error while formatting chat message!");
			e.printStackTrace();
		}
    	return result;
	}
	
	public static String uncolor(String message) {
		return message
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
		.replaceAll("&k", "");
	}
	
	public static Text error(String message) {
		return Texts.of(TextColors.RED, message);
	}
	
	public static Text permissions() {
		return Texts.of(TextColors.RED, "You do not have permissions!");
	}
	
	public static Text usage(String message) {
		return Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, message);
	}
	
}
