package main.java.me.creepsterlgc.core.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.utils.DeserializeUtils;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import main.java.me.creepsterlgc.core.utils.TimeUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.command.CommandSource;


public class CommandMailRead {

	public CommandMailRead(CommandSource sender, String[] args) {

		if(sender instanceof Player == false) { sender.sendMessage(Text.builder("Cannot be run by the console!").color(TextColors.RED).build()); return; }

		if(!PermissionsUtils.has(sender, "core.mail.read")) { sender.sendMessage(Text.builder("You do not have permissions!").color(TextColors.RED).build()); return; }

		if(args.length != 1) { sender.sendMessage(Text.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/mail read")); return; }

		Player player = (Player) sender;
		CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

		List<String> mails = DeserializeUtils.messages(p.getMails());
		HashMap<Integer, Text> list = new HashMap<Integer, Text>();
		HashMap<Integer, List<Text>> pages = new HashMap<Integer, List<Text>>();

		int counter = 1;

		for(String mail : mails) {
			String time = TimeUtils.toString(System.currentTimeMillis() - Double.parseDouble(mail.split(":", 3)[0]));
			String name = mail.split(":", 3)[1];
			String message = mail.split(":", 3)[2];
			list.put(counter, Text.of(TextColors.GRAY, time, " ago | ", TextColors.WHITE, name, TextColors.GRAY, ": ", TextColors.WHITE, message));
			counter += 1;
		}

		if(counter == 1) {
			sender.sendMessage(Text.builder("Your inbox is empty!").color(TextColors.RED).build());
			return;
		}

		counter = 0;
		while(!list.isEmpty()) {
			List<Text> c = new ArrayList<Text>();
			for(int i = 1; i <= 6; i++) {
				if(!list.containsKey(i + counter * 6)) break;
				c.add(list.get(i + counter * 6));
				list.remove(i  + counter * 6);
			}
			counter += 1;
			pages.put(counter, c);
		}

		p.setPages(pages);

		p.setPageTitle(Text.of(TextColors.GOLD, "Inbox"));
		p.setPageHeader(Text.of(TextColors.GREEN, "Time", TextColors.GRAY, " | ", TextColors.GREEN, "Sender", TextColors.GRAY, " | ", TextColors.GREEN, "Message"));

		Controller.getGame().getCommandManager().process(sender.getCommandSource().get(), "page 1");

	}

}
