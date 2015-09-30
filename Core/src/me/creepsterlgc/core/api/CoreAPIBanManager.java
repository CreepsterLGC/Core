package me.creepsterlgc.core.api;

import me.creepsterlgc.core.customized.CoreBan;
import me.creepsterlgc.core.customized.CoreDatabase;

public class CoreAPIBanManager {

	public static CoreAPIBanManager instance;
	
	public boolean addBan(String uuid, String sender, String reason, double time, double duration) {
		if(CoreDatabase.getBan(uuid) != null) return false;
		CoreBan ban = new CoreBan(uuid, sender.toLowerCase(), reason, time, duration); ban.insert(); return true;
	}
	
	/*
	 * RETURNS: true if the ban could be issued, false if not.
	 * 
	 * uuid = The target's uuid
	 * sender = Sender issuing the ban
	 * reason = The ban's reason
	 * time = Systemtime when the ban has been issued
	 * duration = If the ban is permanent you go with 0. Otherwise you do: (System.currentTimeMillis() + TIME_IN_MILLISECONDS)
	 * 
	 * EXAMPLE: ("my-unique-id", "creepsterlgc", "griefing my house", System.currentTimeMillis(), System.currentTimeMillis() + 1000 * 3600)
	 * Would ban "my-unique-id" for 1 hour.
	 * 
	 */
	
	public boolean removeBan(String uuid) {
		if(CoreDatabase.getBan(uuid) == null) return false;
		CoreBan ban = CoreDatabase.getBan(uuid); ban.delete(); return true;
	}
	
	/*
	 * RETURNS: true if the ban has been removed, false if not.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would unban "my-unique-id".
	 * 
	 */
	
	public boolean isBanned(String uuid) {
		return CoreDatabase.getBan(uuid) != null;
	}
	
	/*
	 * RETURNS: true if the uuid is banned, false if not.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would check "my-unique-id".
	 * 
	 */
	
	public CoreBan getBan(String uuid) {
		return CoreDatabase.getBan(uuid) != null ? CoreDatabase.getBan(uuid) : null;
	}
	
	/*
	 * RETURNS: the ban or null if the player is not banned.
	 * 
	 */
	
}
