package main.java.me.creepsterlgc.core.utils;

public class TimeUtils {

	public static String toString(double milliseconds) {
		
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		
		while(milliseconds >= 1000 * 60 * 60 * 24) { days += 1; milliseconds -= 1000 * 60 * 60 * 24; }
		while(milliseconds >= 1000 * 60 * 60) { hours += 1; milliseconds -= 1000 * 60 * 60; }
		while(milliseconds >= 1000 * 60) { minutes += 1; milliseconds -= 1000 * 60; }
		while(milliseconds >= 1000) { seconds += 1; milliseconds -= 1000; }
		
		return String.valueOf(days) + "d " + String.valueOf(hours) + "h " + String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s";
		
	}
	
	public static String toShortString(double milliseconds) {
		
		int days = 0;
		int hours = 0;
		int minutes = 0;
		
		while(milliseconds >= 1000 * 60 * 60 * 24) { days += 1; milliseconds -= 1000 * 60 * 60 * 24; }
		while(milliseconds >= 1000 * 60 * 60) { hours += 1; milliseconds -= 1000 * 60 * 60; }
		while(milliseconds >= 1000 * 60) { minutes += 1; milliseconds -= 1000 * 60; }
		
		String time = "";
		if(days > 0) time = days + "d ";
		time = time + hours + "h ";
		time = time + minutes + "m";
		
		return time;
		
	}
	
	public static double toMilliseconds(double time, String unit) {
		
		if(unit.equalsIgnoreCase("days")) { return time * 1000 * 60 * 60 * 24; }
		else if(unit.equalsIgnoreCase("hours")) { return time * 1000 * 60 * 60; }
		else if(unit.equalsIgnoreCase("minutes")) { return time * 1000 * 60; }
		else if(unit.equalsIgnoreCase("seconds")) { return time * 1000; }
		
		return 0;
		
	}
	
}
