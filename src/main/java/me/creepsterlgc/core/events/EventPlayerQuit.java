package main.java.me.creepsterlgc.core.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.files.FileMessages;
import main.java.me.creepsterlgc.core.utils.SerializeUtils;
import main.java.me.creepsterlgc.core.utils.TextUtils;


public class EventPlayerQuit {

	@Listener
    public void onPlayerQuit(ClientConnectionEvent.Disconnect event) {
		
    	if(FileMessages.EVENTS_LEAVE_ENABLE()) {
    		event.setMessage(TextUtils.color(FileMessages.EVENTS_LEAVE_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
    	}
    	
    	Player player = event.getTargetEntity();
    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
    	
    	String world = player.getWorld().getName();
    	double x = player.getLocation().getX();
    	double y = player.getLocation().getY();
    	double z = player.getLocation().getZ();
    	double yaw = 0;
    	double pitch = 0;
    	String location = SerializeUtils.location(world, x, y, z, yaw, pitch);
    	
    	p.setLastlocation(location);
    	p.setLastseen(System.currentTimeMillis());
    	p.update();
    	
    }
	
}
