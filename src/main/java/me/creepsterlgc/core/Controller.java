package main.java.me.creepsterlgc.core;

import java.util.Collection;

import main.java.me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;


public class Controller {

	public static Game game;
	
	public static Game getGame() { return game; }
	public static Server getServer() { return game.getServer(); }
	
	public static void broadcast(Text text) { ServerUtils.broadcast(text); }
	
	public static Collection<Player> getPlayers() { return game.getServer().getOnlinePlayers(); }
	
}
