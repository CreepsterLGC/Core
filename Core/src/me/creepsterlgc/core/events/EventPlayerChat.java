package me.creepsterlgc.core.events;

import java.util.Optional;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreMute;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.files.FileConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.MessageSinkEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


public class EventPlayerChat {

    @Listener
    public void onPlayerChat(MessageSinkEvent.Chat event) {

    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	Player player = optional.get();
    	String uuid = player.getUniqueId().toString();
    	CorePlayer p = CoreDatabase.getPlayer(uuid);
    	
    	CoreMute mute = CoreDatabase.getMute(uuid);
    	
    	if(mute != null) {
    		
    		if(mute.getDuration() != 0 && mute.getDuration() <= System.currentTimeMillis()) {
    			CoreDatabase.removeMute(player.getUniqueId().toString());
    			mute.delete();
    		}
    		else {
	    		player.sendMessage(Texts.of(TextColors.RED, mute.getReason()));
	    		event.setCancelled(true);
	    		return;
    		}
    		
    	}
    	
    	if(FileConfig.AFK_ENABLE_SYSTEM()) {
    	
			p.setLastaction(System.currentTimeMillis());
			
			if(p.getAFK()) {
				Controller.broadcast(Texts.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " is no longer afk."));
				p.setAFK(false);
			}
			
			CoreDatabase.addPlayer(p.getUUID(), p);
		
    	}
    	
    }
	
}
