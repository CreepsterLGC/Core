package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.SPAWN;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.player.RespawnPlayerEvent;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class EventPlayerRespawn {

	@Listener
	public void onPlayerRespawn(RespawnPlayerEvent event) {
		
		SPAWN spawn = DATABASE.getSpawn("default");
		if(spawn != null) {
			
			if(event.getGame().getServer().getWorld(spawn.getWorld()).isPresent()) {
				Transform<World> t = event.getToTransform();
				t.setExtent(event.getGame().getServer().getWorld(spawn.getWorld()).get());
				t.setPosition(new Vector3d(spawn.getX(), spawn.getY(), spawn.getZ()));
				event.setToTransform(t);				
			}
			
		}
			
	}
	
}
