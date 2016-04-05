package main.java.me.creepsterlgc.core.customized;

import java.util.HashMap;

public class CoreChannels {

	private static HashMap<String, CoreChannel> channels = new HashMap<String, CoreChannel>();
	public static void add(String name, CoreChannel channel) { if(!channels.containsKey(name)) channels.put(name, channel); }
	public static void remove(String name) { if(channels.containsKey(name)) channels.remove(name); }
	public static CoreChannel get(String name) { return channels.containsKey(name) ? channels.get(name) : null; }
	public static HashMap<String, CoreChannel> all() { return channels; }
	
}
