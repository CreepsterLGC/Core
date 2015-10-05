package me.creepsterlgc.core.events;

import java.util.Optional;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CoreChannel;
import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreMute;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.files.FileChat;
import me.creepsterlgc.core.files.FileConfig;
import me.creepsterlgc.core.utils.PermissionsUtils;
import me.creepsterlgc.core.utils.TextUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.MessageSinkEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class EventPlayerChat {

    @Listener
    public void onPlayerChat(MessageSinkEvent.Chat event) {

    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	Player player = optional.get();
    	String uuid = player.getUniqueId().toString();
    	CorePlayer p = CoreDatabase.getPlayer(uuid);
    	
    	CoreMute mute = CoreDatabase.getMute(uuid);
    	
    	if(mute != null) {
    		
    		if(mute.getDuration() != 0 && mute.getDuration() <= System.currentTimeMillis()) {
    			CoreDatabase.removeMute(player.getUniqueId().toString());
    			mute.delete();
    		}
    		else {
	    		player.sendMessage(Texts.of(TextColors.RED, mute.getReason()));
	    		event.setCancelled(true);
	    		return;
    		}
    		
    	}
    	
    	if(FileConfig.AFK_ENABLE_SYSTEM()) {
    	
			p.setLastaction(System.currentTimeMillis());
			
			if(p.getAFK()) {
				Controller.broadcast(Texts.of(TextColors.YELLOW, player.getName(), TextColors.GRAY, " is no longer afk."));
				p.setAFK(false);
			}
			
			CoreDatabase.addPlayer(p.getUUID(), p);
		
    	}
    	
		if(!FileChat.USE()) return;
    	
    	String name = player.getName();
    	String message = Texts.toPlain(event.getMessage()); message = message.replaceAll("<" + name + "> ", "");
    	if(!p.getNick().equalsIgnoreCase("")) name = FileConfig.CHAT_NICK_PREFIX() + p.getNick();
    	if(!PermissionsUtils.has(player, "core.chat.color")) { message = TextUtils.uncolor(message); }
    	
    	String prefix = PermissionsUtils.getPrefix(player);
    	String suffix = PermissionsUtils.getSuffix(player);
		
    	if(!FileChat.CHANNELS()) {
    	
	    	String format = FileChat.DEFAULTFORMAT();
	    	
	    	format = format
	    			.replaceAll("%prefix", prefix)
	    			.replaceAll("%suffix", suffix)
	    			.replaceAll("%player", name)
	    			.replaceAll("%message", message);
	    	
	    	Text total = TextUtils.color(format);
	    	
	    	event.setMessage(Texts.of(total));
	    	
    	}
    	else {
    		
    		String channel = p.getChannel();
    		CoreChannel c = CoreDatabase.getChannel(channel);
    		if(c == null) c = CoreDatabase.getChannel(FileChat.DEFAULTCHANNEL());
    		
    		if(!PermissionsUtils.has(player, "core.channel.speak." + c.getID())) {
    			player.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to speak in this channel!"));
    			event.setCancelled(true);
    			return;
    		}
    		
    		String cprefix = c.getPrefix();
    		String csuffix = c.getSuffix();
    		
	    	String format = c.getFormat();
	    	
	    	format = format
	    			.replaceAll("%prefix", prefix)
	    			.replaceAll("%suffix", suffix)
	    			.replaceAll("%player", name)
	    			.replaceAll("%message", message)
	    			.replaceAll("%cprefix", cprefix)
					.replaceAll("%csuffix", csuffix)
	    			.replaceAll("%world", player.getWorld().getName());
	    	
	    	Text total = TextUtils.color(format);
	    	
	    	String range = c.getRange();
	    	
	    	if(range.equalsIgnoreCase("global")) {
	    		for(Player t : Controller.getPlayers()) {
	    			if(!t.hasPermission("core.channel.receive." + channel)) continue;
	    			t.sendMessage(total);
	    		}
	    	}
	    	else if(range.equalsIgnoreCase("world")) {
	    		for(Player t : Controller.getPlayers()) {
	    			if(!t.getWorld().getName().equalsIgnoreCase(player.getWorld().getName())) continue;
	    			if(!t.hasPermission("core.channel.receive." + channel)) continue;
	    			t.sendMessage(total);
	    		}
	    	}
	    	else {
	    		int radius;
	    		try { radius = Integer.parseInt(c.getRange()); }
	    		catch(NumberFormatException e) {
	    			player.sendMessage(Texts.of(TextColors.RED, "Invalid range in channels config!"));
	    	    	event.setCancelled(true);
	    			return;
	    		}
	    		for(Player t : Controller.getPlayers()) {
	    			if(!t.getWorld().getName().equalsIgnoreCase(player.getWorld().getName())) continue;
	    			
					Location<World> l = t.getLocation();
					double x = player.getLocation().getX();
					double z = player.getLocation().getZ();
					boolean hit_x = false;
					boolean hit_z = false;
					if(l.getX() <= x + radius && l.getX() >= x - radius) hit_x = true;
					if(l.getZ() <= z + radius && l.getZ() >= z - radius) hit_z = true;
					if(!hit_x || !hit_z) continue;
					
	    			if(!t.hasPermission("core.channel.receive." + channel)) continue;
	    			t.sendMessage(total);
	    		}
	    	}
	    	
	    	event.setCancelled(true);

    	}
    	
    }
	
}
