package me.creepsterlgc.core;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import me.creepsterlgc.core.commands.CommandAFK;
import me.creepsterlgc.core.commands.CommandBan;
import me.creepsterlgc.core.commands.CommandBanlist;
import me.creepsterlgc.core.commands.CommandBroadcast;
import me.creepsterlgc.core.commands.CommandFeed;
import me.creepsterlgc.core.commands.CommandHeal;
import me.creepsterlgc.core.commands.CommandHome;
import me.creepsterlgc.core.commands.CommandKill;
import me.creepsterlgc.core.commands.CommandList;
import me.creepsterlgc.core.commands.CommandMemory;
import me.creepsterlgc.core.commands.CommandMessage;
import me.creepsterlgc.core.commands.CommandMute;
import me.creepsterlgc.core.commands.CommandPage;
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
import me.creepsterlgc.core.commands.CommandTicket;
import me.creepsterlgc.core.commands.CommandTime;
import me.creepsterlgc.core.commands.CommandUnban;
import me.creepsterlgc.core.commands.CommandUnmute;
import me.creepsterlgc.core.commands.CommandWarp;
import me.creepsterlgc.core.commands.CommandWeather;
import me.creepsterlgc.core.customized.COMMANDS;
import me.creepsterlgc.core.customized.CONFIG;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.SERVER;
import me.creepsterlgc.core.events.EventGameClientLogin;
import me.creepsterlgc.core.events.EventPlayerChat;
import me.creepsterlgc.core.events.EventPlayerDeath;
import me.creepsterlgc.core.events.EventPlayerJoin;
import me.creepsterlgc.core.events.EventPlayerKick;
import me.creepsterlgc.core.events.EventPlayerMove;
import me.creepsterlgc.core.events.EventPlayerQuit;
import me.creepsterlgc.core.events.EventPlayerRespawn;
import me.creepsterlgc.core.events.EventSignChange;

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
    	COMMANDS.setup();
    	DATABASE.setup(game);
    	DATABASE.load(game);
    	
    	Controller.game = game;
    	SERVER.sink = game.getServer().getBroadcastSink();
    	
    	game.getEventManager().register(this, this);
    	game.getEventManager().register(this, new EventGameClientLogin());
    	game.getEventManager().register(this, new EventPlayerChat());
    	game.getEventManager().register(this, new EventPlayerDeath());
    	game.getEventManager().register(this, new EventPlayerKick());
    	game.getEventManager().register(this, new EventPlayerMove());
    	game.getEventManager().register(this, new EventPlayerRespawn());
    	game.getEventManager().register(this, new EventPlayerQuit());
    	game.getEventManager().register(this, new EventSignChange());
    	
    	if(COMMANDS.AFK()) game.getCommandDispatcher().register(this, new CommandAFK(game), "afk");
    	if(COMMANDS.BAN()) game.getCommandDispatcher().register(this, new CommandBan(game), "ban");
    	if(COMMANDS.BANLIST()) game.getCommandDispatcher().register(this, new CommandBanlist(), "banlist");
    	if(COMMANDS.BROADCAST()) game.getCommandDispatcher().register(this, new CommandBroadcast(), "broadcast");
    	if(COMMANDS.FEED()) game.getCommandDispatcher().register(this, new CommandFeed(game), "feed");
    	if(COMMANDS.HEAL()) game.getCommandDispatcher().register(this, new CommandHeal(game), "heal");
    	if(COMMANDS.HOME()) game.getCommandDispatcher().register(this, new CommandHome(), "home");
    	if(COMMANDS.KICK()) game.getCommandDispatcher().register(this, new CommandKick(game), "kick");
    	if(COMMANDS.KILL()) game.getCommandDispatcher().register(this, new CommandKill(game), "kill");
    	if(COMMANDS.LIST()) game.getCommandDispatcher().register(this, new CommandList(game), "list", "who");
    	if(COMMANDS.MEMORY()) game.getCommandDispatcher().register(this, new CommandMemory(), "memory");
    	if(COMMANDS.MSG()) game.getCommandDispatcher().register(this, new CommandMessage(), "m", "msg", "message", "w", "whisper", "tell");
    	if(COMMANDS.MUTE()) game.getCommandDispatcher().register(this, new CommandMute(game), "mute");
    	if(COMMANDS.PING()) game.getCommandDispatcher().register(this, new CommandPing(), "ping");
    	if(COMMANDS.REPLY()) game.getCommandDispatcher().register(this, new CommandReply(), "r", "reply");
    	if(COMMANDS.SPAWN()) game.getCommandDispatcher().register(this, new CommandSpawn(), "spawn");
    	if(COMMANDS.TP()) game.getCommandDispatcher().register(this, new CommandTP(game), "tp");
    	if(COMMANDS.TPA()) game.getCommandDispatcher().register(this, new CommandTPA(game), "tpa");
    	if(COMMANDS.TPACCEPT()) game.getCommandDispatcher().register(this, new CommandTPAccept(game), "tpaccept");
    	if(COMMANDS.TPAHERE()) game.getCommandDispatcher().register(this, new CommandTPAHere(game), "tpahere");
    	if(COMMANDS.TPDEATH()) game.getCommandDispatcher().register(this, new CommandTPDeath(game), "tpdeath");
    	if(COMMANDS.TPDENY()) game.getCommandDispatcher().register(this, new CommandTPDeny(game), "tpdeny");
    	if(COMMANDS.TPHERE()) game.getCommandDispatcher().register(this, new CommandTPHere(game), "tphere");
    	if(COMMANDS.TEMPBAN()) game.getCommandDispatcher().register(this, new CommandTempban(game), "tempban");
    	if(COMMANDS.TICKET()) game.getCommandDispatcher().register(this, new CommandTicket(), "ticket");
    	if(COMMANDS.TIME()) game.getCommandDispatcher().register(this, new CommandTime(game), "time");
    	if(COMMANDS.UNBAN()) game.getCommandDispatcher().register(this, new CommandUnban(game), "unban");
    	if(COMMANDS.UNMUTE()) game.getCommandDispatcher().register(this, new CommandUnmute(game), "unmute");
    	if(COMMANDS.WARP()) game.getCommandDispatcher().register(this, new CommandWarp(), "warp");
    	if(COMMANDS.WEATHER()) game.getCommandDispatcher().register(this, new CommandWeather(game), "weather");
    	
    	game.getCommandDispatcher().register(this, new CommandPage(), "page");
    	
    	game.getScheduler().createTaskBuilder().interval(200, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		public void run() {
    			DATABASE.commit();
    		}
    	}).submit(this);
    	
    	game.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).execute(new Runnable() {
    		public void run() {
    			SERVER.heartbeat();
    		}
    	}).submit(this);
    	
    }
    
    @Subscribe
    public void onPlayerJoin(final PlayerJoinEvent event) {
    	
		PLAYER p = DATABASE.getPlayer(event.getEntity().getUniqueId().toString());
		p.setLastaction((double)System.currentTimeMillis());
		DATABASE.addPlayer(p.getUUID(), p);
		
    	event.setNewMessage(Texts.of(TextColors.YELLOW, event.getUser().getName(), TextColors.GRAY, " has joined."));
    	
    	game.getScheduler().createTaskBuilder().delay(100, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		public void run() {
    			EventPlayerJoin.onPlayerJoin(event);
    		}
    	}).submit(this);
    }
	
}
