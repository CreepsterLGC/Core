package main.java.me.creepsterlgc.core.events;

import java.util.HashMap;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;


public class EventPlayerInteractEntity {

    @Listener
    public void onPlayerInteractEntity(InteractEntityEvent.Primary event) {

    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

    	if(player.getItemInHand().isPresent() && PermissionsUtils.has(player, "core.powertool")) {

    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");

    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandManager().process(player.getCommandSource().get(), powertools.get(id));
    		}

    	}

    }

    @Listener
    public void onPlayerInteractEntity(InteractEntityEvent.Secondary event) {

    	if(event.getTargetEntity() instanceof Player == false) return;
    	Player player = (Player) event.getTargetEntity();
    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

    	if(player.getItemInHand().isPresent() && PermissionsUtils.has(player, "core.powertool")) {

    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");

    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandManager().process(player.getCommandSource().get(), powertools.get(id));
    		}

    	}

    }

}
