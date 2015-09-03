package me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.creepsterlgc.core.Controller;
import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;


public class CommandPage implements CommandCallable {
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if(sender instanceof Player == false) { sender.sendMessage(Texts.builder("Cannot be run by the console!").color(TextColors.RED).build()); return CommandResult.success(); }
		
		Player player = (Player) sender;
		PLAYER p = DATABASE.getPlayer(player.getUniqueId().toString());
		
		int page;
		try { page = Integer.parseInt(arguments); }
		catch(NumberFormatException e) {
			sender.sendMessage(Texts.builder("<page> has to be a number!").color(TextColors.RED).build());
			return CommandResult.success();
		}
		
		HashMap<Integer, List<Text>> pages = p.getPages();
		if(!pages.containsKey(page)) {
			sender.sendMessage(Texts.builder("Page does not exist!").color(TextColors.RED).build());
			return CommandResult.success();
		}

		List<Text> c = pages.get(page);
		
		PaginationService paginationService = Controller.getGame().getServiceManager().provide(PaginationService.class).get();
		PaginationBuilder builder = paginationService.builder();
		
		Text title = p.getPageTitle();
		Text header = p.getPageHeader();
		Text page_previous = null;
		Text page_next = null;
		if(pages.containsKey(page - 1)) {
			page_previous = Texts.builder("Previous Page").color(TextColors.GOLD).onHover(TextActions.showText(Texts.of(TextColors.GRAY, "Go to previous site"))).onClick(TextActions.runCommand("/page " + String.valueOf(page - 1))).build();
		}
		if(pages.containsKey(page + 1)) {
			page_next = Texts.builder("Next Page").color(TextColors.GOLD).onHover(TextActions.showText(Texts.of(TextColors.GRAY, "Go to next site"))).onClick(TextActions.runCommand("/page " + String.valueOf(page + 1))).build();
		}
		Text footer = Texts.of();
		if(page_previous != null && page_next != null) {
			Text separator = Texts.of(TextColors.GRAY, " | ");
			footer = Texts.builder().append(page_previous).append(separator).append(page_next).build();
		}
		else if(page_previous != null) {
			footer = page_previous;
		}
		else if(page_next != null) {
			footer = page_next;
		}
		else {
			footer = Texts.of(TextColors.GRAY, "This is the only page found.");
		}
		
		builder
		.contents(c)
		.paddingString("-")
		.title(title)
		.header(header)
		.footer(footer)
		.sendTo(sender);
		
		return CommandResult.success();
		
	}

	private final Text usage = Texts.builder("Usage: /page <page>").color(TextColors.YELLOW).build();
	private final Text help = Texts.builder("Help: /page <page>").color(TextColors.YELLOW).build();
	private final Text description = Texts.builder("Core | Page Command").color(TextColors.YELLOW).build();
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(help); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String args) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }

}
