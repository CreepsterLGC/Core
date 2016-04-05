package main.java.me.creepsterlgc.core.utils;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.files.FileConfig;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;

public class ServerUtils {
	
	public static MessageChannel sink;
	
	public static Player getPlayer(String player) {
		
		for(Player p : Controller.getServer().getOnlinePlayers()) {
			if(p.getName().equalsIgnoreCase(player)) return p;
		}
		
		Player found = null;
		for(Player p : Controller.getServer().getOnlinePlayers()) {
			if(!p.getName().toLowerCase().contains(player.toLowerCase())) continue;
			if(found != null) return null; found = p;
		}
		
		return found;
		
	}
	
	public static void broadcast(Text text) {
		sink.send(text);
	}
	
	public static void broadcast(String permission, Text text) {
		for(Player player : Controller.getPlayers()) {
			if(!PermissionsUtils.has(player, permission)) continue;
			player.sendMessage(text);
		}
	}
	
	public static void heartbeat() {
		
		for(Player player : Controller.getServer().getOnlinePlayers()) {
			
			CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
			
			p.setOnlinetime(p.getOnlinetime() + 1000);
			p.update();
			
		}
		
		if(!FileConfig.AFK_ENABLE_SYSTEM()) return;
		
		for(Player player : Controller.getServer().getOnlinePlayers()) {
			
			CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
			
			double time = System.currentTimeMillis();
			time -= FileConfig.AFK_TIMER_IN_SECONDS() * 1000;
			time -= FileConfig.AFK_KICK_AFTER() * 1000;
			
			if(p.getAFK() && FileConfig.AFK_KICK_ENABLE() && p.getLastaction() < time && !PermissionsUtils.has(player, "core.afk.kick.except")) {
				
				player.kick(Text.of(Text.of(TextColors.RED, "You have been kicked for being AFK!")));
				
			}
			
			if(p.getAFK()) continue;
			if(p.getLastaction() > System.currentTimeMillis() - FileConfig.AFK_TIMER_IN_SECONDS() * 1000) continue;
			
			p.setAFK(true);
			Controller.broadcast(Text.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " is now afk."));
			
		}
		
	}

}