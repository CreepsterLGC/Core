package me.creepsterlgc.core.customized;

import java.util.List;

import me.creepsterlgc.core.utils.SerializeUtils;

public class CoreTicket {

	private int id;
	private String uuid;
	private String message;
	private double time;
	List<String> comments;
	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	private String assigned;
	private String priority;
	private String status;
	
	public CoreTicket(int id, String uuid, String message, double time, List<String> comments, String world, double x, double y, double z, double yaw, double pitch, String assigned, String priority, String status) {
		this.id = id;
		this.uuid = uuid;
		this.message = message;
		this.time = time;
		this.comments = comments;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.assigned = assigned;
		this.priority = priority;
		this.status = status;
	}
	
	public void setID(int id) { this.id = id; }
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setMessage(String message) { this.message = message; }
	public void setTime(double time) { this.time = time; }
	public void setComments(List<String> comments) { this.comments = comments; }
	public void setWorld(String world) { this.world = world; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setZ(double z) { this.z = z; }
	public void setYaw(double yaw) { this.yaw = yaw; }
	public void setPitch(double pitch) { this.pitch = pitch; }
	public void setAssigned(String assigned) { this.assigned = assigned; }
	public void setPriority(String priority) { this.priority = priority; }
	public void setStatus(String status) { this.status = status; }
	
	public int getID() { return id; }
	public String getUUID() { return uuid; }
	public String getMessage() { return message; }
	public double getTime() { return time; }
	public List<String> getComments() { return comments; }
	public String getWorld() { return world; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public double getYaw() { return yaw; }
	public double getPitch() { return pitch; }
	public String getAssigned() { return assigned; }
	public String getPriority() { return priority; }
	public String getStatus() { return status; }
	
	public void insert() {
		CoreDatabase.queue("INSERT INTO tickets VALUES (" + id + ", '" + uuid + "', '" + message + "', " + time + ", '" + SerializeUtils.messages(comments) + "', '" + world + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", '" + assigned + "', '" + priority + "', '" + status + "')");
		CoreDatabase.addTicket(id, this);
	}
	
	public void update() {
		CoreDatabase.queue("UPDATE tickets SET uuid =  '" + uuid + "', message = '" + message + "', time = " + time + ", comments = '" + SerializeUtils.messages(comments) + "', world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", yaw = " + yaw + ", pitch = " + pitch + ", assigned = '" + assigned + "', priority = '" + priority + "', status = '" + status + "' WHERE id = " + id + "");
		CoreDatabase.removeBan(uuid);
		CoreDatabase.addTicket(id, this);
	}
	
	public void delete() {
		CoreDatabase.queue("DELETE FROM tickets WHERE id = " + id + "");
		CoreDatabase.removeTicket(id);
	}
	
}
