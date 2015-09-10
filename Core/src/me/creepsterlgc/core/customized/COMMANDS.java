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
				commands.getNode("enabled", "powertool").setValue(true);
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
				commands.getNode("enabled", "tpswap").setValue(true);
				commands.getNode("enabled", "unban").setValue(true);
				commands.getNode("enabled", "unmute").setValue(true);
				commands.getNode("enabled", "warp").setValue(true);
				commands.getNode("enabled", "weather").setValue(true);
				
				commands.getNode("version").setValue(4);
				
		        manager.save(commands);
				
			}
			
			commands = manager.load();
			
			if(commands.getNode("version").getInt() <= 1) {
				
				commands.getNode("enabled", "tpswap").setValue(true);
				commands.getNode("enabled", "powertool").setValue(true);
				
				commands.getNode("version").setValue(4);
				
				manager.save(commands);
				
				commands = manager.load();
				
			}
			
			if(commands.getNode("version").getInt() <= 2) {
				
				commands.getNode("enabled", "tpswap").setValue(true);
				commands.getNode("enabled", "force").setValue(true);
				
				commands.getNode("version").setValue(4);
				
				manager.save(commands);
				
				commands = manager.load();
				
			}
			
			if(commands.getNode("version").getInt() <= 3) {
				
				commands.getNode("enabled", "tpswap").setValue(true);
				
				commands.getNode("version").setValue(4);
				
				manager.save(commands);
				
				commands = manager.load();
				
			}
			
			commands = manager.load();
			
		     
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static boolean AFK() { return commands.getNode("enabled", "afk").getBoolean(); }
	public static boolean BAN() { return commands.getNode("enabled", "ban").getBoolean(); }
	public static boolean BANLIST() { return commands.getNode("enabled", "banlist").getBoolean(); }
	public static boolean BROADCAST() { return commands.getNode("enabled", "broadcast").getBoolean(); }
	public static boolean FEED() { return commands.getNode("enabled", "feed").getBoolean(); }
	public static boolean FORCE() { return commands.getNode("enabled", "force").getBoolean(); }
	public static boolean HEAL() { return commands.getNode("enabled", "heal").getBoolean(); }
	public static boolean HOME() { return commands.getNode("enabled", "home").getBoolean(); }
	public static boolean KICK() { return commands.getNode("enabled", "kick").getBoolean(); }
	public static boolean KILL() { return commands.getNode("enabled", "kill").getBoolean(); }
	public static boolean LIST() { return commands.getNode("enabled", "list").getBoolean(); }
	public static boolean MEMORY() { return commands.getNode("enabled", "memory").getBoolean(); }
	public static boolean MSG() { return commands.getNode("enabled", "msg").getBoolean(); }
	public static boolean MUTE() { return commands.getNode("enabled", "mute").getBoolean(); }
	public static boolean PING() { return commands.getNode("enabled", "ping").getBoolean(); }
	public static boolean POWERTOOL() { return commands.getNode("enabled", "powertool").getBoolean(); }
	public static boolean REPLY() { return commands.getNode("enabled", "reply").getBoolean(); }
	public static boolean SPAWN() { return commands.getNode("enabled", "spawn").getBoolean(); }
	public static boolean TEMPBAN() { return commands.getNode("enabled", "tempban").getBoolean(); }
	public static boolean TICKET() { return commands.getNode("enabled", "ticket").getBoolean(); }
	public static boolean TIME() { return commands.getNode("enabled", "time").getBoolean(); }
	public static boolean TP() { return commands.getNode("enabled", "tp").getBoolean(); }
	public static boolean TPA() { return commands.getNode("enabled", "tpa").getBoolean(); }
	public static boolean TPACCEPT() { return commands.getNode("enabled", "tpaccept").getBoolean(); }
	public static boolean TPAHERE() { return commands.getNode("enabled", "tpahere").getBoolean(); }
	public static boolean TPDEATH() { return commands.getNode("enabled", "tpdeath").getBoolean(); }
	public static boolean TPDENY() { return commands.getNode("enabled", "tpdeny").getBoolean(); }
	public static boolean TPHERE() { return commands.getNode("enabled", "tphere").getBoolean(); }
	public static boolean TPSWAP() { return commands.getNode("enabled", "tpswap").getBoolean(); }
	public static boolean UNBAN() { return commands.getNode("enabled", "unban").getBoolean(); }
	public static boolean UNMUTE() { return commands.getNode("enabled", "unmute").getBoolean(); }
	public static boolean WARP() { return commands.getNode("enabled", "warp").getBoolean(); }
	public static boolean WEATHER() { return commands.getNode("enabled", "weather").getBoolean(); }
	
}
