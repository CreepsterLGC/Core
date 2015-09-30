package me.creepsterlgc.core.events;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.SERVER;
import me.creepsterlgc.core.customized.SPAWN;
import me.creepsterlgc.core.customized.TEXT;
import me.creepsterlgc.core.files.MESSAGES;
import me.creepsterlgc.core.files.MOTD;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;


public class EventPlayerJoin {
	
	@Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
    	
    	Player player = event.getTargetEntity();
    	
		String uuid = player.getUniqueId().toString();
		String name = player.getName().toLowerCase();
		
		PLAYER player_uuid = DATABASE.getPlayer(DATABASE.getUUID(name));
		PLAYER player_name = DATABASE.getPlayer(uuid);
		
    	if(MESSAGES.EVENTS_JOIN_ENABLE()) {
    		event.setMessage(TEXT.color(MESSAGES.EVENTS_JOIN_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
    	}
    	
		PLAYER pl = DATABASE.getPlayer(event.getTargetEntity().getUniqueId().toString());
		if(pl != null) {
			pl.setLastaction(System.currentTimeMillis());
			DATABASE.addPlayer(pl.getUUID(), pl);
		}
		
		if(player_uuid == null && player_name == null) {
			
			PLAYER p = new PLAYER(uuid, name, "", "", 0, 0, 0, 0, 0, 0, "", "", "", System.currentTimeMillis(), System.currentTimeMillis());
			p.setLastaction(System.currentTimeMillis());
			p.insert();
			
	    	if(MESSAGES.EVENTS_FIRSTJOIN_ENABLE()) {
	    		event.setMessage(TEXT.color(MESSAGES.EVENTS_FIRSTJOIN_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
	    		if(MESSAGES.EVENTS_FIRSTJOIN_UNIQUEPLAYERS_SHOW()) {
		    		event.setMessage(Texts.of(TEXT.color(MESSAGES.EVENTS_FIRSTJOIN_MESSAGE().replaceAll("%player", player.getName())), "\n", TEXT.color(MESSAGES.EVENTS_FIRSTJOIN_UNIQUEPLAYERS_MESSAGE().replaceAll("%players", String.valueOf(DATABASE.getPlayers().size())))));
	    		}
	    	}
			
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
		else if(player_uuid == null && player_name != null) {
			
			DATABASE.removePlayer(player_name.getUUID());
			DATABASE.removeUUID(player_name.getName());
			
			player_name.setName(player_name.getUUID());
			player_name.update();
			
			PLAYER p = new PLAYER(uuid, name, "", "", 0, 0, 0, 0, 0, 0, "", "", "", System.currentTimeMillis(), System.currentTimeMillis());
			p.setLastaction(System.currentTimeMillis());
			p.insert();
			
	    	if(MESSAGES.EVENTS_FIRSTJOIN_ENABLE()) {
	    		event.setMessage(TEXT.color(MESSAGES.EVENTS_FIRSTJOIN_MESSAGE().replaceAll("%player", event.getTargetEntity().getName())));
	    		if(MESSAGES.EVENTS_FIRSTJOIN_UNIQUEPLAYERS_SHOW()) {
		    		event.setMessage(Texts.of(TEXT.color(MESSAGES.EVENTS_FIRSTJOIN_MESSAGE().replaceAll("%player", player.getName())), "\n", TEXT.color(MESSAGES.EVENTS_FIRSTJOIN_UNIQUEPLAYERS_MESSAGE().replaceAll("%players", String.valueOf(DATABASE.getPlayers().size())))));
	    		}
	    	}
			
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
		else if(player_uuid != null && player_name == null) {
			
			DATABASE.removePlayer(player_uuid.getUUID());
			DATABASE.removeUUID(player_uuid.getName());
			
			player_uuid.setName(name);
			player_uuid.setLastaction(System.currentTimeMillis());
			player_uuid.update();
			
			SERVER.broadcast(Texts.of(TextColors.GOLD, player_uuid.getName(), " is now known as ", player.getName(), "!"));
			
		}
		else {
			
		}
		
		if(MOTD.SHOW_ON_JOIN()) {
			for(String s : MOTD.MESSAGE()) {
				s = s.replaceAll("%player", player.getName());
				player.sendMessage(TEXT.color(s));
			}
		}
		
    }

}
