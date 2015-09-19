package me.creepsterlgc.core;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import me.creepsterlgc.core.commands.CommandAFK;
import me.creepsterlgc.core.commands.CommandBan;
import me.creepsterlgc.core.commands.CommandBanlist;
import me.creepsterlgc.core.commands.CommandBroadcast;
import me.creepsterlgc.core.commands.CommandButcher;
import me.creepsterlgc.core.commands.CommandCore;
import me.creepsterlgc.core.commands.CommandFakejoin;
import me.creepsterlgc.core.commands.CommandFakeleave;
import me.creepsterlgc.core.commands.CommandFeed;
import me.creepsterlgc.core.commands.CommandForce;
import me.creepsterlgc.core.commands.CommandHeal;
import me.creepsterlgc.core.commands.CommandHome;
import me.creepsterlgc.core.commands.CommandKill;
import me.creepsterlgc.core.commands.CommandList;
import me.creepsterlgc.core.commands.CommandMail;
import me.creepsterlgc.core.commands.CommandMemory;
import me.creepsterlgc.core.commands.CommandMessage;
import me.creepsterlgc.core.commands.CommandMoney;
import me.creepsterlgc.core.commands.CommandMute;
import me.creepsterlgc.core.commands.CommandNick;
import me.creepsterlgc.core.commands.CommandOnlinetime;
import me.creepsterlgc.core.commands.CommandPage;
import me.creepsterlgc.core.commands.CommandPing;
import me.creepsterlgc.core.commands.CommandPowertool;
import me.creepsterlgc.core.commands.CommandRealname;
import me.creepsterlgc.core.commands.CommandReply;
import me.creepsterlgc.core.commands.CommandTP;
import me.creepsterlgc.core.commands.CommandTPA;
import me.creepsterlgc.core.commands.CommandTPAHere;
import me.creepsterlgc.core.commands.CommandTPAccept;
import me.creepsterlgc.core.commands.CommandTPDeath;
import me.creepsterlgc.core.commands.CommandTPDeny;
import me.creepsterlgc.core.commands.CommandTPHere;
import me.creepsterlgc.core.commands.CommandTPSwap;
import me.creepsterlgc.core.commands.CommandTempban;
import me.creepsterlgc.core.commands.CommandKick;
import me.creepsterlgc.core.commands.CommandSpawn;
import me.creepsterlgc.core.commands.CommandTicket;
import me.creepsterlgc.core.commands.CommandTime;
import me.creepsterlgc.core.commands.CommandUnban;
import me.creepsterlgc.core.commands.CommandUnmute;
import me.creepsterlgc.core.commands.CommandWarp;
import me.creepsterlgc.core.commands.CommandWeather;
import me.creepsterlgc.core.CoreAPI;
import me.creepsterlgc.core.customized.COMMANDS;
import me.creepsterlgc.core.customized.CONFIG;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.MESSAGES;
import me.creepsterlgc.core.customized.SERVER;
import me.creepsterlgc.core.events.EventGameClientLogin;
import me.creepsterlgc.core.events.EventPlayerAttackEntity;
import me.creepsterlgc.core.events.EventPlayerChat;
import me.creepsterlgc.core.events.EventPlayerDeath;
import me.creepsterlgc.core.events.EventPlayerInteractEntity;
import me.creepsterlgc.core.events.EventPlayerJoin;
import me.creepsterlgc.core.events.EventPlayerKick;
import me.creepsterlgc.core.events.EventPlayerMove;
import me.creepsterlgc.core.events.EventPlayerQuit;
import me.creepsterlgc.core.events.EventPlayerRespawn;
import me.creepsterlgc.core.events.EventSignChange;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.ProviderExistsException;

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
	
    @Listener
    public void onEnable(GameStartingServerEvent event) {
    	
    	File folder = new File("mods/Core");
    	if(!folder.exists()) folder.mkdir();
    	
    	CONFIG.setup();
    	COMMANDS.setup();
    	MESSAGES.setup();
    	DATABASE.setup(game);
    	DATABASE.load(game);
    	
    	Controller.game = game;
    	SERVER.sink = game.getServer().getBroadcastSink();
    	
        if (!game.getServiceManager().provide(CoreAPI.class).isPresent()) {
            try {
                game.getServiceManager().setProvider(this, CoreAPI.class, new CoreAPI());
            } catch (ProviderExistsException e) {
                logger.warning("Error while registering CoreAPI!");
            }
        }
    	
    	game.getEventManager().registerListeners(this, this);
    	game.getEventManager().registerListeners(this, new EventGameClientLogin());
    	game.getEventManager().registerListeners(this, new EventPlayerAttackEntity());
    	game.getEventManager().registerListeners(this, new EventPlayerChat());
    	game.getEventManager().registerListeners(this, new EventPlayerDeath());
    	game.getEventManager().registerListeners(this, new EventPlayerInteractEntity());
    	game.getEventManager().registerListeners(this, new EventPlayerJoin());
    	game.getEventManager().registerListeners(this, new EventPlayerKick());
    	game.getEventManager().registerListeners(this, new EventPlayerMove());
    	game.getEventManager().registerListeners(this, new EventPlayerRespawn());
    	game.getEventManager().registerListeners(this, new EventPlayerQuit());
    	game.getEventManager().registerListeners(this, new EventSignChange());
    	
    	if(COMMANDS.AFK()) game.getCommandDispatcher().register(this, new CommandAFK(), "afk");
    	if(COMMANDS.BAN()) game.getCommandDispatcher().register(this, new CommandBan(game), "ban");
    	if(COMMANDS.BANLIST()) game.getCommandDispatcher().register(this, new CommandBanlist(), "banlist");
    	if(COMMANDS.BROADCAST()) game.getCommandDispatcher().register(this, new CommandBroadcast(), "broadcast");
    	if(COMMANDS.BUTCHER()) game.getCommandDispatcher().register(this, new CommandButcher(), "butcher");
    	if(COMMANDS.CORE()) game.getCommandDispatcher().register(this, new CommandCore(), "core");
    	if(COMMANDS.FAKEJOIN()) game.getCommandDispatcher().register(this, new CommandFakejoin(), "fakejoin");
    	if(COMMANDS.FAKELEAVE()) game.getCommandDispatcher().register(this, new CommandFakeleave(), "fakeleave");
    	if(COMMANDS.FEED()) game.getCommandDispatcher().register(this, new CommandFeed(game), "feed");
    	if(COMMANDS.FORCE()) game.getCommandDispatcher().register(this, new CommandForce(game), "force", "sudo");
    	if(COMMANDS.HEAL()) game.getCommandDispatcher().register(this, new CommandHeal(game), "heal");
    	if(COMMANDS.HOME()) game.getCommandDispatcher().register(this, new CommandHome(), "home");
    	if(COMMANDS.KICK()) game.getCommandDispatcher().register(this, new CommandKick(game), "kick");
    	if(COMMANDS.KILL()) game.getCommandDispatcher().register(this, new CommandKill(game), "kill");
    	if(COMMANDS.LIST()) game.getCommandDispatcher().register(this, new CommandList(game), "list", "who");
    	if(COMMANDS.MAIL()) game.getCommandDispatcher().register(this, new CommandMail(), "mail");
    	if(COMMANDS.MEMORY()) game.getCommandDispatcher().register(this, new CommandMemory(), "memory");
    	if(COMMANDS.MSG()) game.getCommandDispatcher().register(this, new CommandMessage(), "m", "msg", "message", "w", "whisper", "tell");
    	if(COMMANDS.MONEY()) game.getCommandDispatcher().register(this, new CommandMoney(), "money");
    	if(COMMANDS.MUTE()) game.getCommandDispatcher().register(this, new CommandMute(game), "mute");
    	if(COMMANDS.NICK()) game.getCommandDispatcher().register(this, new CommandNick(), "nick");
    	if(COMMANDS.ONLINETIME()) game.getCommandDispatcher().register(this, new CommandOnlinetime(game), "onlinetime");
    	if(COMMANDS.PING()) game.getCommandDispatcher().register(this, new CommandPing(), "ping");
    	if(COMMANDS.POWERTOOL()) game.getCommandDispatcher().register(this, new CommandPowertool(game), "powertool");
    	if(COMMANDS.REALNAME()) game.getCommandDispatcher().register(this, new CommandRealname(), "realname");
    	if(COMMANDS.REPLY()) game.getCommandDispatcher().register(this, new CommandReply(), "r", "reply");
    	if(COMMANDS.SPAWN()) game.getCommandDispatcher().register(this, new CommandSpawn(), "spawn");
    	if(COMMANDS.TEMPBAN()) game.getCommandDispatcher().register(this, new CommandTempban(game), "tempban");
    	if(COMMANDS.TICKET()) game.getCommandDispatcher().register(this, new CommandTicket(), "ticket");
    	if(COMMANDS.TIME()) game.getCommandDispatcher().register(this, new CommandTime(game), "time");
    	if(COMMANDS.TP()) game.getCommandDispatcher().register(this, new CommandTP(game), "tp", "teleport");
    	if(COMMANDS.TPA()) game.getCommandDispatcher().register(this, new CommandTPA(game), "tpa");
    	if(COMMANDS.TPACCEPT()) game.getCommandDispatcher().register(this, new CommandTPAccept(game), "tpaccept");
    	if(COMMANDS.TPAHERE()) game.getCommandDispatcher().register(this, new CommandTPAHere(game), "tpahere");
    	if(COMMANDS.TPDEATH()) game.getCommandDispatcher().register(this, new CommandTPDeath(game), "tpdeath", "back");
    	if(COMMANDS.TPDENY()) game.getCommandDispatcher().register(this, new CommandTPDeny(game), "tpdeny");
    	if(COMMANDS.TPHERE()) game.getCommandDispatcher().register(this, new CommandTPHere(game), "tphere");
    	if(COMMANDS.TPSWAP()) game.getCommandDispatcher().register(this, new CommandTPSwap(game), "tpswap");
    	if(COMMANDS.UNBAN()) game.getCommandDispatcher().register(this, new CommandUnban(game), "unban");
    	if(COMMANDS.UNMUTE()) game.getCommandDispatcher().register(this, new CommandUnmute(game), "unmute");
    	if(COMMANDS.WARP()) game.getCommandDispatcher().register(this, new CommandWarp(), "warp");
    	if(COMMANDS.WEATHER()) game.getCommandDispatcher().register(this, new CommandWeather(game), "weather");
    	
    	game.getCommandDispatcher().register(this, new CommandPage(), "page");
    	
    	game.getScheduler().createTaskBuilder().interval(200, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		@Override
			public void run() {
    			DATABASE.commit();
    		}
    	}).submit(this);
    	
    	game.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).execute(new Runnable() {
    		@Override
			public void run() {
    			SERVER.heartbeat();
    		}
    	}).submit(this);
    	
    }
    
    @Listener
    public void onDisable(GameStoppingServerEvent event) {
    	DATABASE.commit();
    }
	
}
