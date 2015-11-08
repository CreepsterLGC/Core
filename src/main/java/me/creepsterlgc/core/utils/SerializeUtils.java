package me.creepsterlgc.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class SerializeUtils {

	public static String list(List<String> list) {
		
		if(list.isEmpty()) return "";
		
		StringBuilder serialized = new StringBuilder();
		for(String s : list) serialized.append(s + ",");
		serialized.deleteCharAt(serialized.length() - 1);
		
		return serialized.toString();
		
	}
	
	public static String messages(List<String> messages) {
		
		if(messages.isEmpty()) return "";
		
		StringBuilder serialized = new StringBuilder();
		for(String s : messages) serialized.append(s + "-;;");
		for(int i = 1; i <= 3; i++) serialized.deleteCharAt(serialized.length() - 1);
		
		return serialized.toString();
		
	}
	
	public static String location(String world, double x, double y, double z, double yaw, double pitch) {
		return world + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
	}
	
	public static String members(HashMap<String, Double> members) {
		
		if(members.isEmpty()) return "";
		
		StringBuilder serialized = new StringBuilder();
		
		for(Entry<String, Double> e : members.entrySet()) {
			serialized.append(e.getKey() + ":" + String.valueOf(e.getValue()));
			serialized.append("-;;");
		}
		
		for(int i = 1; i <= 3; i++) serialized.deleteCharAt(serialized.length() - 1);
		
		return serialized.toString();
		
	}
	
	public static String settings(HashMap<String, String> settings) {
		
		if(settings.isEmpty()) return "";
		
		StringBuilder serialized = new StringBuilder();
		
		for(Entry<String, String> e : settings.entrySet()) {
			serialized.append(e.getKey() + ":" + e.getValue());
			serialized.append("-;;");
		}
		
		for(int i = 1; i <= 3; i++) serialized.deleteCharAt(serialized.length() - 1);
		
		return serialized.toString();
		
	}
	
}
