package me.creepsterlgc.core.customized;

public class COMMAND {
	
	public static String combineArgs(int start, String[] args) {
		if(args.length < start) return "";
        StringBuilder combined = new StringBuilder();
        for(int i = start; i < args.length; i++) combined.append(args[i] + " ");
        combined.deleteCharAt(combined.length()-1);
		return combined.toString();
	}
	
}
