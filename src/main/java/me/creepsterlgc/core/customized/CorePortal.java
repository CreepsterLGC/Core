package main.java.me.creepsterlgc.core.customized;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TextUtils;

public class CorePortal {

	private String name;
	private String zone;
	private String warp;
	private String message;
	
	public CorePortal(String name, String region, String warp, String message) {
		this.name = name;
		this.zone = region;
		this.warp = warp;
		this.message = message;
	}
	
	public void setName(String name) { this.name = name; }
	public void setZone(String zone) { this.zone = zone; }
	public void setWarp(String warp) { this.warp = warp; }
	public void setMessage(String message) { this.message = message; }
	
	public String getName() { return name; }
	public String getZone() { return zone; }
	public String getWarp() { return warp; }
	public String getMessage() { return message; }
	
	public void insert() {
		CoreDatabase.queue("INSERT INTO portals VALUES ('" + name + "', '" + zone + "', '" + warp + "', '" + message + "')");
		CoreDatabase.addPortal(name, this);
	}
	
	public void update() {
		CoreDatabase.queue("UPDATE portals SET zone = '" + zone + "', warp = '" + warp + "', message = '" + message + "' WHERE name = '" + name + "'");
		CoreDatabase.addPortal(name, this);
	}
	
	public void delete() {
		CoreDatabase.queue("DELETE FROM portals WHERE name = '" + name + "'");
		CoreDatabase.removePortal(name);
	}
	
	public void transport() {
		
		CoreZone z = CoreDatabase.getZone(zone); if(z == null) return;
		CoreWarp w = CoreDatabase.getWarp(warp); if(w == null) return;
		
		if(!Controller.getServer().getWorld(w.getWorld()).isPresent()) return;
		World world = Controller.getServer().getWorld(w.getWorld()).get();
		
		for(Player player : Controller.getPlayers()) {
			if(!z.isInside(player)) continue;
			if(!PermissionsUtils.has(player, "core.portal.teleport." + name)) continue;
			player.setLocation(new Location<World>(world, w.getX(), w.getY(), w.getZ()));
			player.sendMessage(TextUtils.color(message));
		}
		
	}
	
}
