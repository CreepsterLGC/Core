package me.creepsterlgc.core.customized;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;

public class TEXT {
	
	private Text text;
	
	public TEXT(Text text) {
		this.text = text;
	}
	
	public static TEXT create(String message, int layout) {
		
		TextColor color = null;
		if(layout == 0) color = TextColors.YELLOW;
		if(layout == 1) color = TextColors.GOLD;
		if(layout == 2) color = TextColors.DARK_AQUA;
		if(layout == 3) color = TextColors.DARK_GRAY;
		if(layout == 4) color = TextColors.GRAY;
		if(layout == 5) color = TextColors.AQUA;
		if(layout == 6) color = TextColors.BLUE;
		if(layout == 7) color = TextColors.DARK_GREEN;
		if(layout == 8) color = TextColors.GREEN;
		if(layout == 9) color = TextColors.WHITE;
		if(layout == 10) color = TextColors.RED;
		
		return new TEXT(Texts.builder(message).color(color).build());
		
	}
	
	public TEXT add(String message, int layout) {
		
		TextColor color = null;
		if(layout == 0) color = TextColors.YELLOW;
		if(layout == 1) color = TextColors.GOLD;
		if(layout == 2) color = TextColors.DARK_AQUA;
		if(layout == 3) color = TextColors.DARK_GRAY;
		if(layout == 4) color = TextColors.GRAY;
		if(layout == 5) color = TextColors.AQUA;
		if(layout == 6) color = TextColors.BLUE;
		if(layout == 7) color = TextColors.DARK_GREEN;
		if(layout == 8) color = TextColors.GREEN;
		if(layout == 9) color = TextColors.WHITE;
		if(layout == 10) color = TextColors.RED;
		
		text = Texts.builder().append(text).append(Texts.builder(message).color(color).build()).build();
		
		return this;
		
	}
	
	public Text get() {
		return text;
	}
	
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

}
