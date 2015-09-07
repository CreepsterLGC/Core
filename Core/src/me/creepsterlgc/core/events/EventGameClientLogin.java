package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.BAN;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.TIME;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


public class EventGameClientLogin {

    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login event) {
    	
    	BAN ban = DATABASE.getBan(event.getProfile().getUniqueId().toString());
    	
    	if(ban != null) {
    		
    		if(ban.getDuration() != 0 && ban.getDuration() <= System.currentTimeMillis()) {
    			DATABASE.removeBan(event.getProfile().getUniqueId().toString());
    			ban.delete();
    		}
    		else {
	    		String time = TIME.toString(ban.getDuration() - System.currentTimeMillis());
	    		event.setMessage(Texts.of(TextColors.GRAY, "Banned for another: ", TextColors.RED, time, "\n\n", TextColors.RED, "Reason: ", TextColors.GRAY, ban.getReason()));
	    		event.setCancelled(true);
	    		return;
    		}
    		
    	}
    	
    }
	
}
