package me.creepsterlgc.core.customized;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class COMMANDS {
	
	public static File file = new File("mods/Core/Commands.conf");
	public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static ConfigurationNode commands = manager.createEmptyNode(ConfigurationOptions.defaults());

	public static void setup() {

		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
				
				commands.getNode("enabled", "afk").setValue(true);
				commands.getNode("enabled", "ban").setValue(true);
				commands.getNode("enabled", "banlist").setValue(true);
				commands.getNode("enabled", "broadcast").setValue(true);
				commands.getNode("enabled", "feed").setValue(true);
				commands.getNode("enabled", "heal").setValue(true);
				commands.getNode("enabled", "home").setValue(true);
				commands.getNode("enabled", "kick").setValue(true);
				commands.getNode("enabled", "kill").setValue(true);
				commands.getNode("enabled", "list").setValue(true);
				commands.getNode("enabled", "memory").setValue(true);
				commands.getNode("enabled", "msg").setValue(true);
				commands.getNode("enabled", "mute").setValue(true);
				commands.getNode("enabled", "ping").setValue(true);
				commands.getNode("enabled", "reply").setValue(true);
				commands.getNode("enabled", "spawn").setValue(true);
				commands.getNode("enabled", "tempban").setValue(true);
				commands.getNode("enabled", "ticket").setValue(true);
				commands.getNode("enabled", "time").setValue(true);
				commands.getNode("enabled", "tp").setValue(true);
				commands.getNode("enabled", "tpa").setValue(true);
				commands.getNode("enabled", "tpaccept").setValue(true);
				commands.getNode("enabled", "tpahere").setValue(true);
				commands.getNode("enabled", "tpdeny").setValue(true);
				commands.getNode("enabled", "tpdeath").setValue(true);
				commands.getNode("enabled", "tphere").setValue(true);
				commands.getNode("enabled", "unban").setValue(true);
				commands.getNode("enabled", "unmute").setValue(true);
				commands.getNode("enabled", "warp").setValue(true);
				commands.getNode("enabled", "weather").setValue(true);
				
				commands.getNode("version").setValue(1);
				
		        manager.save(commands);
				
			}
			
			commands = manager.load();
			
		     
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static boolean COMMANDS_AFK() { return commands.getNode("enabled", "afk").getBoolean(); }
	public static boolean COMMANDS_BAN() { return commands.getNode("enabled", "ban").getBoolean(); }
	public static boolean COMMANDS_BANLIST() { return commands.getNode("enabled", "banlist").getBoolean(); }
	public static boolean COMMANDS_BROADCAST() { return commands.getNode("enabled", "broadcast").getBoolean(); }
	public static boolean COMMANDS_FEED() { return commands.getNode("enabled", "feed").getBoolean(); }
	public static boolean COMMANDS_HEAL() { return commands.getNode("enabled", "heal").getBoolean(); }
	public static boolean COMMANDS_HOME() { return commands.getNode("enabled", "home").getBoolean(); }
	public static boolean COMMANDS_KICK() { return commands.getNode("enabled", "kick").getBoolean(); }
	public static boolean COMMANDS_KILL() { return commands.getNode("enabled", "kill").getBoolean(); }
	public static boolean COMMANDS_LIST() { return commands.getNode("enabled", "list").getBoolean(); }
	public static boolean COMMANDS_MEMORY() { return commands.getNode("enabled", "memory").getBoolean(); }
	public static boolean COMMANDS_MSG() { return commands.getNode("enabled", "msg").getBoolean(); }
	public static boolean COMMANDS_MUTE() { return commands.getNode("enabled", "mute").getBoolean(); }
	public static boolean COMMANDS_PING() { return commands.getNode("enabled", "ping").getBoolean(); }
	public static boolean COMMANDS_REPLY() { return commands.getNode("enabled", "reply").getBoolean(); }
	public static boolean COMMANDS_SPAWN() { return commands.getNode("enabled", "spawn").getBoolean(); }
	public static boolean COMMANDS_TEMPBAN() { return commands.getNode("enabled", "tempban").getBoolean(); }
	public static boolean COMMANDS_TICKET() { return commands.getNode("enabled", "ticket").getBoolean(); }
	public static boolean COMMANDS_TIME() { return commands.getNode("enabled", "time").getBoolean(); }
	public static boolean COMMANDS_TP() { return commands.getNode("enabled", "tp").getBoolean(); }
	public static boolean COMMANDS_TPA() { return commands.getNode("enabled", "tpa").getBoolean(); }
	public static boolean COMMANDS_TPACCEPT() { return commands.getNode("enabled", "tpaccept").getBoolean(); }
	public static boolean COMMANDS_TPAHERE() { return commands.getNode("enabled", "tpahere").getBoolean(); }
	public static boolean COMMANDS_TPDEATH() { return commands.getNode("enabled", "tpdeath").getBoolean(); }
	public static boolean COMMANDS_TPDENY() { return commands.getNode("enabled", "tpdeny").getBoolean(); }
	public static boolean COMMANDS_TPHERE() { return commands.getNode("enabled", "tphere").getBoolean(); }
	public static boolean COMMANDS_UNBAN() { return commands.getNode("enabled", "unban").getBoolean(); }
	public static boolean COMMANDS_UNMUTE() { return commands.getNode("enabled", "unmute").getBoolean(); }
	public static boolean COMMANDS_WARP() { return commands.getNode("enabled", "warp").getBoolean(); }
	public static boolean COMMANDS_WEATHER() { return commands.getNode("enabled", "weather").getBoolean(); }
	
}
