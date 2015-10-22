package me.creepsterlgc.core.events;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreWorld;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.text.Texts;


public class EventEntitySpawn {
	
	@Listener
    public void onEntitySpawn(SpawnEntityEvent event) {
    	
		Entity entity = event.getEntities().iterator().next();
		
    	CoreWorld w = CoreDatabase.getWorld(entity.getWorld().getName());
    	
    	Controller.broadcast(Texts.of("1"));
    	if(w == null) return;
    	Controller.broadcast(Texts.of("2"));
    	if(!w.getAnimalSpawning() && entity instanceof Animal || entity.getType().equals(EntityTypes.BAT)) {
    		event.setCancelled(true);
        	Controller.broadcast(Texts.of("3"));
    		return;
    	}
    	if(!w.getMonsterSpawning() && entity instanceof Monster) {
    		event.setCancelled(true);
    		return;
    	}
		
    }

}
