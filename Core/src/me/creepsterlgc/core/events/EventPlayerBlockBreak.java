package me.creepsterlgc.core.events;

import java.util.Optional;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreWorld;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;


public class EventPlayerBlockBreak {

    @Listener
    public void onPlayerBlockBreak(ChangeBlockEvent.Break event) {
    	
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
