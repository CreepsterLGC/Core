package main.java.me.creepsterlgc.core.customized;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;

import main.java.me.creepsterlgc.core.files.FileConfig;
import main.java.me.creepsterlgc.core.utils.DeserializeUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.service.sql.SqlService;

public class CoreDatabase {
	
	public static SqlService sql;
	public static DataSource datasource;
	
	public static List<String> queue = new ArrayList<String>();
	
	public static void setup(Game game) {

		try {
			
			sql = game.getServiceManager().provide(SqlService.class).get();
			
			if(!FileConfig.MYSQL_USE()) {
				
		    	File folder = new File("config/core/data");
		    	if(!folder.exists()) folder.mkdir();
		    	
				datasource = sql.getDataSource("jdbc:sqlite:config/core/data/core.db");
				
			}
			else {
				
				String host = FileConfig.MYSQL_HOST();
				String port = String.valueOf(FileConfig.MYSQL_PORT());
				String username = FileConfig.MYSQL_USERNAME();
				String password = FileConfig.MYSQL_PASSWORD();
				String database = FileConfig.MYSQL_DATABASE();
				
				datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);
				
			}
			
			DatabaseMetaData metadata = datasource.getConnection().getMetaData();
			ResultSet resultset = metadata.getTables(null, null, "%", null);
			
			List<String> tables = new ArrayList<String>();		
			while(resultset.next()) {
				tables.add(resultset.getString(3));
			}

			if(!tables.contains("bans")) {
				execute("CREATE TABLE bans (uuid TEXT, sender TEXT, reason TEXT, time DOUBLE, duration DOUBLE)");
			}
			
			if(!tables.contains("homes")) {
				execute("CREATE TABLE homes (uuid TEXT, name TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE)");
			}
			
			if(!tables.contains("mutes")) {
				execute("CREATE TABLE mutes (uuid TEXT, duration DOUBLE, reason TEXT)");
			}
			
			if(!tables.contains("players")) {
				execute("CREATE TABLE players (uuid TEXT, name TEXT, nick TEXT, channel TEXT, money DOUBLE, god DOUBLE, fly DOUBLE, tptoggle DOUBLE, invisible DOUBLE, onlinetime DOUBLE, mails TEXT, lastlocation TEXT, lastdeath TEXT, firstseen DOUBLE, lastseen DOUBLE)");
			}
			
			if(!tables.contains("spawns")) {
				execute("CREATE TABLE spawns (name TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, message TEXT)");
			}
			
			if(!tables.contains("tickets")) {
				execute("CREATE TABLE tickets (id DOUBLE, uuid TEXT, message TEXT, time DOUBLE, comments TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, assigned TEXT, priority TEXT, status TEXT)");
			}
			
			if(!tables.contains("warps")) {
				execute("CREATE TABLE warps (name TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, owner TEXT, invited TEXT, private TEXT, message TEXT)");
			}
			
			if(!tables.contains("zones")) {
				execute("CREATE TABLE zones (name TEXT, world TEXT, x1 DOUBLE, y1 DOUBLE, z1 DOUBLE, x2 DOUBLE, y2 DOUBLE, z2 DOUBLE, priority DOUBLE, owner TEXT, members TEXT, settings TEXT)");
			}
			
			if(!tables.contains("portals")) {
				execute("CREATE TABLE portals (name TEXT, zone TEXT, warp TEXT, message TEXT)");
			}
				
		} catch (SQLException e) { e.printStackTrace(); }
			
		
	}
	
	public static void load(Game game) {
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM bans");
			while(rs.next()) {
				CoreBan ban = new CoreBan(rs.getString("uuid"), rs.getString("sender"), rs.getString("reason"), rs.getDouble("time"), rs.getDouble("duration"));
				CoreDatabase.addBan(ban.getUUID(), ban);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM mutes");
			while(rs.next()) {
				CoreMute mute = new CoreMute(rs.getString("uuid"), rs.getDouble("duration"), rs.getString("reason"));
				CoreDatabase.addMute(mute.getUUID(), mute);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM players");
			while(rs.next()) {
				CorePlayer player = new CorePlayer(rs.getString("uuid"), rs.getString("name"), rs.getString("nick"), rs.getString("channel"), rs.getDouble("money"), rs.getDouble("god"), rs.getDouble("fly"), rs.getDouble("tptoggle"), rs.getDouble("invisible"), rs.getDouble("onlinetime"), rs.getString("mails"), rs.getString("lastlocation"), rs.getString("lastdeath"), rs.getDouble("firstseen"), rs.getDouble("lastseen"));
				CoreDatabase.addPlayer(player.getUUID(), player);
				CoreDatabase.addUUID(player.getName(), player.getUUID());
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM homes");
			while(rs.next()) {
				CoreHome home = new CoreHome(rs.getString("uuid"), rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"));
				CorePlayer player = CoreDatabase.getPlayer(home.getUUID());
				player.setHome(home.getName(), home);
				CoreDatabase.removePlayer(home.getUUID());
				CoreDatabase.addPlayer(home.getUUID(), player);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM spawns");
			while(rs.next()) {
				CoreSpawn spawn = new CoreSpawn(rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("message"));
				CoreDatabase.addSpawn(spawn.getName(), spawn);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM tickets");
			while(rs.next()) {
				CoreTicket ticket = new CoreTicket((int)rs.getDouble("id"), rs.getString("uuid"), rs.getString("message"), rs.getDouble("time"), DeserializeUtils.messages(rs.getString("comments")), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("assigned"), rs.getString("priority"), rs.getString("status"));
				CoreDatabase.addTicket(ticket.getID(), ticket);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM warps");
			while(rs.next()) {
				CoreWarp warp = new CoreWarp(rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("owner"), DeserializeUtils.list(rs.getString("invited")), rs.getString("private"), rs.getString("message"));
				CoreDatabase.addWarp(warp.getName(), warp);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM zones");
			while(rs.next()) {
				CoreZone zone = new CoreZone(rs.getString("name"), rs.getString("world"), rs.getDouble("x1"), rs.getDouble("y1"), rs.getDouble("z1"), rs.getDouble("x2"), rs.getDouble("y2"), rs.getDouble("z2"), rs.getDouble("priority"), rs.getString("owner"), DeserializeUtils.members(rs.getString("members")), DeserializeUtils.settings(rs.getString("settings")));
				CoreDatabase.addZone(zone.getName(), zone);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM portals");
			while(rs.next()) {
				CorePortal portal = new CorePortal(rs.getString("name"), rs.getString("zone"), rs.getString("warp"), rs.getString("message"));
				CoreDatabase.addPortal(portal.getName(), portal);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void execute(String execute) {	
		try {
			
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute(execute);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(List<String> execute) {	
		try {
		
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			for(String e : execute) statement.execute(e);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commit() {
		
		if(queue.isEmpty()) return;
		execute(queue);
		queue.clear();
		
	}
	
	public static void queue(String queue) { CoreDatabase.queue.add(queue); }
	
	private static HashMap<String, CoreBan> bans = new HashMap<String, CoreBan>();
	public static void addBan(String uuid, CoreBan ban) { if(!bans.containsKey(uuid)) bans.put(uuid, ban); }
	public static void removeBan(String uuid) { if(bans.containsKey(uuid)) bans.remove(uuid); }
	public static CoreBan getBan(String uuid) { return bans.containsKey(uuid) ? bans.get(uuid) : null; }
	public static HashMap<String, CoreBan> getBans() { return bans; }
	
	private static HashMap<String, CoreMute> mutes = new HashMap<String, CoreMute>();
	public static void addMute(String uuid, CoreMute mute) { if(!mutes.containsKey(uuid)) mutes.put(uuid, mute); }
	public static void removeMute(String uuid) { if(mutes.containsKey(uuid)) mutes.remove(uuid); }
	public static CoreMute getMute(String uuid) { return mutes.containsKey(uuid) ? mutes.get(uuid) : null; }
	public static HashMap<String, CoreMute> getMutes() { return mutes; }
	
	private static HashMap<String, CorePlayer> players = new HashMap<String, CorePlayer>();
	public static void addPlayer(String uuid, CorePlayer player) { if(!players.containsKey(players)) players.put(uuid, player); }
	public static void removePlayer(String uuid) { if(players.containsKey(uuid)) players.remove(uuid); }
	public static CorePlayer getPlayer(String uuid) { return players.containsKey(uuid) ? players.get(uuid) : null; }
	public static HashMap<String, CorePlayer> getPlayers() { return players; }
	
	private static HashMap<String, String> uuids = new HashMap<String, String>();
	public static void addUUID(String name, String uuid) { uuids.put(name, uuid); }
	public static void removeUUID(String name) { if(uuids.containsKey(name)) uuids.remove(name); }
	public static String getUUID(String name) { return uuids.containsKey(name) ? uuids.get(name) : null; }
	
	private static HashMap<String, CoreSpawn> spawns = new HashMap<String, CoreSpawn>();
	public static void addSpawn(String name, CoreSpawn spawn) { if(!spawns.containsKey(name)) spawns.put(name, spawn); }
	public static void removeSpawn(String name) { if(spawns.containsKey(name)) spawns.remove(name); }
	public static CoreSpawn getSpawn(String name) { return spawns.containsKey(name) ? spawns.get(name) : null; }
	public static HashMap<String, CoreSpawn> getSpawns() { return spawns; }
	
	private static HashMap<Integer, CoreTicket> tickets = new HashMap<Integer, CoreTicket>();
	public static void addTicket(int id, CoreTicket ticket) { if(!tickets.containsKey(id)) tickets.put(id, ticket); }
	public static void removeTicket(int id) { if(tickets.containsKey(id)) tickets.remove(id); }
	public static CoreTicket getTicket(int id) { return tickets.containsKey(id) ? tickets.get(id) : null; }
	public static HashMap<Integer, CoreTicket> getTickets() { return tickets; }
	public static void clearTickets() { tickets.clear(); }
	
	private static HashMap<String, CoreWarp> warps = new HashMap<String, CoreWarp>();
	public static void addWarp(String name, CoreWarp warp) { if(!warps.containsKey(name)) warps.put(name, warp); }
	public static void removeWarp(String name) { if(warps.containsKey(name)) warps.remove(name); }
	public static CoreWarp getWarp(String name) { return warps.containsKey(name) ? warps.get(name) : null; }
	public static HashMap<String, CoreWarp> getWarps() { return warps; }
	
	private static HashMap<String, CoreZone> zones = new HashMap<String, CoreZone>();
	public static void addZone(String name, CoreZone zone) { zones.put(name, zone); }
	public static void removeZone(String name) { if(zones.containsKey(name)) zones.remove(name); }
	public static CoreZone getZone(String name) { return zones.containsKey(name) ? zones.get(name) : null; }
	public static HashMap<String, CoreZone> getZones() { return zones; }
	
	private static HashMap<String, CorePortal> portals = new HashMap<String, CorePortal>();
	public static void addPortal(String name, CorePortal portal) { portals.put(name, portal); }
	public static void removePortal(String name) { if(portals.containsKey(name)) portals.remove(name); }
	public static CorePortal getPortal(String name) { return portals.containsKey(name) ? portals.get(name) : null; }
	public static HashMap<String, CorePortal> getPortals() { return portals; }
	
}
