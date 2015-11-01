package me.creepsterlgc.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreZone;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class ZoneUtils {
	
	public static boolean canBuild(Player player, Location<World> location) {
		
		if(PermissionsUtils.has(player, "core.zone.bypass." + location.getExtent().getName() + ".build")) return true;
		
		List<CoreZone> zones = getRegions(location);
		
		for(CoreZone z : zones) {
			
			if(z.getBuild() || z.isOwner(player) || z.isMember(player)) continue;
			
			return false;
			
		}
		
		return true;
		
	}
	
	public static boolean canInteract(Player player, Location<World> location) {
		
		if(PermissionsUtils.has(player, "core.zone.bypass." + location.getExtent().getName() + ".interact")) return true;
		
		List<CoreZone> zones = getRegions(location);
		
		for(CoreZone z : zones) {
			
			if(z.getInteract() || z.isOwner(player) || z.isMember(player)) continue;
			
			return false;
			
		}
		
		return true;
		
	}
	
	public static boolean canPVP(Player player, Location<World> location) {
		
		if(PermissionsUtils.has(player, "core.zone.bypass." + location.getExtent().getName() + ".pvp")) return true;
		
		List<CoreZone> zones = getRegions(location);
		
		for(CoreZone z : zones) {
			
			if(z.getPVP() || z.isOwner(player) || z.isMember(player)) continue;
			
			return false;
			
		}
		
		return true;
		
	}
	
	public static List<CoreZone> getRegions(Location<World> location) {
		
		List<CoreZone> zones = new ArrayList<CoreZone>();
		double priority = 0;
		
		for(Entry<String, CoreZone> e : CoreDatabase.getZones().entrySet()) {
			
			CoreZone z = e.getValue();
			
			if(!z.isInside(location)) continue;
			if(priority > z.getPriority()) continue;
			else if(priority == z.getPriority()) zones.add(z);
			else if(priority < z.getPriority()) {
				priority = z.getPriority();
				zones.clear(); zones.add(z);
			}
			
		}
		
		return zones;
		
	}
	
}
