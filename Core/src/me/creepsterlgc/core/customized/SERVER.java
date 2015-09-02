package me.creepsterlgc.core.customized;

import me.creepsterlgc.core.Controller;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.sink.MessageSink;

public class SERVER {
	
	public static MessageSink sink;
	
	public static Player getPlayer(String player) {
		
		Player found = null;
		for(Player p : Controller.getServer().getOnlinePlayers()) {
			if(!p.getName().toLowerCase().contains(player.toLowerCase())) continue;
			if(found != null) return null; found = p;
		}
		
		return found;
		
	}
	
	public static void broadcast(Text text) {
		sink.sendMessage(text);
	}

}
