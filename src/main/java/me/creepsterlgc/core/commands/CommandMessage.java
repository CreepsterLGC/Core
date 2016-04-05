package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CoreMute;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.CommandUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.ServerUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandMessage implements CommandCallable {

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(!PermissionsUtils.has(sender, "core.msg")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(args.length < 2) { sender.sendMessage(Text.builder("Usage: /msg <player> <message>").color(TextColors.YELLOW).build()); return CommandResult.success(); }

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
		    		checking.sendMessage(Text.of(TextColors.RED, mute.getReason()));
		    		return CommandResult.success();
	    		}
	    	}
		}

		Player player = ServerUtils.getPlayer(args[0]);

		if(player == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Player not found!"));
			return CommandResult.success();
		}

		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		if(sender instanceof Player) p.setReply(sender.getName().toLowerCase());

		sender.sendMessage(Text.of(TextColors.YELLOW, "To ", player.getName(), ": ", TextColors.WHITE, message));
		player.sendMessage(Text.of(TextColors.YELLOW, "From ", sender.getName(), ": ", TextColors.WHITE, message));

		return CommandResult.success();

	}

	private final Text usage = Text.builder("Usage: /msg").color(TextColors.YELLOW).build();
	private final Text help = Text.builder("Help: /msg").color(TextColors.YELLOW).build();
	private final Text description = Text.builder("Core | Message Command").color(TextColors.YELLOW).build();
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
