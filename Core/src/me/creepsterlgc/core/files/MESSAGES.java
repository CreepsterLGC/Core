package me.creepsterlgc.core.files;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class MESSAGES {
	
	public static File file = new File("config/core/messages.conf");
	public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static ConfigurationNode messages = manager.createEmptyNode(ConfigurationOptions.defaults());

	public static void setup() {

		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
				
				messages.getNode("events", "join", "enable").setValue(true);
				messages.getNode("events", "join", "message").setValue("&e%player &7has joined.");
				
				messages.getNode("events", "leave", "enable").setValue(true);
				messages.getNode("events", "leave", "message").setValue("&e%player &7has left.");
				
				messages.getNode("events", "firstjoin", "enable").setValue(true);
				messages.getNode("events", "firstjoin", "message").setValue("&e%player &7has joined for the first time!");
				messages.getNode("events", "firstjoin", "uniqueplayers", "show").setValue(true);
				messages.getNode("events", "firstjoin", "uniqueplayers", "message").setValue("&e%players &7unique players already joined.");
				
				messages.getNode("version").setValue(1);
				
		        manager.save(messages);
				
			}
			
			messages = manager.load();
			
		     
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static boolean EVENTS_JOIN_ENABLE() { return messages.getNode("events", "join", "enable").getBoolean(); }
	public static String EVENTS_JOIN_MESSAGE() { return messages.getNode("events", "join", "message").getString(); }
	
	public static boolean EVENTS_LEAVE_ENABLE() { return messages.getNode("events", "leave", "enable").getBoolean(); }
	public static String EVENTS_LEAVE_MESSAGE() { return messages.getNode("events", "leave", "message").getString(); }
	
	public static boolean EVENTS_FIRSTJOIN_ENABLE() { return messages.getNode("events", "firstjoin", "enable").getBoolean(); }
	public static String EVENTS_FIRSTJOIN_MESSAGE() { return messages.getNode("events", "firstjoin", "message").getString(); }
	public static boolean EVENTS_FIRSTJOIN_UNIQUEPLAYERS_SHOW() { return messages.getNode("events", "firstjoin", "uniqueplayers", "show").getBoolean(); }
	public static String EVENTS_FIRSTJOIN_UNIQUEPLAYERS_MESSAGE() { return messages.getNode("events", "firstjoin", "uniqueplayers", "message").getString(); }
	
}
