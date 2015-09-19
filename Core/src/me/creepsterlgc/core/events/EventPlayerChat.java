package me.creepsterlgc.core.events;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CONFIG;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MUTE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.TEXT;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.MessageSinkEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TextMessageException;

import com.google.common.base.Optional;


public class EventPlayerChat {

    @Listener
    public void onPlayerChat(MessageSinkEvent event) {
    	
    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;
    	
    	Player player = optional.get();
    	String uuid = player.getUniqueId().toString();
    	PLAYER p = DATABASE.getPlayer(uuid);
    	
    	MUTE mute = DATABASE.getMute(uuid);
    	
    	if(mute != null) {
    		
    		if(mute.getDuration() != 0 && mute.getDuration() <= System.currentTimeMillis()) {
    			DATABASE.removeMute(player.getUniqueId().toString());
    			mute.delete();
    		}
    		else {
	    		player.sendMessage(Texts.of(TextColors.RED, mute.getReason()));
	    		event.setCancelled(true);
	    		return;
    		}
    		
    	}
    	
    	if(CONFIG.AFK_ENABLE_SYSTEM()) {
    	
			p.setLastaction(System.currentTimeMillis());
			
			if(p.getAFK()) {
				Controller.broadcast(Texts.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " is no longer afk."));
				p.setAFK(false);
			}
			
			DATABASE.addPlayer(p.getUUID(), p);
		
    	}
    	
		if(!CONFIG.CHAT_USE()) return;
		
    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
    	
    	String name = player.getName();
    	
    	String m = Texts.toPlain(event.getMessage());
    	m = m.replaceAll("<" + name + "> ", "");
    	
    	if(!p.getNick().equalsIgnoreCase("")) name = CONFIG.CHAT_NICK_PREFIX() + p.getNick();
    	
    	String prefix = "";
    	String suffix = "";
    	
    	Text message = Texts.of();
    	
		if (subject instanceof OptionSubject) {
			OptionSubject optionSubject = (OptionSubject) subject;
			prefix = optionSubject.getOption("prefix").or("");
			suffix = optionSubject.getOption("suffix").or("");
		}
    	
    	if(PERMISSIONS.has(player, "core.chat.color")) {
    		message = TEXT.color(m);
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
    	
    	event.setMessage(Texts.of(total, message));
    	
    }
	
}
