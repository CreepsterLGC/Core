package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.SPAWN;

import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerRespawnEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class EventPlayerRespawn {

	@Subscribe
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		
		SPAWN spawn = DATABASE.getSpawn("default");
		if(spawn != null) {
			
			if(event.getGame().getServer().getWorld(spawn.getWorld()).isPresent()) {
				
				event.setNewRespawnLocation(new Location<World>(event.getGame().getServer().getWorld(spawn.getWorld()).get(), spawn.getX(), spawn.getY(), spawn.getZ()));
				
			}
			
		}
			
	}
	
}
