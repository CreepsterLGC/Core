package me.creepsterlgc.core.api;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreWarp;

public class CoreAPIWarpManager {

	public static CoreAPIWarpManager instance;
	
	public boolean teleport(Player player, String warp) {
		CoreWarp w = CoreDatabase.getWarp(warp.toLowerCase());
		if(w == null) return false;
		if(!Controller.getServer().getWorld(w.getWorld()).isPresent()) return false;
		player.setLocation(new Location<World>(Controller.getServer().getWorld(w.getWorld()).get(), new Vector3d(w.getX(), w.getY(), w.getZ())));
		return true;
	}
	
	/*
	 * RETURNS: true if the player could be teleported, false if not.
	 * 
	 * player = The target player
	 * warp = Name of the warp
	 * 
	 * EXAMPLE: (player, "beach")
	 * Would teleport player to warp "beach".
	 * 
	 */
	
}
