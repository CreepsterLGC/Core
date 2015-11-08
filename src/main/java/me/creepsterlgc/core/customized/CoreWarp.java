package main.java.me.creepsterlgc.core.customized;

import java.util.List;

import main.java.me.creepsterlgc.core.utils.SerializeUtils;

public class CoreWarp {
	
	private String name;
	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	private String owner;
	private List<String> invited;
	private String priv;
	private String message;
	
	public CoreWarp(String name, String world, double x, double y, double z, double yaw, double pitch, String owner, List<String> invited, String priv, String message) {
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.owner = owner;
		this.invited = invited;
		this.priv = priv;
		this.message = message;
	}
	
	public void insert() {
		CoreDatabase.queue("INSERT INTO warps VALUES ('" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", '" + owner + "', '" + SerializeUtils.list(invited) + "', '" + priv + "', '" + message + "')");
		CoreDatabase.addWarp(name, this);
	}
	
	public void update() {
		CoreDatabase.queue("UPDATE warps SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", yaw = " + yaw + ", pitch = " + pitch + ", owner = '" + owner + "', invited = '" + SerializeUtils.list(invited) + "', private = '" + priv + "', message = '" + message + "' WHERE name = '" + name + "'");
		CoreDatabase.removeWarp(name);
		CoreDatabase.addWarp(name, this);
	}
	
	public void delete() {
		CoreDatabase.queue("DELETE FROM warps WHERE name = '" + name + "'");
		CoreDatabase.removeWarp(name);
	}
	
	public void setName(String name) { this.name = name; }
	public void setWorld(String world) { this.world = world; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setZ(double z) { this.z = z; }
	public void setYaw(double yaw) { this.yaw = yaw; }
	public void setPitch(double pitch) { this.pitch = pitch; }
	public void setOwner(String owner) { this.owner = owner; }
	public void setInvited(List<String> invited) { this.invited = invited; }
	public void setPrivate(String priv) { this.priv = priv; }
	
	public String getName() { return name; }
	public String getWorld() { return world; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public double getYaw() { return yaw; }
	public double getPitch() { return pitch; }
	public String getOwner() { return owner; }
	public List<String> getInvited() { return invited; }
	public String getPrivate() { return priv; }

}
