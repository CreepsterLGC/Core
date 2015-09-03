package me.creepsterlgc.core.customized;

import java.util.ArrayList;
import java.util.List;

public class DESERIALIZE {
	
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
	
}
