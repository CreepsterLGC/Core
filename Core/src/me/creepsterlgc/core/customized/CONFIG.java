package me.creepsterlgc.core.customized;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class CONFIG {
	
	public static File file = new File("mods/Core/Core.conf");
	public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static ConfigurationNode config = manager.createEmptyNode(ConfigurationOptions.defaults());

	public static void setup() {

		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
				
				config.getNode("mysql", "use").setValue(false);
				config.getNode("mysql", "host").setValue("localhost");
				config.getNode("mysql", "port").setValue(3306);
				config.getNode("mysql", "username").setValue("root");
				config.getNode("mysql", "password").setValue("password");
				config.getNode("mysql", "database").setValue("minecraft");
				
				config.getNode("chat", "use").setValue(true);
				config.getNode("chat", "format").setValue("%prefix%player%suffix&8: &f");
				
				config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").setValue(3600);
				config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").setValue(600);
				
				config.getNode("afk", "ENABLE_SYSTEM").setValue(true);
				config.getNode("afk", "TIMER_IN_SECONDS").setValue(180);
				config.getNode("afk", "KICK_ENABLE").setValue(false);
				config.getNode("afk", "KICK_AFTER").setValue(300);
				
				config.getNode("list", "ORDER_BY_GROUPS").setValue(true);
				config.getNode("list", "SHOW_PREFIX").setValue(true);
				config.getNode("list", "SHOW_SUFFIX").setValue(true);
				
				config.getNode("version").setValue(6);
				
		        manager.save(config);
				
			}
			
	        config = manager.load();
			
			if(config.getNode("version").getInt() <= 4) {
				
				config.getNode("list", "ORDER_BY_GROUPS").setValue(true);
				config.getNode("list", "SHOW_PREFIX").setValue(true);
				config.getNode("list", "SHOW_SUFFIX").setValue(true);
				
				config.getNode("afk", "ENABLE_SYSTEM").setValue(true);
				
				config.getNode("version").setValue(6);
				
				manager.save(config);
				
		        config = manager.load();
				
			}
			
			if(config.getNode("version").getInt() <= 5) {
				
				config.getNode("list", "ORDER_BY_GROUPS").setValue(true);
				config.getNode("list", "SHOW_PREFIX").setValue(true);
				config.getNode("list", "SHOW_SUFFIX").setValue(true);
				
				config.getNode("afk", "ENABLE_SYSTEM").setValue(true);
				
				config.getNode("version").setValue(6);
				
				manager.save(config);
				
		        config = manager.load();
				
			}
			
		     
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static boolean MYSQL_USE() { return config.getNode("mysql", "use").getBoolean(); }
	public static String MYSQL_HOST() { return config.getNode("mysql", "host").getString(); }
	public static int MYSQL_PORT() { return config.getNode("mysql", "port").getInt(); }
	public static String MYSQL_USERNAME() { return config.getNode("mysql", "username").getString(); }
	public static String MYSQL_PASSWORD() { return config.getNode("mysql", "password").getString(); }
	public static String MYSQL_DATABASE() { return config.getNode("mysql", "database").getString(); }
	
	public static int LIMITS_MAX_TEMPBAN_TIME_IN_SECONDS() { return config.getNode("limits", "MAX_TEMPBAN_TIME_IN_SECONDS").getInt(); }
	public static int LIMITS_MAX_MUTE_TIME_IN_SECONDS() { return config.getNode("limits", "MAX_MUTE_TIME_IN_SECONDS").getInt(); }

	public static boolean AFK_ENABLE_SYSTEM() { return config.getNode("afk", "ENABLE_SYSTEM").getBoolean(); }
	public static double AFK_TIMER_IN_SECONDS() { return config.getNode("afk", "TIMER_IN_SECONDS").getDouble(); }
	public static boolean AFK_KICK_ENABLE() { return config.getNode("afk", "KICK_ENABLE").getBoolean(); }
	public static double AFK_KICK_AFTER() { return config.getNode("afk", "KICK_AFTER").getDouble(); }
	
	public static boolean CHAT_USE() { return config.getNode("chat", "use").getBoolean(); }
	public static String CHAT_FORMAT() { return config.getNode("chat", "format").getString(); }
	
	public static boolean LIST_ORDER_BY_GROUPS() { return config.getNode("list", "ORDER_BY_GROUPS").getBoolean(); }
	public static boolean LIST_SHOW_PREFIX() { return config.getNode("list", "SHOW_PREFIX").getBoolean(); }
	public static boolean LIST_SHOW_SUFFIX() { return config.getNode("list", "SHOW_SUFFIX").getBoolean(); }
	
	
}
