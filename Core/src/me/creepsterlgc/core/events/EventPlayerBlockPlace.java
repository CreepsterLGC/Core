package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreWorld;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.PlaceBlockEvent;

import com.google.common.base.Optional;


public class EventPlayerBlockPlace {

    @Listener
    public void onPlayerBlockPlace(PlaceBlockEvent event) {
    	
    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	Player player = optional.get();
    	CoreWorld w = CoreDatabase.getWorld(event.getTargetWorld().getName());
    	
    	if(w == null) return;
	    if(!w.getBuild() && !PermissionsUtils.has(player, "core.world." + w.getName() + ".bypass.build")) {
		    event.setCancelled(true);
	    }
    	
    }
	
}
