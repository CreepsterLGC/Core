package me.creepsterlgc.core.api;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;

public class CoreAPIChatManager {

	public static CoreAPIChatManager instance;
	
	public boolean setNick(String uuid, String nick) {
		CorePlayer player = CoreDatabase.getPlayer(uuid);
		if(player == null) return false;
		player.setNick(nick);
		player.update();
		return true;
	}
	
	/*
	 * RETURNS: true if the nick could be set, false if not.
	 * 
	 * uuid = The target's uuid
	 * nick = The nickname. Supports color codes (ex: "&cCreep&6ster&7LGC" would be possible)
	 * 
	 * EXAMPLE: ("my-unique-id", "&6Jim")
	 * Would set the nick for "my-unique-id" to "&6Jim".
	 * 
	 */
	
	public String getNick(String uuid) {
		CorePlayer player = CoreDatabase.getPlayer(uuid);
		if(player == null) return ""; return player.getNick();
	}
	
	/*
	 * RETURNS: The nick of the uuid. If the uuid could not be found it returns "".
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would return the nick of "my-unique-id".
	 * 
	 */
	
}
