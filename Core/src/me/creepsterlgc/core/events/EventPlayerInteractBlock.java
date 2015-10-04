package me.creepsterlgc.core.events;

import java.util.HashMap;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreWorld;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.base.Optional;


public class EventPlayerInteractBlock {

    @Listener
    public void onPlayerInteractBlock(InteractBlockEvent.Primary event) {
    	
    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	Player player = optional.get();
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
	    if(!w.getBuild() && !PermissionsUtils.has(player, "core.world." + w.getName() + ".bypass.interact")) {
		    event.setCancelled(true);
	    }
    	
    }
    
    @Listener
    public void onPlayerInteractBlock(InteractBlockEvent.Secondary event) {
    	
    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	Player player = optional.get();
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
	    if(!w.getBuild() && !PermissionsUtils.has(player, "core.world." + w.getName() + ".bypass.interact")) {
		    event.setCancelled(true);
	    }
    	
    }
	
}
