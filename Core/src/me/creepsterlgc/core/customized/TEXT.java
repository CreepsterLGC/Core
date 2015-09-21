package me.creepsterlgc.core.customized;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.TextMessageException;

public class TEXT {
	
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
	
	public static String getPrefix(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			return optionSubject.getOption("prefix").or("");
		}
		return "";
	}

	public static String getSuffix(Player player) {
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			return optionSubject.getOption("suffix").or("");
		}
		return "";
	}
	
}
