package me.creepsterlgc.core;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import me.creepsterlgc.core.commands.CommandBan;
import me.creepsterlgc.core.commands.CommandBanlist;
import me.creepsterlgc.core.commands.CommandBroadcast;
import me.creepsterlgc.core.commands.CommandFeed;
import me.creepsterlgc.core.commands.CommandHeal;
import me.creepsterlgc.core.commands.CommandHome;
import me.creepsterlgc.core.commands.CommandKill;
import me.creepsterlgc.core.commands.CommandMemory;
import me.creepsterlgc.core.commands.CommandMessage;
import me.creepsterlgc.core.commands.CommandMute;
import me.creepsterlgc.core.commands.CommandPing;
import me.creepsterlgc.core.commands.CommandReply;
import me.creepsterlgc.core.commands.CommandTP;
import me.creepsterlgc.core.commands.CommandTPA;
import me.creepsterlgc.core.commands.CommandTPAHere;
import me.creepsterlgc.core.commands.CommandTPAccept;
import me.creepsterlgc.core.commands.CommandTPDeath;
import me.creepsterlgc.core.commands.CommandTPDeny;
import me.creepsterlgc.core.commands.CommandTPHere;
import me.creepsterlgc.core.commands.CommandTempban;
import me.creepsterlgc.core.commands.CommandKick;
import me.creepsterlgc.core.commands.CommandSpawn;
import me.creepsterlgc.core.commands.CommandTime;
import me.creepsterlgc.core.commands.CommandUnban;
import me.creepsterlgc.core.commands.CommandUnmute;
import me.creepsterlgc.core.commands.CommandWarp;
import me.creepsterlgc.core.commands.CommandWeather;
import me.creepsterlgc.core.customized.CONFIG;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.SERVER;
import me.creepsterlgc.core.events.EventGameClientLogin;
import me.creepsterlgc.core.events.EventPlayerChat;
import me.creepsterlgc.core.events.EventPlayerDeath;
import me.creepsterlgc.core.events.EventPlayerJoin;
import me.creepsterlgc.core.events.EventPlayerKick;
import me.creepsterlgc.core.events.EventPlayerQuit;
import me.creepsterlgc.core.events.EventPlayerRespawn;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

@Plugin(id = "Core", name = "Core Plugin")

public class Core {

	@Inject
	private Game game;
	
	@Inject
	Logger logger;
	
	public static Core core;
	
	public static Core getInstance() { return core; }
	public Game getGame() { return game; }
	
    @Subscribe
    public void onServerStarting(ServerStartingEvent event) {
    	
    	File folder = new File("mods/Core");
    	if(!folder.exists()) folder.mkdir();
    	
    	CONFIG.setup();
    	DATABASE.setup(game);
    	DATABASE.load(game);
    	
    	Controller.game = game;
    	SERVER.sink = game.getServer().getBroadcastSink();
    	
    	game.getEventManager().register(this, this);
    	game.getEventManager().register(this, new EventGameClientLogin());
    	game.getEventManager().register(this, new EventPlayerChat());
    	game.getEventManager().register(this, new EventPlayerDeath());
    	game.getEventManager().register(this, new EventPlayerKick());
    	game.getEventManager().register(this, new EventPlayerRespawn());
    	game.getEventManager().register(this, new EventPlayerQuit());
    	
    	game.getCommandDispatcher().register(this, new CommandBan(game), "ban");
    	game.getCommandDispatcher().register(this, new CommandBanlist(), "banlist");
    	game.getCommandDispatcher().register(this, new CommandBroadcast(), "broadcast");
    	game.getCommandDispatcher().register(this, new CommandFeed(game), "feed");
    	game.getCommandDispatcher().register(this, new CommandHeal(game), "heal");
    	game.getCommandDispatcher().register(this, new CommandHome(), "home");
    	game.getCommandDispatcher().register(this, new CommandKick(game), "kick");
    	game.getCommandDispatcher().register(this, new CommandKill(game), "kill");
    	game.getCommandDispatcher().register(this, new CommandMemory(), "memory");
    	game.getCommandDispatcher().register(this, new CommandMessage(), "m", "msg", "message", "w", "whisper", "tell");
    	game.getCommandDispatcher().register(this, new CommandMute(game), "mute");
    	game.getCommandDispatcher().register(this, new CommandPing(), "ping");
    	game.getCommandDispatcher().register(this, new CommandReply(), "r", "reply");
    	game.getCommandDispatcher().register(this, new CommandSpawn(), "spawn");
    	game.getCommandDispatcher().register(this, new CommandTP(game), "tp");
    	game.getCommandDispatcher().register(this, new CommandTPA(game), "tpa");
    	game.getCommandDispatcher().register(this, new CommandTPAccept(game), "tpaccept");
    	game.getCommandDispatcher().register(this, new CommandTPAHere(game), "tpahere");
    	game.getCommandDispatcher().register(this, new CommandTPDeath(game), "tpdeath");
    	game.getCommandDispatcher().register(this, new CommandTPDeny(game), "tpdeny");
    	game.getCommandDispatcher().register(this, new CommandTPHere(game), "tphere");
    	game.getCommandDispatcher().register(this, new CommandTempban(game), "tempban");
    	game.getCommandDispatcher().register(this, new CommandTime(game), "time");
    	game.getCommandDispatcher().register(this, new CommandWarp(), "warp");
    	game.getCommandDispatcher().register(this, new CommandWeather(game), "weather");
    	game.getCommandDispatcher().register(this, new CommandUnban(game), "unban");
    	game.getCommandDispatcher().register(this, new CommandUnmute(game), "unmute");
    	
    	game.getScheduler().createTaskBuilder().interval(200, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		public void run() {
    			DATABASE.commit();
    		}
    	}).submit(this);
    	
    }
    
    @Subscribe
    public void onPlayerJoin(final PlayerJoinEvent event) {
    	
    	event.setNewMessage(Texts.of(TextColors.YELLOW, event.getUser().getName(), TextColors.GRAY, " has joined."));
    	game.getScheduler().createTaskBuilder().delay(100, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		public void run() {
    			EventPlayerJoin.onPlayerJoin(event);
    		}
    	}).submit(this);
    }
	
}
