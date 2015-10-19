package me.creepsterlgc.core.utils;

import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;

import me.creepsterlgc.core.Controller;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ItemUtils {
	
	public static ItemStack build(ItemType type, int quantity) {
		return Controller.getGame().getRegistry().createItemBuilder().itemType(type).quantity(quantity).build();
	}
	
	public static void drop(ItemStack item, Player player) {
		
		Location<World> location = player.getLocation();
		
		Optional<Entity> o = location.getExtent().createEntity(EntityTypes.ITEM, location.getPosition());
		if (o.isPresent()) {
		    Item i = (Item) o.get();
		    i.offer(Keys.REPRESENTED_ITEM, item.createSnapshot());
		    location.getExtent().spawnEntity(i, Cause.of(player));
		}
		
	}
	
	public static ItemType getType(String type) {
		for(Entry<String, Set<ItemType>> e : Controller.getGame().getRegistry().getGameDictionary().getAllItems().entrySet()) {
			ItemType t = e.getValue().iterator().next();
			String name = t.getName().replaceAll("minecraft:", "");
			if(!name.equalsIgnoreCase(type)) continue;
			return t;
		}
		return null;
	}

}
