package main.java.me.creepsterlgc.core.customized;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CoreSelection {
	
	private Location<World> firstpoint;
	private Location<World> secondpoint;
	
	public CoreSelection() {
		
	}
	
	public void setFirstPoint(Location<World> firstpoint) { this.firstpoint = firstpoint; }
	public void setSecondPoint(Location<World> secondpoint) { this.secondpoint = secondpoint; }
	
	public boolean isSet() {
		if(firstpoint == null || secondpoint == null) return false;
		return true;
	}
	
	public boolean isValid() {
		if(!firstpoint.getExtent().getName().equalsIgnoreCase(secondpoint.getExtent().getName())) return false;
		return true;
	}
	
	public World getWorld() {
		if(!firstpoint.getExtent().getName().equalsIgnoreCase(secondpoint.getExtent().getName())) return null;
		return firstpoint.getExtent();
	}
	
	public double getX1() {
		double first = firstpoint.getPosition().getX();
		double second = secondpoint.getPosition().getX();
		if(first <= second) return first; return second;
	}
	
	public double getY1() {
		double first = firstpoint.getPosition().getY();
		double second = secondpoint.getPosition().getY();
		if(first <= second) return first; return second;
	}
	
	public double getZ1() {
		double first = firstpoint.getPosition().getZ();
		double second = secondpoint.getPosition().getZ();
		if(first <= second) return first; return second;
	}
	
	public double getX2() {
		double first = firstpoint.getPosition().getX();
		double second = secondpoint.getPosition().getX();
		if(first <= second) return second; return first;
	}
	public double getY2() {
		double first = firstpoint.getPosition().getY();
		double second = secondpoint.getPosition().getY();
		if(first <= second) return second; return first;
	}
	
	public double getZ2() {
		double first = firstpoint.getPosition().getZ();
		double second = secondpoint.getPosition().getZ();
		if(first <= second) return second; return first;
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
	
	public boolean isInside(Location<World> location) {
		if(!location.getExtent().getName().equals(firstpoint.getExtent().getName())) return false;
		if(!location.getExtent().getName().equals(secondpoint.getExtent().getName())) return false;
    	if(location.getX() < getX1() || location.getX() > getX2()
    	|| location.getY() < getY1() || location.getY() > getY2()
    	|| location.getZ() < getZ1() || location.getZ() > getZ2()) return false; return true;
	}
	
	public boolean isInside(Player player) {
		if(!player.getWorld().getName().equals(firstpoint.getExtent().getName())) return false;
		if(!player.getWorld().getName().equals(secondpoint.getExtent().getName())) return false;
    	if(player.getLocation().getX() < getX1() || player.getLocation().getX() > getX2()
    	|| player.getLocation().getY() < getY1() || player.getLocation().getY() > getY2()
    	|| player.getLocation().getZ() < getZ1() || player.getLocation().getZ() > getZ2()) return false; return true;
	}
	
	public void expand(String direction, double range) {
		
		double fx = firstpoint.getX();
		double fy = firstpoint.getY();
		double fz = firstpoint.getZ();
		double sx = secondpoint.getX();
		double sy = secondpoint.getY();
		double sz = secondpoint.getZ();
		
		if(direction.equalsIgnoreCase("down")) {
			double y = getY1();
			y -= range; if(y < 0) y = 0;
			if(fy <= sy) firstpoint = new Location<World>(firstpoint.getExtent(), fx, y, fz);
			else secondpoint = new Location<World>(secondpoint.getExtent(), sx, y, sz);
		}
		else if(direction.equalsIgnoreCase("up")) {
			double y = getY2();
			y += range; if(y > 255) y = 0;
			if(fy >= sy) firstpoint = new Location<World>(firstpoint.getExtent(), fx, y, fz);
			else secondpoint = new Location<World>(secondpoint.getExtent(), sx, y, sz);
		}
		else if(direction.equalsIgnoreCase("south")) {
			double z = getZ2();
			z += range;
			if(fz >= sz) firstpoint = new Location<World>(firstpoint.getExtent(), fx, fy, z);
			else secondpoint = new Location<World>(secondpoint.getExtent(), sx, sy, z);
		}
		else if(direction.equalsIgnoreCase("east")) {
			double x = getX2();
			x += range;
			if(fz >= sz) firstpoint = new Location<World>(firstpoint.getExtent(), x, fy, fz);
			else secondpoint = new Location<World>(secondpoint.getExtent(), x, sy, sz);
		}
		else if(direction.equalsIgnoreCase("north")) {
			double z = getZ1();
			z -= range;
			if(fz <= sz) firstpoint = new Location<World>(firstpoint.getExtent(), fx, fy, z);
			else secondpoint = new Location<World>(secondpoint.getExtent(), sx, sy, z);
		}
		else if(direction.equalsIgnoreCase("west")) {
			double x = getX1();
			x -= range;
			if(fz <= sz) firstpoint = new Location<World>(firstpoint.getExtent(), x, fy, fz);
			else secondpoint = new Location<World>(secondpoint.getExtent(), x, sy, sz);
		}
		
	}
	
	public List<CoreZone> getOverlappingZones() {
		
		List<CoreZone> result = new ArrayList<CoreZone>();
		
		for(Entry<String, CoreZone> e : CoreDatabase.getZones().entrySet()) {
			
			CoreZone z = e.getValue();
			if(!firstpoint.getExtent().getName().equals(z.getWorld())) continue;
			if(!secondpoint.getExtent().getName().equals(z.getWorld())) continue;
			
			World w = firstpoint.getExtent();
			
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
			
			double x1 = getX1();
			double y1 = getY1();
			double z1 = getZ1();
			double x2 = getX2();
			double y2 = getY2();
			double z2 = getZ2();
			
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
