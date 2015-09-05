package me.creepsterlgc.core.customized;

public class MUTE {

	private String uuid;
	private double duration;
	private String reason;
	
	public MUTE(String uuid, double duration, String reason) {
		this.uuid = uuid;
		this.duration = duration;
		this.reason = reason;
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setDuration(double duration) { this.duration = duration; }
	public void setReason(String reason) { this.reason = reason; }
	
	public String getUUID() { return uuid; }
	public double getDuration() { return duration; }
	public String getReason() { return reason; }
	
	public void insert() {
		DATABASE.queue("INSERT INTO mutes VALUES ('" + uuid + "', " + duration + ", '" + reason + "')");
		DATABASE.addMute(uuid, this);
	}
	
	public void update() {
		DATABASE.queue("UPDATE mutes SET duration = " + duration + ", reason = '" + reason + "' WHERE uuid = '" + uuid + "'");
		DATABASE.removeMute(uuid);
		DATABASE.addMute(uuid, this);
	}
	
	public void delete() {
		DATABASE.queue("DELETE FROM mutes WHERE uuid = '" + uuid + "'");
		DATABASE.removeMute(uuid);
	}
	
}
