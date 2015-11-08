package main.java.me.creepsterlgc.core.customized;

public class CoreMute {

	private String uuid;
	private double duration;
	private String reason;
	
	public CoreMute(String uuid, double duration, String reason) {
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
		CoreDatabase.queue("INSERT INTO mutes VALUES ('" + uuid + "', " + duration + ", '" + reason + "')");
		CoreDatabase.addMute(uuid, this);
	}
	
	public void update() {
		CoreDatabase.queue("UPDATE mutes SET duration = " + duration + ", reason = '" + reason + "' WHERE uuid = '" + uuid + "'");
		CoreDatabase.removeMute(uuid);
		CoreDatabase.addMute(uuid, this);
	}
	
	public void delete() {
		CoreDatabase.queue("DELETE FROM mutes WHERE uuid = '" + uuid + "'");
		CoreDatabase.removeMute(uuid);
	}
	
}
