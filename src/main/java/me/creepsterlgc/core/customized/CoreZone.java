package me.creepsterlgc.core.customized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.utils.SerializeUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CoreZone {
	
	private String name;
	private String world;
	private double x1;
	private double y1;
	private double z1;
	private double x2;
	private double y2;
	private double z2;
	private double priority;
	private String owner;
	private HashMap<String, Double> members;
	private HashMap<String, String> settings;
	
	public CoreZone(String name, String world, double x1, double y1, double z1, double x2, double y2, double z2, double priority, String owner, HashMap<String, Double> members, HashMap<String, String> settings) {
		this.name = name;
		this.world = world;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.priority = priority;
		this.owner = owner;
		this.members = members;
		this.settings = settings;
	}
	
	public void insert() {
		CoreDatabase.addZone(name, this);
		CoreDatabase.queue("INSERT INTO zones VALUES ('" + name + "', '" + world + "', " + x1 + ", " + y1 + ", " + z1 + ", " + x2 + ", " + y2 + ", " + z2 + ", " + priority + ", '" + owner + "', '" + SerializeUtils.members(members) + "', '" + SerializeUtils.settings(settings) + "')");
	}
	
	public void update() {
		CoreDatabase.addZone(name, this);
		CoreDatabase.queue("UPDATE zones SET world = '" + world + "', x1 = " + x1 + ", y1 = " + y1 + ", z1 = " + z1 + ", x2 = " + x2 + ", y2 = " + y2 + ", z2 = " + z2 + ", priority = " + priority + ", owner = '" + owner + "', members = '" + SerializeUtils.members(members) + "', settings = '" + SerializeUtils.settings(settings) + "' WHERE name = '" + name + "'");
	}
	
	public void delete() {
		CoreDatabase.removeZone(name);
		CoreDatabase.queue("DELETE FROM zones WHERE name = '" + name + "'");
	}
	
	public void setName(String name) { this.name = name; }
	public void setWorld(String world) { this.world = world; }
	public void setX1(double x1) { this.x1 = x1; }
	public void setY1(double y1) { this.y1 = y1; }
	public void setZ1(double z1) { this.z1 = z1; }
	public void setX2(double x2) { this.x2 = x2; }
	public void setY2(double y2) { this.y2 = y2; }
	public void setZ2(double z2) { this.z2 = z2; }
	public void setPriority(double priority) { this.priority = priority; }
	public void setOwner(String uuid) { owner = uuid; }
	
	public String getName() { return name; }
	public String getWorld() { return world; }
	public double getX1() { return x1; }
	public double getY1() { return y1; }
	public double getZ1() { return z1; }
	public double getX2() { return x2; }
	public double getY2() { return y2; }
	public double getZ2() { return z2; }
	public double getPriority() { return priority; }
	public String getOwner() { return owner; }
	
	public HashMap<String, Double> getMembers() {
		for(Entry<String, Double> member : members.entrySet()) {
			double time = member.getValue();
			if(time != 0 && time <= System.currentTimeMillis()) {
				members.remove(member.getKey());
			}
		}
		return members;
	}
	
	public double getLength() {
		return getX2() - getX1() + 1;
	}
	
	public double getHeight() {
		return getY2() - getY1() + 1;
	}
	
	public double getWidth() {
		return getZ2() - getZ1() + 1;
	}
	
	public HashMap<String, String> getSettings() { return settings; }

	public void addMember(String uuid, double time) {
		if(!members.containsKey(uuid)) members.put(uuid, time);
	}
	
	public void addMember(Player player, double time) {
		String uuid = player.getUniqueId().toString();
		if(!members.containsKey(uuid)) members.put(uuid, time);
	}
	
	public void removeMember(String uuid) {
		if(members.containsKey(uuid)) members.remove(uuid);
	}
	
	public void removeMember(Player player) {
		String uuid = player.getUniqueId().toString();
		if(members.containsKey(uuid)) members.remove(uuid);
	}
	
	public boolean isMember(String uuid) {
		if(!members.containsKey(uuid)) return false;
		double time = members.get(uuid);
		if(time != 0 && time <= System.currentTimeMillis()) {
			members.remove(uuid);
			return false;
		}
		return true;
	}
	
	public boolean isMember(Player player) {
		String uuid = player.getUniqueId().toString();
		if(!members.containsKey(uuid)) return false;
		double time = members.get(uuid);
		if(time != 0 && time <= System.currentTimeMillis()) {
			members.remove(uuid);
			return false;
		}
		return true;
	}
	
	public boolean isOwner(Player player) {
		String uuid = player.getUniqueId().toString();
		if(owner.equalsIgnoreCase(uuid)) return true; return false;
	}
	
	public boolean isOwner(String uuid) {
		if(owner.equalsIgnoreCase(uuid)) return true; return false;
	}
	
	public boolean isInside(Location<World> location) {
		if(!location.getExtent().getName().equals(world)) return false;
    	if(location.getX() < x1 || location.getX() > x2
    	|| location.getY() < y1 || location.getY() > y2
    	|| location.getZ() < z1 || location.getZ() > z2) return false; return true;
	}
	
	public boolean isInside(Player player) {
		if(!player.getWorld().getName().equals(world)) return false;
    	if(player.getLocation().getX() < x1 || player.getLocation().getX() > x2
    	|| player.getLocation().getY() < y1 || player.getLocation().getY() > y2
    	|| player.getLocation().getZ() < z1 || player.getLocation().getZ() > z2) return false; return true;
	}
	
	public void allowBuild(boolean state) {
		String s = "allow"; if(state == false) s = "deny";
		settings.put("build", s);
	}
	
	public boolean getBuild() {
		if(!settings.containsKey("build")) return false; String s = settings.get("build");
		if(s.equalsIgnoreCase("allow")) return true; return false;
	}
	
	public void allowInteract(boolean state) {
		String s = "allow"; if(state == false) s = "deny";
		settings.put("interact", s);
	}
	
	public boolean getInteract() {
		if(!settings.containsKey("interact")) return false; String s = settings.get("interact");
		if(s.equalsIgnoreCase("allow")) return true; return false;
	}
	
	public void allowPVP(boolean state) {
		String s = "allow"; if(state == false) s = "deny";
		settings.put("pvp", s);
	}
	
	public boolean getPVP() {
		if(!settings.containsKey("pvp")) return true; String s = settings.get("pvp");
		if(s.equalsIgnoreCase("allow")) return true; return false;
	}
	
	public void allowAnimalSpawning(boolean state) {
		String s = "allow"; if(state == false) s = "deny";
		settings.put("animals", s);
	}
	
	public boolean getAnimalSpawning() {
		if(!settings.containsKey("animals")) return true; String s = settings.get("animals");
		if(s.equalsIgnoreCase("allow")) return true; return false;
	}
	
	public void allowMonsterSpawning(boolean state) {
		String s = "allow"; if(state == false) s = "deny";
		settings.put("monsters", s);
	}
	
	public boolean getMonsterSpawning() {
		if(!settings.containsKey("monsters")) return true; String s = settings.get("monsters");
		if(s.equalsIgnoreCase("allow")) return true; return false;
	}
	
	public void allowHunger(boolean state) {
		String s = "allow"; if(state == false) s = "deny";
		settings.put("hunger", s);
	}
	
	public boolean getHunger() {
		if(!settings.containsKey("hunger")) return true; String s = settings.get("hunger");
		if(s.equalsIgnoreCase("allow")) return true; return false;
	}
	
	public void allowInvulnerability(boolean state) {
		String s = "invulnerability"; if(state == false) s = "deny";
		settings.put("invulnerability", s);
	}
	
	public boolean getInvulnerability() {
		if(!settings.containsKey("invulnerability")) return true; String s = settings.get("invulnerability");
		if(s.equalsIgnoreCase("allow")) return true; return false;
	}
	
	public List<CoreZone> getOverlappingZones() {
		
		List<CoreZone> result = new ArrayList<CoreZone>();
		
		for(Entry<String, CoreZone> e : CoreDatabase.getZones().entrySet()) {
			
			CoreZone z = e.getValue(); if(!world.equals(z.getWorld())) continue;
			
			if(!Controller.getServer().getWorld(world).isPresent()) return result;
			World w = Controller.getServer().getWorld(world).get();
			
			double length = z.getX2() - z.getX1() + 1;
			double height = z.getY2() - z.getY1() + 1;
			double width = z.getZ2() - z.getZ1() + 1;
			
			Location<World> p1 = new Location<World>(w, z.getX1(), z.getY1(), z.getZ1());
			Location<World> p2 = new Location<World>(w, z.getX2(), z.getY2(), z.getZ2());
			Location<World> p3 = new Location<World>(w, z.getX1() + length, z.getY1(), z.getZ1());
			Location<World> p4 = new Location<World>(w, z.getX1(), z.getY1() + height, z.getZ1());
			Location<World> p5 = new Location<World>(w, z.getX1(), z.getY1(), z.getZ1() + width);
			Location<World> p6 = new Location<World>(w, z.getX1() + length, z.getY1() + height, z.getZ1());
			Location<World> p7 = new Location<World>(w, z.getX1() + length, z.getY1(), z.getZ1() + width);
			Location<World> p8 = new Location<World>(w, z.getX1(), z.getY1() + height, z.getZ1() + width);
			
			if(isInside(p1) || isInside(p2) || isInside(p3) || isInside(p4) || isInside(p5) || isInside(p6) || isInside(p7) || isInside(p8)) {
				result.add(z);
				continue;
			}
			
			length = x2 - x1 + 1;
			height = y2 - y1 + 1;
			width = z2 - z1 + 1;
			
			p1 = new Location<World>(w, x1, y1, z1);
			p2 = new Location<World>(w, x2, y2, z2);
			p3 = new Location<World>(w, x1 + length, y1, z1);
			p4 = new Location<World>(w, x1, y1 + height, z1);
			p5 = new Location<World>(w, x1, y1, z1 + width);
			p6 = new Location<World>(w, x1 + length, y1 + height, z1);
			p7 = new Location<World>(w, x1 + length, y1, z1 + width);
			p8 = new Location<World>(w, x1, y1 + height, z1 + width);
			
			if(z.isInside(p1) || z.isInside(p2) || z.isInside(p3) || z.isInside(p4) || z.isInside(p5) || z.isInside(p6) || z.isInside(p7) || z.isInside(p8)) {
				result.add(z);
				continue;
			}
			
		}
		
		return result;
		
	}
	
}
