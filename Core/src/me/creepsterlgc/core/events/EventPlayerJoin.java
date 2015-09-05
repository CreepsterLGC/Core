package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.SERVER;
import me.creepsterlgc.core.customized.SPAWN;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.flowpowered.math.vector.Vector3d;


public class EventPlayerJoin {
	
    public static void onPlayerJoin(PlayerJoinEvent event) {
    	
    	Player player = event.getUser();
    	
		String uuid = player.getUniqueId().toString();
		String name = player.getName().toLowerCase();
		
		PLAYER player_uuid = DATABASE.getPlayer(DATABASE.getUUID(name));
		PLAYER player_name = DATABASE.getPlayer(uuid);
		
		if(player_uuid == null && player_name == null) {
			
			PLAYER p = new PLAYER(uuid, name, "", 0, "", "", "", 0, 0);
			p.setLastaction((double)System.currentTimeMillis());
			p.insert();
			
			SERVER.broadcast(Texts.of(TextColors.GOLD, player.getName(), " has joined for the first time!"));
			SERVER.broadcast(Texts.of(TextColors.GOLD, DATABASE.getPlayers().size(), " unique players already joined."));
			
			SPAWN spawn = DATABASE.getSpawn("default");
			if(spawn != null) {
				if(!player.transferToWorld(spawn.getWorld(), new Vector3d(spawn.getX(), spawn.getY(), spawn.getZ()))) { }
			}
			
		}
		else if(player_uuid == null && player_name != null) {
			
			DATABASE.removePlayer(player_name.getUUID());
			DATABASE.removeUUID(player_name.getName());
			
			player_name.setName(player_name.getUUID());
			player_name.update();
			
			PLAYER p = new PLAYER(uuid, name, "", 0, "", "", "", 0, 0);
			p.setLastaction((double)System.currentTimeMillis());
			p.insert();
			
			SERVER.broadcast(Texts.of(TextColors.GOLD, player.getName(), " has joined for the first time!"));
			SERVER.broadcast(Texts.of(TextColors.GOLD, DATABASE.getPlayers().size(), " unique players already joined."));
			
			SPAWN spawn = DATABASE.getSpawn("default");
			if(spawn != null) {
				if(!player.transferToWorld(spawn.getWorld(), new Vector3d(spawn.getX(), spawn.getY(), spawn.getZ()))) { }
			}
			
		}
		else if(player_uuid != null && player_name == null) {
			
			DATABASE.removePlayer(player_uuid.getUUID());
			DATABASE.removeUUID(player_uuid.getName());
			
			player_uuid.setName(name);
			player_uuid.setLastaction((double)System.currentTimeMillis());
			player_uuid.update();
			
			SERVER.broadcast(Texts.of(TextColors.GOLD, player_uuid.getName(), " is now known as ", player.getName(), "!"));
			
		}
		else {
			
		}
		
    }

}
