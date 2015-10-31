package me.creepsterlgc.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeserializeUtils {
	
	public static List<String> list(String list) {
		
		List<String> result = new ArrayList<String>();
		if(list.equalsIgnoreCase("")) return result;
		
		for(String s : list.split(",")) result.add(s);
		return result;
		
	}
	
	public static List<String> messages(String messages) {
		
		List<String> result = new ArrayList<String>();
		if(messages.equalsIgnoreCase("")) return result;
		
		for(String s : messages.split("-;;")) result.add(s);
		return result;
		
	}
	
	public static HashMap<String, Double> members(String members) {
		
		HashMap<String, Double> result = new HashMap<String, Double>();
		
		if(members.equalsIgnoreCase("")) return result;
		
		for(String s : members.split("-;;")) {
			String member = s.split(":")[0];
			double time = Double.parseDouble(s.split(":")[1]);
			result.put(member, time);
		}
		
		return result;
		
	}
	
	public static HashMap<String, String> settings(String settings) {
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		if(settings.equalsIgnoreCase("")) return result;
		
		for(String s : settings.split("-;;")) {
			String setting = s.split(":")[0];
			String value = s.split(":", 2)[1];
			result.put(setting, value);
		}
		
		return result;
		
	}
	
}
