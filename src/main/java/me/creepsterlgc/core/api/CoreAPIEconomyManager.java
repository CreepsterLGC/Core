package main.java.me.creepsterlgc.core.api;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;

public class CoreAPIEconomyManager {

	public static CoreAPIEconomyManager instance;
	
	public boolean addMoney(String uuid, double amount) {
		CorePlayer player = CoreDatabase.getPlayer(uuid); if(player == null) return false;
		player.addMoney(amount); player.update(); return true;
	}
	
	/*
	 * RETURNS: true if the money could be added, false if not.
	 * 
	 * uuid = The target's uuid
	 * amount = The amount to add to the players balance
	 * 
	 * EXAMPLE: ("my-unique-id", 2500)
	 * Would add $2500 to "my-unique-id".
	 * 
	 */
	
	public boolean removeMoney(String uuid, double amount) {
		CorePlayer player = CoreDatabase.getPlayer(uuid); if(player == null) return false;
		player.removeMoney(amount); player.update(); return true;
	}
	
	/*
	 * RETURNS: true if the money could be removed, false if not.
	 * 
	 * uuid = The target's uuid
	 * amount = The amount to remove from the players balance
	 * 
	 * EXAMPLE: ("my-unique-id", 2500)
	 * Would remove $2500 to "my-unique-id".
	 * 
	 */
	
	public boolean setMoney(String uuid, double amount) {
		CorePlayer player = CoreDatabase.getPlayer(uuid); if(player == null) return false;
		player.setMoney(amount); player.update(); return true;
	}
	
	/*
	 * RETURNS: true if the money could be set, false if not.
	 * 
	 * uuid = The target's uuid
	 * amount = The amount to set the players balance to
	 * 
	 * EXAMPLE: ("my-unique-id", 300)
	 * Would set the balance of "my-unique-id" to $300.
	 * 
	 */
	
	public double getMoney(String uuid) {
		CorePlayer player = CoreDatabase.getPlayer(uuid);
		if(player == null) return 0; return player.getMoney();
	}
	
	/*
	 * RETURNS: The players balance. If the player could not be found it returns 0.
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would return the money of "my-unique-id".
	 * 
	 */
	
}
