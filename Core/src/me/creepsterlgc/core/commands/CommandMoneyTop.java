package me.creepsterlgc.core.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;

import me.creepsterlgc.core.customized.DATABASE;
import me.creepsterlgc.core.customized.PERMISSIONS;
import me.creepsterlgc.core.customized.PLAYER;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandSource;

public class CommandMoneyTop {

	public CommandMoneyTop(CommandSource sender, String[] args) {
		
		if(!PERMISSIONS.has(sender, "core.money.top")) { sender.sendMessage(Texts.builder("You do not have permissions!").color(TextColors.RED).build()); return; }
		
		if(args.length != 1) { sender.sendMessage(Texts.of(TextColors.YELLOW, "Usage: ", TextColors.GRAY, "/money top")); return; }
		
		HashMap<String, Double> list = new HashMap<String, Double>();
		
		try {
			Connection c = DATABASE.datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM players ORDER BY money DESC");
			while(rs.next()) {
				if(list.size() >= 10) break;
				list.put(rs.getString("uuid"), rs.getDouble("money"));
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		int c = 0;
		for(Entry<String, Double> e : list.entrySet()) {
			c += 1;
			PLAYER p = DATABASE.getPlayer(e.getKey());
			sender.sendMessage(Texts.of(TextColors.GRAY, c, ". ", TextColors.WHITE, p.getName(), TextColors.GRAY, " - ", TextColors.GREEN, "$", e.getValue()));
		}
		
	}
	
}
