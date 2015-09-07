package me.creepsterlgc.core.events;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.player.KickPlayerEvent;
import me.creepsterlgc.core.customized.MESSAGES;
import me.creepsterlgc.core.customized.TEXT;


public class EventPlayerKick {

	@Listener
    public void onPlayerQuit(KickPlayerEvent event) {
    	
    	if(MESSAGES.EVENTS_LEAVE_ENABLE()) {
    		event.setMessage(TEXT.color(MESSAGES.EVENTS_LEAVE_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
    	}
    	
    }
	
}
