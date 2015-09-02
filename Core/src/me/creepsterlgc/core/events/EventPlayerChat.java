package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MUTE;

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
    		}
    		
    	}
    	
    }
	
}
