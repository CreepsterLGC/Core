package main.java.me.creepsterlgc.core.events;

import main.java.me.creepsterlgc.core.customized.CoreBan;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.customized.CoreSpawn;
import main.java.me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;


public class EventPlayerLogin {

    @Listener
    public void onPlayerLogin(ClientConnectionEvent.Login event) {

    	CoreBan ban = CoreDatabase.getBan(event.getProfile().getUniqueId().toString());

    	if(ban != null) {

    		if(ban.getDuration() != 0 && ban.getDuration() <= System.currentTimeMillis()) {
    			CoreDatabase.removeBan(event.getProfile().getUniqueId().toString());
    			ban.delete();
    		}
    		else {
	    		String time = TimeUtils.toString(ban.getDuration() - System.currentTimeMillis());
	    		event.setMessage(Texts.of(TextColors.GRAY, "Banned for another: ", TextColors.RED, time, "\n\n", TextColors.RED, "Reason: ", TextColors.GRAY, ban.getReason()));
	    		event.setCancelled(true);
	    		return;
    		}

    	}

		String uuid = event.getProfile().getUniqueId().toString();
		CorePlayer player_uuid = CoreDatabase.getPlayer(uuid);

		if(player_uuid == null) {

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

}
