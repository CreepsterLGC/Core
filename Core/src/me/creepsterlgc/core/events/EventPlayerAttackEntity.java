package me.creepsterlgc.core.events;

import java.util.HashMap;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreWorld;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;


public class EventPlayerAttackEntity {

    @Listener
    public void onEventPlayerAttackEntity(InteractEntityEvent.Attack event) {
    	
    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
    	
    	if(player.getItemInHand().isPresent() && PermissionsUtils.has(player, "core.powertool")) {
    		
    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");
    		
    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandDispatcher().process(player.getCommandSource().get(), powertools.get(id));
    		}
    		
    	}
    	
    	CoreWorld w = CoreDatabase.getWorld(player.getWorld().getName());
    	
    	if(w == null) return;
	    if(!w.getBuild() && !PermissionsUtils.has(player, "core.world." + w.getName() + ".bypass.pvp")) {
		    event.setCancelled(true);
	    }
    	
    }
	
}
