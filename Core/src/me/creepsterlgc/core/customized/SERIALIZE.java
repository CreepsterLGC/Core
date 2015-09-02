package me.creepsterlgc.core.customized;

import java.util.List;

public class SERIALIZE {

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
	
}
