package main.java.me.creepsterlgc.core.events;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreSpawn;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

public class EventPlayerRespawn {

	@Listener
	public void onPlayerRespawn(RespawnPlayerEvent event) {

		CoreSpawn spawn = CoreDatabase.getSpawn("default");
		if(spawn != null) {

			if(Sponge.getGame().getServer().getWorld(spawn.getWorld()).isPresent()) {
				Transform<World> t = event.getToTransform();
				t.setExtent(Sponge.getGame().getServer().getWorld(spawn.getWorld()).get());
				t.setPosition(new Vector3d(spawn.getX(), spawn.getY(), spawn.getZ()));
				event.setToTransform(t);
			}

		}

	}

}
