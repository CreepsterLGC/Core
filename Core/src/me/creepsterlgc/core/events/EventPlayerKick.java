package me.creepsterlgc.core.events;

import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerKickEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


public class EventPlayerKick {

    @Subscribe
    public void onPlayerKick(PlayerKickEvent event) {
    	
    	event.setNewMessage(Texts.of(TextColors.YELLOW, event.getUser().getName(), TextColors.GRAY, " has left."));
    	
    }
	
}
