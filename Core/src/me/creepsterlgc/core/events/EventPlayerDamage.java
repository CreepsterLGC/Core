package me.creepsterlgc.core.events;

import java.util.HashMap;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;


public class EventPlayerDamage {

    @Listener
    public void onEventDamageEntity(DamageEntityEvent event) {

    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
    	
    	if(player.getItemInHand().isPresent() && PermissionsUtils.has(player, "core.powertool")) {
    		
    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");
    		
    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandDispatcher().process(player.getCommandSource().get(), powertools.get(id));
    		}
    		
    	}
    	
    }
	
}
