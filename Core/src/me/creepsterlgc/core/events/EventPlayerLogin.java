package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.CoreBan;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


public class EventPlayerLogin {

    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login event) {
    	
    	CoreBan ban = CoreDatabase.getBan(event.getProfile().getUniqueId().toString());
    	
    	if(ban != null) {
    		
    		if(ban.getDuration() != 0 && ban.getDuration() <= System.currentTimeMillis()) {
    			CoreDatabase.removeBan(event.getProfile().getUniqueId().toString());
    			ban.delete();
    		}
    		else {
	    		String time = TimeUtils.toString(ban.getDuration() - System.currentTimeMillis());
	    		event.setMessage(Texts.of(TextColors.GRAY, "Banned for another: ", TextColors.RED, time, "\n\n", TextColors.RED, "Reason: ", TextColors.GRAY, ban.getReason()));
	    		event.setCancelled(true);
	    		return;
    		}
    		
    	}
    	
    }
	
}
