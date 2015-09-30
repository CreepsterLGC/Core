package me.creepsterlgc.core.events;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.files.FileConfig;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;


public class EventPlayerMove {

    @Listener
    public void onPlayerMove(DisplaceEntityEvent event) {
    	
    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	
    	if(!FileConfig.AFK_ENABLE_SYSTEM()) return;
    	
    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
    	if(p == null) return;
		p.setLastaction(System.currentTimeMillis());
		
		if(p.getAFK()) {
			Controller.broadcast(Texts.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " is no longer afk."));
			p.setAFK(false);
		}
		
		CoreDatabase.addPlayer(p.getUUID(), p);
		
    }
	
}
