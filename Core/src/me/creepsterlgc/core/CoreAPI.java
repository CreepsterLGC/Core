package me.creepsterlgc.core;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.BAN;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MUTE;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.SPAWN;
import me.creepsterlgc.core.customized.WARP;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.flowpowered.math.vector.Vector3d;

public class CoreAPI {
	
	public boolean addBan(String uuid, String sender, String reason, double time, double duration) {
		if(DATABASE.getBan(uuid) != null) return false;
		BAN ban = new BAN(uuid, sender.toLowerCase(), reason, time, duration); ban.insert(); return true;
	}
	
	/*
	 * RETURNS: true if the ban could be issued, false if not
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
		if(DATABASE.getBan(uuid) == null) return false;
		BAN ban = DATABASE.getBan(uuid); ban.delete(); return true;
	}
	
	/*
	 * RETURNS: true if the ban has been removed, false if not
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would unban "my-unique-id".
	 * 
	 */
	
	public boolean isBanned(String uuid) {
		return DATABASE.getBan(uuid) != null;
	}
	
	/*
	 * RETURNS: true if the uuid is banned, false if not
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would check "my-unique-id".
	 * 
	 */
	
	public boolean addMute(String uuid, double duration, String reason) {
		if(DATABASE.getMute(uuid) != null) return false;
		MUTE mute = new MUTE(uuid, duration, reason); mute.insert(); return true;
	}
	
	/*
	 * RETURNS: true if the ban mute be issued, false if not
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
	 * RETURNS: true if the ban mute has been issued, false if not
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
	 * RETURNS: true if the uuid is muted, false if not
	 * 
	 * uuid = The target's uuid
	 * 
	 * EXAMPLE: ("my-unique-id")
	 * Would check "my-unique-id".
	 * 
	 */
	
	public boolean warp(Player player, String warp) {
		WARP w = DATABASE.getWarp(warp.toLowerCase());
		if(w == null) return false;
		return player.transferToWorld(w.getWorld(), new Vector3d(w.getX(), w.getY(), w.getZ()));
	}
	
	public boolean spawn(Player player, String spawn) {
		SPAWN s = DATABASE.getSpawn(spawn.toLowerCase());
		if(s == null) return false;
		return player.transferToWorld(s.getWorld(), new Vector3d(s.getX(), s.getY(), s.getZ()));
	}
	
	public boolean setNick(String uuid, String nick) {
		PLAYER player = DATABASE.getPlayer(uuid);
		if(player == null) return false;
		player.setNick(nick);
		player.update();
		return true;
	}
	
	public String getNick(String uuid) {
		PLAYER player = DATABASE.getPlayer(uuid);
		if(player == null) return ""; return player.getNick();
	}
	
	public boolean addMoney(String uuid, double amount) {
		PLAYER player = DATABASE.getPlayer(uuid); if(player == null) return false;
		player.addMoney(amount); player.update(); return true;
	}
	
	public boolean removeMoney(String uuid, double amount) {
		PLAYER player = DATABASE.getPlayer(uuid); if(player == null) return false;
		player.removeMoney(amount); player.update(); return true;
	}
	
	public boolean setMoney(String uuid, double amount) {
		PLAYER player = DATABASE.getPlayer(uuid); if(player == null) return false;
		player.setMoney(amount); player.update(); return true;
	}
	
	public double getMoney(String uuid) {
		PLAYER player = DATABASE.getPlayer(uuid);
		if(player == null) return 0; return player.getMoney();
	}
	
	public void broadcast(Text message) {
		Controller.broadcast(Texts.of(TextColors.GOLD, "Server", TextColors.GRAY, ": ", TextColors.WHITE, message));
	}
	
}
