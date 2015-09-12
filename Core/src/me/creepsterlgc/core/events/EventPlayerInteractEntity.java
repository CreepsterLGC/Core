package me.creepsterlgc.core.events;

import java.util.HashMap;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;


public class EventPlayerInteractEntity {

    @Listener
    public void onPlayerInteractEntity(InteractEntityEvent.Primary event) {
    	
    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
    	
    	if(player.getItemInHand().isPresent() && PERMISSIONS.has(player, "core.powertool")) {
    		
    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");
    		
    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandDispatcher().process(player.getCommandSource().get(), powertools.get(id));
    		}
    		
    	}
    	
    }
    
    @Listener
    public void onPlayerInteractEntity(InteractEntityEvent.Secondary event) {
    	
    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
    	
    	if(player.getItemInHand().isPresent() && PERMISSIONS.has(player, "core.powertool")) {
    		
    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");
    		
    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandDispatcher().process(player.getCommandSource().get(), powertools.get(id));
    		}
    		
    	}
    	
    }
	
}
