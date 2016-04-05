package main.java.me.creepsterlgc.core.utils;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {
	
	public static Text color(String message) {
		Text result = Text.of();
    	result = Text.builder().append(TextSerializers.formattingCode('&').deserialize(message)).build();
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
		return Text.of(TextColors.RED, message);
	}
	
	public static Text permissions() {
		return Text.of(TextColors.RED, "You do not have permissions!");
	}
	
	public static Text usage(String message) {
		return Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, message);
	}
	
}
