package me.creepsterlgc.core.events;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CONFIG;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MUTE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;


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
    	
    	if(CONFIG.AFK_ENABLE_SYSTEM()) {
    	
	    	PLAYER p = DATABASE.getPlayer(uuid);
			p.setLastaction((double)System.currentTimeMillis());
			
			if(p.getAFK()) {
				Controller.broadcast(Texts.of(TextColors.YELLOW, event.getEntity().getName(), TextColors.GRAY, " is no longer afk."));
				p.setAFK(false);
			}
			
			DATABASE.addPlayer(p.getUUID(), p);
		
    	}
    	
		if(!CONFIG.CHAT_USE()) return;
		
    	Player player = event.getUser();
    	
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
    	
    	String name = player.getName();
    	String prefix = "";
    	String suffix = "";
    	String m = Texts.toPlain(event.getNewMessage());
    	m = m.replaceAll("<" + name + "> ", "");
    	
    	Text message = Texts.of();
    	
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			prefix = optionSubject.getOption("prefix").or("");
			suffix = optionSubject.getOption("suffix").or("");
		}
    	
    	if(PERMISSIONS.has(player, "core.chat.color")) {
	    	try {
	    		message = Texts.legacy('&').from(m);
			} catch (TextMessageException e) {
				System.out.println("Core: Error while formatting chat message!");
				e.printStackTrace();
			}
    	}
    	else {
    		message = Texts.of(m);
    	}
    	
    	String format = CONFIG.CHAT_FORMAT();
    	format = format.replaceAll("%prefix", prefix).replaceAll("%suffix", suffix).replaceAll("%player", name);
    	Text total = Texts.of();
    	try {
			total = Texts.legacy('&').from(format);
		} catch (TextMessageException e) {
			System.out.println("Core: Error while formatting chat message!");
			e.printStackTrace();
		}
    	
    	event.setNewMessage(Texts.of(total, message));
    	
    }
	
}
