package me.creepsterlgc.core.events;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MUTE;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


public class EventPlayerChat {

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
    	
    	String uuid = event.getEntity().getUniqueId().toString();
    	
    	MUTE mute = DATABASE.getMute(uuid);
    	
    	if(mute != null) {
    		
    		if(mute.getDuration() != 0 && mute.getDuration() <= System.currentTimeMillis()) {
    			DATABASE.removeMute(event.getEntity().getUniqueId().toString());
    			mute.delete();
    		}
    		else {
	    		event.getEntity().sendMessage(Texts.of(TextColors.RED, mute.getReason()));
	    		event.setCancelled(true);
	    		return;
    		}
    		
    	}
    	
    	PLAYER p = DATABASE.getPlayer(uuid);
		p.setLastaction((double)System.currentTimeMillis());
		
		if(p.getAFK()) {
			Controller.broadcast(Texts.of(TextColors.YELLOW, event.getEntity().getName(), TextColors.GRAY, " is no longer afk."));
			p.setAFK(false);
		}
		
		DATABASE.addPlayer(p.getUUID(), p);
    	
    }
	
}
