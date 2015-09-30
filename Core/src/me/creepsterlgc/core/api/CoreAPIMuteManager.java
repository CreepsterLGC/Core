package me.creepsterlgc.core.api;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MUTE;

public class CoreAPIMuteManager {

	public static CoreAPIMuteManager instance;
	
	public boolean addMute(String uuid, double duration, String reason) {
		if(DATABASE.getMute(uuid) != null) return false;
		MUTE mute = new MUTE(uuid, duration, reason); mute.insert(); return true;
	}
	
	/*
	 * RETURNS: true if the ban mute be issued, false if not.
	 * 
	 * uuid = The target's uuid
	 * reason = The ban's reason
	 * duration = You go with: (System.currentTimeMillis() + TIME_IN_MILLISECONDS)
	 * 
	 * EXAMPLE: ("my-unique-id", "Don't spam please!", System.currentTimeMillis() + 1000 * 60)
	 * Would mute "my-unique-id" for 1 minute.
	 * 
	 */
	
	public boolean removeMute(String uuid) {
		if(DATABASE.getMute(uuid) == null) return false;
		MUTE mute = DATABASE.getMute(uuid); mute.delete(); return true;
	}
	
	/*
	 * RETURNS: true if the ban mute has been issued, false if not.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would unmute "my-unique-id".
	 * 
	 */
	
	public boolean isMuted(String uuid) {
		return DATABASE.getMute(uuid) != null;
	}
	
	/*
	 * RETURNS: true if the uuid is muted, false if not.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would check "my-unique-id".
	 * 
	 */
	
}
