package main.java.me.creepsterlgc.core.utils;

import org.spongepowered.api.entity.living.player.Player;

public class PlayerUtils {
	
	public static double getYaw(Player player) {
		return player.getRotation().getY();
	}
	
	public static double getPitch(Player player) {
		return player.getRotation().getX();
	}
	
	public static String getFacing(Player player) {
		
		double yaw = getYaw(player); if(yaw < 0) yaw += 360;
		double pitch = getPitch(player);
		
		if(pitch > 70) return "down";
		else if(pitch < -70) return "up";
		
		if(yaw >= 340 || yaw <= 20) return "south";
		else if(yaw >= 70 && yaw <= 110) return "east";
		else if(yaw >= 160 && yaw <= 200) return "north";
		else if(yaw >= 250 && yaw <= 290) return "west";
		
		return "";
		
	}
	
}
