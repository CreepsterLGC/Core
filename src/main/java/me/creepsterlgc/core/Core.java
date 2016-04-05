package main.java.me.creepsterlgc.core;

import java.io.File;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import main.java.me.creepsterlgc.core.api.CoreAPI;
import main.java.me.creepsterlgc.core.commands.CommandAFK;
import main.java.me.creepsterlgc.core.commands.CommandBan;
import main.java.me.creepsterlgc.core.commands.CommandBanlist;
import main.java.me.creepsterlgc.core.commands.CommandBroadcast;
import main.java.me.creepsterlgc.core.commands.CommandButcher;
import main.java.me.creepsterlgc.core.commands.CommandChannel;
import main.java.me.creepsterlgc.core.commands.CommandCore;
import main.java.me.creepsterlgc.core.commands.CommandEnchant;
import main.java.me.creepsterlgc.core.commands.CommandFakejoin;
import main.java.me.creepsterlgc.core.commands.CommandFakeleave;
import main.java.me.creepsterlgc.core.commands.CommandFeed;
import main.java.me.creepsterlgc.core.commands.CommandFly;
import main.java.me.creepsterlgc.core.commands.CommandForce;
import main.java.me.creepsterlgc.core.commands.CommandGamemode;
import main.java.me.creepsterlgc.core.commands.CommandGive;
import main.java.me.creepsterlgc.core.commands.CommandHeal;
import main.java.me.creepsterlgc.core.commands.CommandHome;
import main.java.me.creepsterlgc.core.commands.CommandItem;
import main.java.me.creepsterlgc.core.commands.CommandJump;
import main.java.me.creepsterlgc.core.commands.CommandKill;
import main.java.me.creepsterlgc.core.commands.CommandList;
import main.java.me.creepsterlgc.core.commands.CommandMail;
import main.java.me.creepsterlgc.core.commands.CommandMemory;
import main.java.me.creepsterlgc.core.commands.CommandMessage;
import main.java.me.creepsterlgc.core.commands.CommandMotd;
import main.java.me.creepsterlgc.core.commands.CommandMute;
import main.java.me.creepsterlgc.core.commands.CommandNick;
import main.java.me.creepsterlgc.core.commands.CommandOnlinetime;
import main.java.me.creepsterlgc.core.commands.CommandPage;
import main.java.me.creepsterlgc.core.commands.CommandPing;
import main.java.me.creepsterlgc.core.commands.CommandPowertool;
import main.java.me.creepsterlgc.core.commands.CommandRealname;
import main.java.me.creepsterlgc.core.commands.CommandReply;
import main.java.me.creepsterlgc.core.commands.CommandRules;
import main.java.me.creepsterlgc.core.commands.CommandSearchitem;
import main.java.me.creepsterlgc.core.commands.CommandSeen;
import main.java.me.creepsterlgc.core.commands.CommandSpeed;
import main.java.me.creepsterlgc.core.commands.CommandTP;
import main.java.me.creepsterlgc.core.commands.CommandTPA;
import main.java.me.creepsterlgc.core.commands.CommandTPAHere;
import main.java.me.creepsterlgc.core.commands.CommandTPAccept;
import main.java.me.creepsterlgc.core.commands.CommandTPDeath;
import main.java.me.creepsterlgc.core.commands.CommandTPDeny;
import main.java.me.creepsterlgc.core.commands.CommandTPHere;
import main.java.me.creepsterlgc.core.commands.CommandTPPos;
import main.java.me.creepsterlgc.core.commands.CommandTPSwap;
import main.java.me.creepsterlgc.core.commands.CommandTPWorld;
import main.java.me.creepsterlgc.core.commands.CommandTempban;
import main.java.me.creepsterlgc.core.commands.CommandKick;
import main.java.me.creepsterlgc.core.commands.CommandSpawn;
import main.java.me.creepsterlgc.core.commands.CommandTime;
import main.java.me.creepsterlgc.core.commands.CommandUnban;
import main.java.me.creepsterlgc.core.commands.CommandUnmute;
import main.java.me.creepsterlgc.core.commands.CommandWarp;
import main.java.me.creepsterlgc.core.commands.CommandWeather;
import main.java.me.creepsterlgc.core.commands.CommandWhois;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePortal;
import main.java.me.creepsterlgc.core.events.EventPlayerDamage;
import main.java.me.creepsterlgc.core.events.EventPlayerInteractBlock;
import main.java.me.creepsterlgc.core.events.EventPlayerLogin;
import main.java.me.creepsterlgc.core.events.EventPlayerChat;
import main.java.me.creepsterlgc.core.events.EventPlayerDeath;
import main.java.me.creepsterlgc.core.events.EventPlayerInteractEntity;
import main.java.me.creepsterlgc.core.events.EventPlayerJoin;
import main.java.me.creepsterlgc.core.events.EventPlayerKick;
import main.java.me.creepsterlgc.core.events.EventPlayerMove;
import main.java.me.creepsterlgc.core.events.EventPlayerQuit;
import main.java.me.creepsterlgc.core.events.EventPlayerRespawn;
import main.java.me.creepsterlgc.core.events.EventSignChange;
import main.java.me.creepsterlgc.core.files.FileChat;
import main.java.me.creepsterlgc.core.files.FileCommands;
import main.java.me.creepsterlgc.core.files.FileConfig;
import main.java.me.creepsterlgc.core.files.FileMessages;
import main.java.me.creepsterlgc.core.files.FileMotd;
import main.java.me.creepsterlgc.core.files.FileRules;
import main.java.me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

