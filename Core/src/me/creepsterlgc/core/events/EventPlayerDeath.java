package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.SERIALIZE;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerDeathEvent;


public class EventPlayerDeath {

    @Subscribe
    public void onPlayerDeath(PlayerDeathEvent event) {
    	String world = event.getEntity().getWorld().getName().toLowerCase();
    	double x = event.getLocation().getX();
    	double y = event.getLocation().getY();
    	double z = event.getLocation().getZ();
    	double yaw = 0;
    	double pitch = 0;
    	String location = SERIALIZE.location(world, x, y, z, yaw, pitch);
    	Player player = (Player) event.getEntity();
    	PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
    	p.setLastdeath(location);
    	p.update();
    }
	
}
