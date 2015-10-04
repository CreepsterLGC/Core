package me.creepsterlgc.core.customized;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.files.FileWorlds;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.difficulty.Difficulty;

public class CoreWorld {

	private String name;
	private boolean priv;
	private List<String> whitelist;
	private Difficulty difficulty;
	private GameMode gamemode;
	private boolean monsters;
	private boolean animals;
	private boolean pvp;
	private boolean build;
	private boolean interact;
	private Location<World> spawn;
	private boolean hunger;
	private boolean invulnerability;
	private String time;
	private String weather;
	private double border;
	
	public CoreWorld(String name, boolean priv, List<String> whitelist, Difficulty difficulty, GameMode gamemode, boolean monsters, boolean animals, boolean pvp, boolean build, boolean interact, Location<World> spawn, boolean hunger, boolean invulnerability, String time, String weather, double border) {
		this.name = name;
		this.priv = priv;
		this.whitelist = whitelist;
		this.difficulty = difficulty;
		this.gamemode = gamemode;
		this.monsters = monsters;
		this.animals = animals;
		this.pvp = pvp;
		this.build = build;
		this.interact = interact;
		this.spawn = spawn;
		this.hunger = hunger;
		this.invulnerability = invulnerability;
		this.time = time;
		this.weather = weather;
		this.border = border;
	}
	
	public void update() {
		CoreDatabase.addWorld(name, this);
		FileWorlds.save(this);
		if(!Controller.getServer().getWorld(name).isPresent()) return;
		World world = Controller.getServer().getWorld(name).get();
		world.getProperties().setDifficulty(difficulty);
		world.getProperties().setGameMode(gamemode);
		world.getProperties().setSpawnPosition(spawn.getBlockPosition());
	}
	
	
	public void setPrivate(boolean state) { this.priv = state; update(); }
	public void setSpawn(Location<World> spawn) { this.spawn = spawn; update(); }
	public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; update(); }
	public void setGamemode(GameMode gamemode) { this.gamemode = gamemode; update(); }
	public void setTime(String time) { this.time = time; update(); }
	public void setWeather(String weather) { this.weather = weather; update(); }
	public void setBorder(double border) { this.border = border; update(); }
	
	public void allowMonsterSpawning(boolean state) { this.monsters = state; update(); }
	public void allowAnimalSpawning(boolean state) { this.animals = state; update(); }
	public void allowPVP(boolean state) { this.pvp = state; update(); }
	public void allowBuild(boolean state) { this.build = state; update(); }
	public void allowInteract(boolean state) { this.interact = state; update(); }
	public void allowHunger(boolean state) { this.hunger = state; update(); }
	public void allowInvulnerability(boolean state) { this.invulnerability = state; update(); }
	
	public String getName() { return name; }
	public List<String> getWhitelist() { return whitelist; }
	public Location<World> getSpawn() { return spawn; }
	public Difficulty getDifficulty() { return difficulty; }
	public GameMode getGamemode() { return gamemode; }
	public boolean getMonsterSpawning() { return monsters; }
	public boolean getAnimalSpawning() { return animals; }
	public boolean getPVP() { return pvp; }
	public boolean getBuild() { return build; }
	public boolean getInteract() { return interact; }
	public boolean getHunger() { return hunger; }
	public boolean getInvulnerability() { return invulnerability; }
	public String getTime() { return time; }
	public String getWeather() { return weather; }
	public double getBorder() { return border; }
	
	public void addWhitelist(String uuid) {
		if(!whitelist.contains(uuid)) whitelist.add(uuid);
		update();
	}

	public void addWhitelist(Player player) {
		String uuid = player.getUniqueId().toString();
		if(!whitelist.contains(uuid)) whitelist.add(uuid);
		update();
	}
	
	public void removeWhitelist(String uuid) {
		if(whitelist.contains(uuid)) whitelist.remove(uuid);
		update();
	}
	
	public void removeWhitelist(Player player) {
		String uuid = player.getUniqueId().toString();
		if(whitelist.contains(uuid)) whitelist.remove(uuid);
		update();
	}
	
	public boolean isPrivate() {
		return priv;
	}
	
	public boolean isWhitelisted(String uuid) {
		return whitelist.contains(uuid);
	}
	
	public boolean isWhitelisted(Player player) {
		return whitelist.contains(player.getUniqueId().toString());
	}
	
	public boolean hasBorder() {
		return border > 0;
	}

	public void teleport(Player player) {
		player.setLocation(spawn);
	}
	
	public List<Player> getPlayers() { 
		List<Player> players = new ArrayList<Player>();
		for(Player player : Controller.getPlayers()) {
			if(!player.getWorld().getName().equalsIgnoreCase(name)) continue;
			players.add(player);
		}
		return players;
	}
	
}
