package me.creepsterlgc.core.commands;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.CHANNEL;
import me.creepsterlgc.core.customized.COMMAND;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;
import me.creepsterlgc.core.customized.TEXT;
import me.creepsterlgc.core.files.CONFIG;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class CommandChannelSpeak {

	public CommandChannelSpeak(CommandSource sender, String[] args) {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }
		
		if(args.length < 2) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/channel <channel> <mesage>")); return; }
	
		String channel = args[0].toLowerCase();
		CHANNEL c = DATABASE.getChannel(channel);
		
		if(c == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Channel not found!"));
			return;
		}
		
		if(!PERMISSIONS.has(sender, "core.channel.leave." + channel)) {
			sender.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to leave this channel!"));
			return;
		}
		
		Player player = (Player) sender;
		PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
		
    	String name = player.getName();
		String message = COMMAND.combineArgs(1, args);
    	if(!p.getNick().equalsIgnoreCase("")) name = CONFIG.CHAT_NICK_PREFIX() + p.getNick();
    	if(!PERMISSIONS.has(player, "core.chat.color")) { message = TEXT.uncolor(message); }
    	
    	String prefix = TEXT.getPrefix(player);
    	String suffix = TEXT.getSuffix(player);
    		
    	if(!PERMISSIONS.has(player, "core.channel.speak." + c.getID())) {
    		player.sendMessage(Texts.of(TextColors.RED, "You do not have permissions to speak in this channel!"));
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
	    	
	    Text total = TEXT.color(format);
	    	
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
		
	}

}
