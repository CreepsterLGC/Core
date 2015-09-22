package me.creepsterlgc.core.customized;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class CHAT {
	
	public static File file = new File("mods/Core/Chat.conf");
	public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static ConfigurationNode chat = manager.createEmptyNode(ConfigurationOptions.defaults());

	public static void setup() {

		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
				
				chat.getNode("chat", "use").setValue(true);
				chat.getNode("chat", "channels").setValue(true);
				chat.getNode("chat", "defaultchannel").setValue("global");
				chat.getNode("chat", "defaultformat").setValue("%prefix%player%suffix&8: &f%message");
				chat.getNode("chat", "nickprefix").setValue("&7*");
				
				chat.getNode("channels", "local", "name").setValue("&eLocal");
				chat.getNode("channels", "local", "trigger").setValue("l");
				chat.getNode("channels", "local", "prefix").setValue("&7[&eLocal&7] ");
				chat.getNode("channels", "local", "suffix").setValue("");
				chat.getNode("channels", "local", "format").setValue("%cprefix%prefix&7%player%suffix%csuffix&8: &f%message");
				chat.getNode("channels", "local", "range").setValue("100");
				
				chat.getNode("channels", "world", "name").setValue("&eWorld");
				chat.getNode("channels", "world", "trigger").setValue("w");
				chat.getNode("channels", "world", "prefix").setValue("");
				chat.getNode("channels", "world", "suffix").setValue("");
				chat.getNode("channels", "world", "format").setValue("&7[&e%world&7] %prefix&7%player%suffix&8: &7%message");
				chat.getNode("channels", "world", "range").setValue("world");
				
				chat.getNode("channels", "global", "name").setValue("&6Global");
				chat.getNode("channels", "global", "trigger").setValue("g");
				chat.getNode("channels", "global", "prefix").setValue("&7[&6G&7] ");
				chat.getNode("channels", "global", "suffix").setValue("");
				chat.getNode("channels", "global", "format").setValue("%cprefix%prefix&7%player%suffix%csuffix&8: &7%message");
				chat.getNode("channels", "global", "range").setValue("global");
				
				chat.getNode("channels", "staff", "name").setValue("&3Staff");
				chat.getNode("channels", "staff", "trigger").setValue("s");
				chat.getNode("channels", "staff", "prefix").setValue("&7[&3Staff&7] ");
				chat.getNode("channels", "staff", "suffix").setValue("");
				chat.getNode("channels", "staff", "format").setValue("%cprefix%prefix&7%player%suffix%csuffix&8: &b%message");
				chat.getNode("channels", "staff", "range").setValue("global");
				
				chat.getNode("channels", "help", "name").setValue("&2Help");
				chat.getNode("channels", "help", "trigger").setValue("h");
				chat.getNode("channels", "help", "prefix").setValue("&7[&2Help&7] ");
				chat.getNode("channels", "help", "suffix").setValue("");
				chat.getNode("channels", "help", "format").setValue("%cprefix%prefix&7%player%suffix%csuffix&8: &a%message");
				chat.getNode("channels", "help", "range").setValue("global");
				
				chat.getNode("version").setValue(1);
				
		        manager.save(chat);
				
			}
			
			chat = manager.load();
			
			if(chat.getNode("channels").hasMapChildren()) {
				for(Entry<Object, ? extends ConfigurationNode> e : chat.getNode("channels").getChildrenMap().entrySet()) {
					
					String channel = e.getKey().toString();
					
					String name = chat.getNode("channels", channel, "name").getString();
					String trigger = chat.getNode("channels", channel, "trigger").getString();
					String prefix = chat.getNode("channels", channel, "prefix").getString();
					String suffix = chat.getNode("channels", channel, "suffix").getString();
					String format = chat.getNode("channels", channel, "format").getString();
					String range = chat.getNode("channels", channel, "range").getString();
					
					CHANNEL c = new CHANNEL(channel, trigger, name, prefix, suffix, format, range);
					DATABASE.addChannel(channel, c);
					
				}
			}
			
		     
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static boolean USE() { return chat.getNode("chat", "use").getBoolean(); }
	public static boolean CHANNELS() { return chat.getNode("chat", "channels").getBoolean(); }
	public static String DEFAULTCHANNEL() { return chat.getNode("chat", "defaultchannel").getString(); }
	public static String DEFAULTFORMAT() { return chat.getNode("chat", "defaultformat").getString(); }
	public static String NICKPREFIX() { return chat.getNode("chat", "nickprefix").getString(); }
	
}
