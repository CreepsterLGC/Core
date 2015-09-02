package me.creepsterlgc.core.customized;

import java.util.HashMap;

public class PLAYER {
	
	private String uuid;
	private String name;
	private String godmode;
	private double flymode;
	private String mails;
	private String location;
	private String lastdeath;
	private double onlinetime;
	private double lastonline;
	
	private HashMap<String, HOME> homes;
	private String reply;
	HashMap<String, Double> tpa;
	HashMap<String, Double> tpahere;
	
	public PLAYER(String uuid, String name, String godmode, double flymode, String mails, String location, String lastdeath, double onlinetime, double lastonline) {
		this.uuid = uuid;
		this.name = name;
		this.godmode = godmode;
		this.flymode = flymode;
		this.mails = mails;
		this.location = location;
		this.lastdeath = lastdeath;
		this.onlinetime = onlinetime;
		this.lastonline = lastonline;
		
		homes = new HashMap<String, HOME>();
		reply = "";
		tpa = new HashMap<String, Double>();
		tpahere = new HashMap<String, Double>();
	}
	
	public void insert() {
		DATABASE.queue("INSERT INTO players VALUES ('" + uuid + "', '" + name + "', '" + godmode + "', " + flymode + ", '" + mails + "', '" + location + "', '" + lastdeath + "', " + onlinetime + ", " + lastonline + ")");
		DATABASE.addPlayer(uuid, this);
		DATABASE.addUUID(name, uuid);
	}
	
	public void update() {
		DATABASE.queue("UPDATE players SET name = '" + name + "', godmode = '" + godmode + "', flymode = " + flymode + ", mails = '" + mails + "', location = '" + location + "', lastdeath = '" + lastdeath + "', onlinetime = " + onlinetime + ", lastonline = " + lastonline + " WHERE uuid = '" + uuid + "'");
		DATABASE.removePlayer(uuid);
		DATABASE.removeUUID(name);
		DATABASE.addPlayer(uuid, this);
		DATABASE.addUUID(name, uuid);
	}
	
	public void delete() {
		DATABASE.queue("DELETE FROM players WHERE uuid = '" + uuid + "'");
		DATABASE.removePlayer(uuid);
		DATABASE.removeUUID(name);
	}

	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setName(String name) { this.name = name; }
	public void setGodmode(String godmode) { this.godmode = godmode; }
	public void setFlymode(double flymode) { this.flymode = flymode; }
	public void setMails(String mails) { this.mails = mails; }
	public void setLocation(String location) { this.location = location; }
	public void setLastdeath(String lastdeath) { this.lastdeath = lastdeath; }
	public void setOnlinetime(double onlinetime) { this.onlinetime = onlinetime; }
	public void setLastonline(double lastonline) { this.lastonline = lastonline; }
	
	public void setHome(String name, HOME home) { if(homes == null) homes = new HashMap<String, HOME>(); homes.put(name, home); }
	public void setHomes(HashMap<String, HOME> homes) { if(homes == null) homes = new HashMap<String, HOME>(); this.homes = homes; }
	public void setReply(String reply) { this.reply = reply; }
	public void setTPA(HashMap<String, Double> tpa) { this.tpa = tpa; }
	public void setTPAHere(HashMap<String, Double> tpahere) { this.tpahere = tpahere; }
	
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public String getGodmode() { return godmode; }
	public double getFlymode() { return flymode; }
	public String getMails() { return mails; }
	public String getLocation() { return location; }
	public String getLastdeath() { return lastdeath; }
	public double getOnlinetime() { return onlinetime; }
	public double getLastonline() { return lastonline; }
	
	public HOME getHome(String name) { if(homes == null) homes = new HashMap<String, HOME>(); return homes.containsKey(name) ? homes.get(name) : null; }
	public HashMap<String, HOME> getHomes() { if(homes == null) homes = new HashMap<String, HOME>(); return homes; }
	public String getReply() { return reply; }
	public HashMap<String, Double> getTPA() { return tpa; }
	public HashMap<String, Double> getTPAHere() { return tpahere; }
	
}
