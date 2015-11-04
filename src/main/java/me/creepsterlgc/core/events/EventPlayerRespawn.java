package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreSpawn;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.player.RespawnPlayerEvent;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class EventPlayerRespawn {

	@Listener
	public void onPlayerRespawn(RespawnPlayerEvent event) {
		
		CoreSpawn spawn = CoreDatabase.getSpawn("default");
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