@Plugin(id = "core", name = "Core Plugin", version = "@project.version@")

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

    	File folder = new File("config/core");
    	if(!folder.exists()) folder.mkdir();

    	Controller.game = game;
    	ServerUtils.sink = game.getServer().getBroadcastChannel();

    	FileConfig.setup();
    	FileChat.setup();
    	FileCommands.setup();
    	FileMessages.setup();
    	FileMotd.setup();
    	FileRules.setup();

    	CoreDatabase.setup(game);
    	CoreDatabase.load(game);

        if (!game.getServiceManager().provide(CoreAPI.class).isPresent()) {
            try {
                game.getServiceManager().setProvider(this, CoreAPI.class, new CoreAPI());
            } catch (Exception e) {
                logger.warning("Error while registering CoreAPI!");
            }
        }

    	game.getEventManager().registerListeners(this, new EventPlayerLogin());
    	game.getEventManager().registerListeners(this, new EventPlayerChat());
    	game.getEventManager().registerListeners(this, new EventPlayerDamage());
    	game.getEventManager().registerListeners(this, new EventPlayerDeath());
    	game.getEventManager().registerListeners(this, new EventPlayerInteractBlock());
    	game.getEventManager().registerListeners(this, new EventPlayerInteractEntity());
    	game.getEventManager().registerListeners(this, new EventPlayerJoin());
    	game.getEventManager().registerListeners(this, new EventPlayerKick());
    	game.getEventManager().registerListeners(this, new EventPlayerMove());
    	game.getEventManager().registerListeners(this, new EventPlayerRespawn());
    	game.getEventManager().registerListeners(this, new EventPlayerQuit());
    	game.getEventManager().registerListeners(this, new EventSignChange());

    	if(FileCommands.AFK()) game.getCommandManager().register(this, new CommandAFK(), "afk");
    	if(FileCommands.BAN()) game.getCommandManager().register(this, new CommandBan(), "ban");
    	if(FileCommands.BANLIST()) game.getCommandManager().register(this, new CommandBanlist(), "banlist");
    	if(FileCommands.BROADCAST()) game.getCommandManager().register(this, new CommandBroadcast(), "broadcast");
    	if(FileCommands.BUTCHER()) game.getCommandManager().register(this, new CommandButcher(), "butcher");
    	if(FileCommands.CHANNEL()) game.getCommandManager().register(this, new CommandChannel(), "channel", "ch", "c");
    	if(FileCommands.CORE()) game.getCommandManager().register(this, new CommandCore(), "core");
    	if(FileCommands.ENCHANT()) game.getCommandManager().register(this, new CommandEnchant(game), "enchant");
    	if(FileCommands.FAKEJOIN()) game.getCommandManager().register(this, new CommandFakejoin(), "fakejoin");
    	if(FileCommands.FAKELEAVE()) game.getCommandManager().register(this, new CommandFakeleave(), "fakeleave");
    	if(FileCommands.FEED()) game.getCommandManager().register(this, new CommandFeed(), "feed");
    	if(FileCommands.FLY()) game.getCommandManager().register(this, new CommandFly(), "fly");
    	if(FileCommands.FORCE()) game.getCommandManager().register(this, new CommandForce(game), "force", "sudo");
    	if(FileCommands.GAMEMODE()) game.getCommandManager().register(this, new CommandGamemode(), "gamemode", "gm");
    	if(FileCommands.GIVE()) game.getCommandManager().register(this, new CommandGive(), "give", "g");
    	if(FileCommands.HEAL()) game.getCommandManager().register(this, new CommandHeal(game), "heal");
    	if(FileCommands.HOME()) game.getCommandManager().register(this, new CommandHome(), "home");
    	if(FileCommands.ITEM()) game.getCommandManager().register(this, new CommandItem(), "item", "i");
    	if(FileCommands.JUMP()) game.getCommandManager().register(this, new CommandJump(), "jump", "j");
    	if(FileCommands.KICK()) game.getCommandManager().register(this, new CommandKick(game), "kick");
    	if(FileCommands.KILL()) game.getCommandManager().register(this, new CommandKill(game), "kill");
    	if(FileCommands.LIST()) game.getCommandManager().register(this, new CommandList(game), "list", "who");
    	if(FileCommands.MAIL()) game.getCommandManager().register(this, new CommandMail(), "mail");
    	if(FileCommands.MEMORY()) game.getCommandManager().register(this, new CommandMemory(), "memory");
    	if(FileCommands.MSG()) game.getCommandManager().register(this, new CommandMessage(), "m", "msg", "message", "w", "whisper", "tell");
    	if(FileCommands.MOTD()) game.getCommandManager().register(this, new CommandMotd(), "motd");
    	if(FileCommands.MUTE()) game.getCommandManager().register(this, new CommandMute(game), "mute");
    	if(FileCommands.NICK()) game.getCommandManager().register(this, new CommandNick(), "nick");
    	if(FileCommands.ONLINETIME()) game.getCommandManager().register(this, new CommandOnlinetime(game), "onlinetime");
    	if(FileCommands.PING()) game.getCommandManager().register(this, new CommandPing(), "ping");
    	if(FileCommands.POWERTOOL()) game.getCommandManager().register(this, new CommandPowertool(game), "powertool");
    	if(FileCommands.REALNAME()) game.getCommandManager().register(this, new CommandRealname(), "realname");
    	if(FileCommands.REPLY()) game.getCommandManager().register(this, new CommandReply(), "r", "reply");
    	if(FileCommands.RULES()) game.getCommandManager().register(this, new CommandRules(), "rules");
    	if(FileCommands.SEARCHITEM()) game.getCommandManager().register(this, new CommandSearchitem(), "searchitem", "si", "search");
    	if(FileCommands.SEEN()) game.getCommandManager().register(this, new CommandSeen(game), "seen");
    	if(FileCommands.SPAWN()) game.getCommandManager().register(this, new CommandSpawn(), "spawn");
    	if(FileCommands.SPEED()) game.getCommandManager().register(this, new CommandSpeed(), "speed");
    	if(FileCommands.TEMPBAN()) game.getCommandManager().register(this, new CommandTempban(game), "tempban");
    	if(FileCommands.TIME()) game.getCommandManager().register(this, new CommandTime(game), "time");
    	if(FileCommands.TP()) game.getCommandManager().register(this, new CommandTP(game), "tp", "teleport");
    	if(FileCommands.TPA()) game.getCommandManager().register(this, new CommandTPA(game), "tpa");
    	if(FileCommands.TPACCEPT()) game.getCommandManager().register(this, new CommandTPAccept(game), "tpaccept");
    	if(FileCommands.TPAHERE()) game.getCommandManager().register(this, new CommandTPAHere(game), "tpahere");
    	if(FileCommands.TPDEATH()) game.getCommandManager().register(this, new CommandTPDeath(game), "tpdeath", "back");
    	if(FileCommands.TPDENY()) game.getCommandManager().register(this, new CommandTPDeny(game), "tpdeny");
    	if(FileCommands.TPHERE()) game.getCommandManager().register(this, new CommandTPHere(game), "tphere");
    	if(FileCommands.TPPOS()) game.getCommandManager().register(this, new CommandTPPos(game), "tppos");
    	if(FileCommands.TPSWAP()) game.getCommandManager().register(this, new CommandTPSwap(game), "tpswap");
    	if(FileCommands.TPWORLD()) game.getCommandManager().register(this, new CommandTPWorld(game), "tpworld");
    	if(FileCommands.UNBAN()) game.getCommandManager().register(this, new CommandUnban(game), "unban");
    	if(FileCommands.UNMUTE()) game.getCommandManager().register(this, new CommandUnmute(game), "unmute");
    	if(FileCommands.WARP()) game.getCommandManager().register(this, new CommandWarp(), "warp");
    	if(FileCommands.WEATHER()) game.getCommandManager().register(this, new CommandWeather(game), "weather");
    	if(FileCommands.WHOIS()) game.getCommandManager().register(this, new CommandWhois(), "whois", "check");

    	game.getCommandManager().register(this, new CommandPage(), "page");

    	game.getScheduler().createTaskBuilder().interval(200, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		@Override
			public void run() {
    			CoreDatabase.commit();
    		}
    	}).submit(this);

    	game.getScheduler().createTaskBuilder().interval(200, TimeUnit.MILLISECONDS).execute(new Runnable() {
    		@Override
			public void run() {
    			for(Entry<String, CorePortal> e : CoreDatabase.getPortals().entrySet()) e.getValue().transport();
    		}
    	}).submit(this);

    	game.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).execute(new Runnable() {
    		@Override
			public void run() {
    			ServerUtils.heartbeat();
    		}
    	}).submit(this);

    }

    @Listener
    public void onDisable(GameStoppingServerEvent event) {
    	CoreDatabase.commit();
    }

}
