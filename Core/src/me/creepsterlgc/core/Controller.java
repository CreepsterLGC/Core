package me.creepsterlgc.core;

import java.util.Collection;

import me.creepsterlgc.core.customized.SERVER;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import com.flowpowered.math.vector.Vector3d;


public class Controller {

	public static Game game;
	
	public static Game getGame() { return game; }
	public static Server getServer() { return game.getServer(); }
	
	public static void broadcast(Text text) { SERVER.broadcast(text); }
	
	public static Collection<Player> getPlayers() { return Core.getInstance().getGame().getServer().getOnlinePlayers(); }
	
	public static boolean teleport(Player player, Player target) { return player.transferToWorld(target.getWorld().getName(), new Vector3d(target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ())); }
	
	public static void kick(Player player, Text reason) { player.kick(reason); }
	
//	public static void ban(PLAYER player, CommandSource sender, String reason, double duration) { BAN ban = new BAN(player.getUUID(), sender.getName().toLowerCase(), reason, System.currentTimeMillis(), duration); ban.insert(); }
//	public static void unban(String uuid) { if(STORAGE.getBan(uuid) == null) return; BAN ban = STORAGE.getBan(uuid); ban.delete(); }
//	public static BAN checkban(String uuid) { return STORAGE.getBan(uuid) != null ? STORAGE.getBan(uuid) : null; }
	
}
