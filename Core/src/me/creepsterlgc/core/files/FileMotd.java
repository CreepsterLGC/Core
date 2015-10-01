package me.creepsterlgc.core.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class FileMotd {
	
	public static File file = new File("config/core/motd.conf");
	public static ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static ConfigurationNode motd = manager.createEmptyNode(ConfigurationOptions.defaults());

	public static void setup() {

		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
				
				List<String> message = new ArrayList<String>();
				message.add("&6Welcome, %player!");
				message.add("&7Make sure to read the &e/rules");
				message.add("&aEnjoy your stay!");
				
				motd.getNode("message").setValue(message);
				motd.getNode("SHOW_ON_JOIN").setValue(true);
				motd.getNode("version").setValue(1);
				
		        manager.save(motd);
				
			}
			
			motd = manager.load();
		     
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static List<String> MESSAGE() { return motd.getNode("message").getList(transform); }
	
	public static Function<Object,String> transform = new Function<Object,String>() {
	    @Override
	    public String apply(Object input) {
	        if (input instanceof String) return (String) input;
	        else return null;
	    }
	};
	
	public static boolean SHOW_ON_JOIN() { return motd.getNode("SHOW_ON_JOIN").getBoolean(); }
	
}
