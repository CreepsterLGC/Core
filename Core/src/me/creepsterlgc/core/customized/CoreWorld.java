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
	private boolean invincible;
	private String time;
	private String weather;
	
	public CoreWorld(String name, boolean priv, List<String> whitelist, Difficulty difficulty, GameMode gamemode, boolean monsters, boolean animals, boolean pvp, boolean build, boolean interact, Location<World> spawn, boolean hunger, boolean invincible, String time, String weather) {
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
		this.invincible = invincible;
		this.time = time;
		this.weather = weather;
	}
	
	public void update() {
		CoreDatabase.addWorld(name, this);
		FileWorlds.save(this);
	}
	
	
	public void setPrivate(boolean state) { this.priv = state; update(); }
	public void setSpawn(Location<World> spawn) { this.spawn = spawn; update(); }
	public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; update(); }
	public void setGamemode(GameMode gamemode) { this.gamemode = gamemode; update(); }
	public void setMonsterSpawning(boolean state) { this.monsters = state; update(); }
	public void setAnimalSpawning(boolean state) { this.animals = state; update(); }
	public void setPVP(boolean state) { this.pvp = state; update(); }
	public void setBuild(boolean state) { this.build = state; update(); }
	public void setInteract(boolean state) { this.interact = state; update(); }
	public void setHunger(boolean state) { this.hunger = state; update(); }
	public void setInvincible(boolean state) { this.invincible = state; update(); }
	public void setTime(String time) { this.time = time; update(); }
	public void setWeather(String weather) { this.weather = weather; update(); }
	
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
	public boolean getInvincible() { return invincible; }
	public String getTime() { return time; }
	public String getWeather() { return weather; }
	
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
