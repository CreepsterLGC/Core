package me.creepsterlgc.core.events;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CONFIG;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerMoveEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


public class EventPlayerMove {

    @Subscribe
    public void onPlayerMove(PlayerMoveEvent event) {
    	
    	if(!CONFIG.AFK_ENABLE_SYSTEM()) return;
    	
    	Player player = event.getEntity();
    	
    	PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
    	if(p == null) return;
		p.setLastaction((double) System.currentTimeMillis());
		
		if(p.getAFK()) {
			Controller.broadcast(Texts.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " is no longer afk."));
			p.setAFK(false);
		}
		
		DATABASE.addPlayer(p.getUUID(), p);
		
    }
	
}
