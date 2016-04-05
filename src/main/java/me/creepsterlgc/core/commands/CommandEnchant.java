package main.java.me.creepsterlgc.core.commands;

import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.utils.CommandUtils;
import main.java.me.creepsterlgc.core.utils.ItemUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.Game;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;


public class CommandEnchant implements CommandCallable {

	public Game game;

	public CommandEnchant(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {

		String[] args = arguments.split(" ");

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(!PermissionsUtils.has(sender, "core.enchant")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return CommandResult.success(); }

		if(arguments.equalsIgnoreCase("")) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/enchant <enchantment> <level>")); return CommandResult.success(); }
		if(args.length < 1 || args.length > 2) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/enchant <enchantment> <level>")); return CommandResult.success(); }

		Player player = (Player) sender;

		if(!player.getItemInHand().isPresent()) {
			sender.sendMessage(Text.of(TextColors.RED, "You need to have an item in your hand!"));
			return CommandResult.success();
		}

		ItemStack i = player.getItemInHand().get();
		EnchantmentData d = i.getOrCreate(EnchantmentData.class).get();

		Enchantment e = ItemUtils.getEnchantment(args[0]);

		if(e == null) {
			sender.sendMessage(Text.of(TextColors.RED, "Enchantment not found!"));
			return CommandResult.success();
		}

		if(!e.canBeAppliedToStack(i)) {
			sender.sendMessage(Text.of(TextColors.RED, "Enchantment is not compatible with this item!"));
			return CommandResult.success();
		}

		if(!CommandUtils.isInt(args[1])) {
			sender.sendMessage(Text.of(TextColors.RED, "<level> has to be a number!"));
			return CommandResult.success();
		}

		int level = CommandUtils.getInt(args[1]);

		for(ItemEnchantment ie : d.enchantments()) if(ie.getEnchantment().getId().equals(e.getId())) d.enchantments().remove(ie);
		d.set(d.enchantments().add(new ItemEnchantment(e, level))); i.offer(d);

		player.setItemInHand(i);

		sender.sendMessage(Text.of(TextColors.GRAY, "Added ", TextColors.YELLOW, e.getId().replaceAll("minecraft:", ""), " ", level, TextColors.GRAY, " to ", TextColors.YELLOW, i.getItem().getId().replaceAll("minecraft:", "")));

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
