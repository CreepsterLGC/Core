package me.creepsterlgc.core.customized;

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

}
