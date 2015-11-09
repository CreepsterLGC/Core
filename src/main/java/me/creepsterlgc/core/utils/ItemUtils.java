package main.java.me.creepsterlgc.core.utils;

import java.util.Optional;
import main.java.me.creepsterlgc.core.Controller;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackBuilder;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ItemUtils {
	
	public static ItemStack build(ItemType type, int quantity) {
		return Controller.getGame().getRegistry().createBuilder(ItemStackBuilder.class).itemType(type).quantity(quantity).build();
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
		Optional<ItemType> o = Controller.getGame().getRegistry().getType(ItemType.class, type);
		if(o.isPresent()) return o.get(); return null;
	}
	
	public static Enchantment getEnchantment(String enchantment) {
		
		if(enchantment.equalsIgnoreCase("aqua_affinity")) return Enchantments.AQUA_AFFINITY;
		else if(enchantment.equalsIgnoreCase("bane_of_arthropods")) return Enchantments.BANE_OF_ARTHROPODS;
		else if(enchantment.equalsIgnoreCase("blast_protection")) return Enchantments.BLAST_PROTECTION;
		else if(enchantment.equalsIgnoreCase("depth_strider")) return Enchantments.DEPTH_STRIDER;
		else if(enchantment.equalsIgnoreCase("efficiency")) return Enchantments.EFFICIENCY;
		else if(enchantment.equalsIgnoreCase("feather_falling")) return Enchantments.FEATHER_FALLING;
		else if(enchantment.equalsIgnoreCase("fire_aspect")) return Enchantments.FIRE_ASPECT;
		else if(enchantment.equalsIgnoreCase("fire_protection")) return Enchantments.FIRE_PROTECTION;
		else if(enchantment.equalsIgnoreCase("flame")) return Enchantments.FLAME;
		else if(enchantment.equalsIgnoreCase("fortune")) return Enchantments.FORTUNE;
		else if(enchantment.equalsIgnoreCase("infinity")) return Enchantments.INFINITY;
		else if(enchantment.equalsIgnoreCase("knockback")) return Enchantments.KNOCKBACK;
		else if(enchantment.equalsIgnoreCase("looting")) return Enchantments.LOOTING;
		else if(enchantment.equalsIgnoreCase("luck_of_the_sea")) return Enchantments.LUCK_OF_THE_SEA;
		else if(enchantment.equalsIgnoreCase("lure")) return Enchantments.LURE;
		else if(enchantment.equalsIgnoreCase("power")) return Enchantments.POWER;
		else if(enchantment.equalsIgnoreCase("power")) return Enchantments.POWER;
		else if(enchantment.equalsIgnoreCase("projectile_protection")) return Enchantments.PROJECTILE_PROTECTION;
		else if(enchantment.equalsIgnoreCase("protection")) return Enchantments.PROTECTION;
		else if(enchantment.equalsIgnoreCase("punch")) return Enchantments.PUNCH;
		else if(enchantment.equalsIgnoreCase("respiration")) return Enchantments.RESPIRATION;
		else if(enchantment.equalsIgnoreCase("sharpness")) return Enchantments.SHARPNESS;
		else if(enchantment.equalsIgnoreCase("silk_touch")) return Enchantments.SILK_TOUCH;
		else if(enchantment.equalsIgnoreCase("smite")) return Enchantments.SMITE;
		else if(enchantment.equalsIgnoreCase("thorns")) return Enchantments.THORNS;
		else if(enchantment.equalsIgnoreCase("unbreaking")) return Enchantments.UNBREAKING;
		
		return null;
		
	}

}
