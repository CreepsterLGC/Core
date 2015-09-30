package me.creepsterlgc.core.utils;

import java.util.List;

public class SerializeUtils {

	public static String list(List<String> list) {
		
		if(list.isEmpty()) return "";
		
		StringBuilder serialized = new StringBuilder();
		for(String s : list) serialized.append(s + ",");
		serialized.deleteCharAt(serialized.length() - 1);
		
		return serialized.toString();
		
	}
	
	public static String location(String world, double x, double y, double z, double yaw, double pitch) {
		return world + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
	}
	
	public static String messages(List<String> messages) {
		
		if(messages.isEmpty()) return "";
		
		StringBuilder serialized = new StringBuilder();
		for(String s : messages) serialized.append(s + "-;;");
		for(int i = 1; i <= 3; i++) serialized.deleteCharAt(serialized.length() - 1);
		
		return serialized.toString();
		
	}
	
}
