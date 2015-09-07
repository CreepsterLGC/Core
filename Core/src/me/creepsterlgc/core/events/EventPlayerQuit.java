package me.creepsterlgc.core.events;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import me.creepsterlgc.core.customized.MESSAGES;
import me.creepsterlgc.core.customized.TEXT;


public class EventPlayerQuit {

	@Listener
    public void onPlayerQuit(ClientConnectionEvent.Disconnect event) {
    	
    	if(MESSAGES.EVENTS_LEAVE_ENABLE()) {
    		event.setMessage(TEXT.color(MESSAGES.EVENTS_LEAVE_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
    	}
    	
    }
	
}
