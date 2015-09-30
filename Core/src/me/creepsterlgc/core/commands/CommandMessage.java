package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CoreMute;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.customized.CoreServer;
import me.creepsterlgc.core.utils.CommandUtils;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandMessage implements CommandCallable {
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		String[] args = arguments.split(" ");
		
		if(!PermissionsUtils.has(sender, "core.msg")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(args.length < 2) { sender.sendMessage(Texts.builder("Usage: /msg <player> <message>").color(TextColors.YELLOW).build()); return CommandResult.success(); }
		
		String message = CommandUtils.combineArgs(1, args);
		
		if(sender instanceof Player) {
			
			Player checking = (Player) sender;
			
	    	CoreMute mute = CoreDatabase.getMute(checking.getUniqueId().toString());
	    	
	    	if(mute != null) {
	    		
	    		if(mute.getDuration() != 0 && mute.getDuration() <= System.currentTimeMillis()) {
	    			CoreDatabase.removeMute(checking.getUniqueId().toString());
	    			mute.delete();
	    		}
	    		else {
		    		checking.sendMessage(Texts.of(TextColors.RED, mute.getReason()));
		    		return CommandResult.success();
	    		}
	    	}
		}
		
		Player player = CoreServer.getPlayer(args[0]);
		
		if(player == null) {
			sender.sendMessage(Texts.of(TextColors.RED, "Player not found!"));
			return CommandResult.success();
		}
		
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		if(sender instanceof Player) p.setReply(sender.getName().toLowerCase());
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, "To ", player.getName(), ": ", TextColors.WHITE, message));
		player.sendMessage(Texts.of(TextColors.YELLOW, "From ", sender.getName(), ": ", TextColors.WHITE, message));
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /msg").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /msg").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Message Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	@Override
	public Text getUsage(CommandSource sender) { return usage; }
	@Override
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	@Override
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	@Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	@Override
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
