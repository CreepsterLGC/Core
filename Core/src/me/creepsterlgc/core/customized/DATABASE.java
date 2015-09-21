package me.creepsterlgc.core.customized;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;

import org.spongepowered.api.Game;
import org.spongepowered.api.service.sql.SqlService;

public class DATABASE {
	
	public static SqlService sql;
	public static DataSource datasource;
	
	public static List<String> queue = new ArrayList<String>();
	
	public static void setup(Game game) {

		try {
			
			sql = game.getServiceManager().provide(SqlService.class).get();
			
			if(!CONFIG.MYSQL_USE()) {
				
				datasource = sql.getDataSource("jdbc:sqlite:mods/Core/Core.db");
				
			}
			else {
				
				String host = CONFIG.MYSQL_HOST();
				String port = String.valueOf(CONFIG.MYSQL_PORT());
				String username = CONFIG.MYSQL_USERNAME();
				String password = CONFIG.MYSQL_PASSWORD();
				String database = CONFIG.MYSQL_DATABASE();
				
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
				
		} catch (SQLException e) { e.printStackTrace(); }
			
		
	}
	
	public static void load(Game game) {
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM bans");
			while(rs.next()) {
				BAN ban = new BAN(rs.getString("uuid"), rs.getString("sender"), rs.getString("reason"), rs.getDouble("time"), rs.getDouble("duration"));
				DATABASE.addBan(ban.getUUID(), ban);
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
				MUTE mute = new MUTE(rs.getString("uuid"), rs.getDouble("duration"), rs.getString("reason"));
				DATABASE.addMute(mute.getUUID(), mute);
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
				PLAYER player = new PLAYER(rs.getString("uuid"), rs.getString("name"), rs.getString("nick"), rs.getString("channel"), rs.getDouble("money"), rs.getDouble("god"), rs.getDouble("fly"), rs.getDouble("tptoggle"), rs.getDouble("invisible"), rs.getDouble("onlinetime"), rs.getString("mails"), rs.getString("lastlocation"), rs.getString("lastdeath"), rs.getDouble("firstseen"), rs.getDouble("lastseen"));
				DATABASE.addPlayer(player.getUUID(), player);
				DATABASE.addUUID(player.getName(), player.getUUID());
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
				HOME home = new HOME(rs.getString("uuid"), rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"));
				PLAYER player = DATABASE.getPlayer(home.getUUID());
				player.setHome(home.getName(), home);
				DATABASE.removePlayer(home.getUUID());
				DATABASE.addPlayer(home.getUUID(), player);
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
				SPAWN spawn = new SPAWN(rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("message"));
				DATABASE.addSpawn(spawn.getName(), spawn);
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
				TICKET ticket = new TICKET((int)rs.getDouble("id"), rs.getString("uuid"), rs.getString("message"), rs.getDouble("time"), DESERIALIZE.messages(rs.getString("comments")), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("assigned"), rs.getString("priority"), rs.getString("status"));
				DATABASE.addTicket(ticket.getID(), ticket);
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
				WARP warp = new WARP(rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("owner"), DESERIALIZE.list(rs.getString("invited")), rs.getString("private"), rs.getString("message"));
				DATABASE.addWarp(warp.getName(), warp);
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
	
	public static void queue(String queue) { DATABASE.queue.add(queue); }
	
	private static HashMap<String, BAN> bans = new HashMap<String, BAN>();
	public static void addBan(String uuid, BAN ban) { if(!bans.containsKey(uuid)) bans.put(uuid, ban); }
	public static void removeBan(String uuid) { if(bans.containsKey(uuid)) bans.remove(uuid); }
	public static BAN getBan(String uuid) { return bans.containsKey(uuid) ? bans.get(uuid) : null; }
	public static HashMap<String, BAN> getBans() { return bans; }
	
	private static HashMap<String, MUTE> mutes = new HashMap<String, MUTE>();
	public static void addMute(String uuid, MUTE mute) { if(!mutes.containsKey(uuid)) mutes.put(uuid, mute); }
	public static void removeMute(String uuid) { if(mutes.containsKey(uuid)) mutes.remove(uuid); }
	public static MUTE getMute(String uuid) { return mutes.containsKey(uuid) ? mutes.get(uuid) : null; }
	public static HashMap<String, MUTE> getMutes() { return mutes; }
	
	private static HashMap<String, PLAYER> players = new HashMap<String, PLAYER>();
	public static void addPlayer(String uuid, PLAYER player) { if(!players.containsKey(players)) players.put(uuid, player); }
	public static void removePlayer(String uuid) { if(players.containsKey(uuid)) players.remove(uuid); }
	public static PLAYER getPlayer(String uuid) { return players.containsKey(uuid) ? players.get(uuid) : null; }
	public static HashMap<String, PLAYER> getPlayers() { return players; }
	
	private static HashMap<String, String> uuids = new HashMap<String, String>();
	public static void addUUID(String name, String uuid) { uuids.put(name, uuid); }
	public static void removeUUID(String name) { if(uuids.containsKey(name)) uuids.remove(name); }
	public static String getUUID(String name) { return uuids.containsKey(name) ? uuids.get(name) : null; }
	
	private static HashMap<String, SPAWN> spawns = new HashMap<String, SPAWN>();
	public static void addSpawn(String name, SPAWN spawn) { if(!spawns.containsKey(name)) spawns.put(name, spawn); }
	public static void removeSpawn(String name) { if(spawns.containsKey(name)) spawns.remove(name); }
	public static SPAWN getSpawn(String name) { return spawns.containsKey(name) ? spawns.get(name) : null; }
	public static HashMap<String, SPAWN> getSpawns() { return spawns; }
	
	private static HashMap<Integer, TICKET> tickets = new HashMap<Integer, TICKET>();
	public static void addTicket(int id, TICKET ticket) { if(!tickets.containsKey(id)) tickets.put(id, ticket); }
	public static void removeTicket(int id) { if(tickets.containsKey(id)) tickets.remove(id); }
	public static TICKET getTicket(int id) { return tickets.containsKey(id) ? tickets.get(id) : null; }
	public static HashMap<Integer, TICKET> getTickets() { return tickets; }
	public static void clearTickets() { tickets.clear(); }
	
	private static HashMap<String, WARP> warps = new HashMap<String, WARP>();
	public static void addWarp(String name, WARP warp) { if(!warps.containsKey(name)) warps.put(name, warp); }
	public static void removeWarp(String name) { if(warps.containsKey(name)) warps.remove(name); }
	public static WARP getWarp(String name) { return warps.containsKey(name) ? warps.get(name) : null; }
	public static HashMap<String, WARP> getWarps() { return warps; }

	private static HashMap<String, CHANNEL> channels = new HashMap<String, CHANNEL>();
	public static void addChannel(String name, CHANNEL channel) { if(!channels.containsKey(name)) channels.put(name, channel); }
	public static void removeChannel(String name) { if(channels.containsKey(name)) channels.remove(name); }
	public static CHANNEL getChannel(String name) { return channels.containsKey(name) ? channels.get(name) : null; }
	public static HashMap<String, CHANNEL> getChannels() { return channels; }
	
}
