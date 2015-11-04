package me.creepsterlgc.core.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import me.creepsterlgc.core.customized.CoreDatabase;
import me.creepsterlgc.core.customized.CorePlayer;
import me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;


public class CommandPowertool implements CommandCallable {
	
	public Game game;
	
	public CommandPowertool(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		if(!PermissionsUtils.has(sender, "core.powertool")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());
		
		if(!player.getItemInHand().isPresent()) {
			sender.sendMessage(Texts.of(TextColors.RED, "You need to have an item in your hand!"));
			return CommandResult.success();
		}
		
		ItemStack i = player.getItemInHand().get();
		
		if(!i.getItem().equals(ItemTypes.GOLDEN_AXE)) {
			sender.sendMessage(Texts.of(TextColors.RED, "You cannot use this item!"));
			return CommandResult.success();
		}
		
		String id = i.getItem().getId().replaceAll("minecraft:", "");
		
		HashMap<String, String> powertools = p.getPowertools();
		
		if(arguments.equalsIgnoreCase("")) {
			if(!powertools.containsKey(id)) {
				sender.sendMessage(Texts.of(TextColors.RED, "This item is not bound!"));
				return CommandResult.success();
			}
			
			powertools.remove(id);
			p.setPowertools(powertools);
			CoreDatabase.addPlayer(p.getUUID(), p);
			
			sender.sendMessage(Texts.of(TextColors.YELLOW, id, TextColors.GRAY, " is not a powertool anymore."));
			
			return CommandResult.success();
		}
		
		String command = arguments;
		command = command.replaceAll("/", "");
		
		sender.sendMessage(Texts.of(TextColors.YELLOW, id, TextColors.GRAY, " is now a powertool."));
		sender.sendMessage(Texts.of(TextColors.GRAY, "Command: ", TextColors.YELLOW, "/", command));
		
		powertools.put(id, command);
		p.setPowertools(powertools);
		CoreDatabase.addPlayer(p.getUUID(), p);
		
		return CommandResult.success();
		
	}
	
	 @Override
	public Text getUsage(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getHelp(CommandSource sender) { return null; }
	 @Override
	public Optional<Text> getShortDescription(CommandSource sender) { return null; }
	 @Override
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return null; }
	 @Override
	public boolean testPermission(CommandSource sender) { return false; }

}
