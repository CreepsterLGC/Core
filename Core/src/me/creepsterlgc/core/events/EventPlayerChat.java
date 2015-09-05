package me.creepsterlgc.core.events;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MUTE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;


public class EventPlayerChat {

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
    	
    	Player player = event.getUser();
    	
    	if(PERMISSIONS.has(player, "core.chat.color")) {
	    	String o = Texts.toPlain(event.getNewMessage());
	    	try {
	    		Text n = Texts.legacy('&').from(o);
	    		event.setNewMessage(n);
			} catch (TextMessageException e) {
				System.out.println("Core: Error while formatting chat message!");
				e.printStackTrace();
			}
    	}
    	
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
