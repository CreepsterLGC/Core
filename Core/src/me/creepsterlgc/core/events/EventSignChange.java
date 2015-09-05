package me.creepsterlgc.core.events;

import java.util.ArrayList;
import java.util.List;

import me.creepsterlgc.core.customized.PERMISSIONS;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.block.tileentity.SignChangeEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.TextMessageException;


public class EventSignChange {

    @Subscribe
    public void onSignChange(SignChangeEvent event) {
    	
    	Player player = (Player) event.getCause().get().getCause();
    	
    	if(PERMISSIONS.has(player, "core.signs.color")) {
    	
    		SignData data = event.getNewData();
    		List<Text> o = data.get(Keys.SIGN_LINES).get();
    		List<Text> n = new ArrayList<Text>();
    		
    		for(int i = 0; i <= 3; i++) {
    			if(o.size() < i + 1) continue;
    			Text line = o.get(i);
    			String plain = Texts.toPlain(line);
    			try {
					line = Texts.legacy('&').from(plain);
				} catch (TextMessageException e) {
					System.out.println("Core: Error while formatting sign text!");
					e.printStackTrace();
				}
    			n.add(line);
    		}
    		
    		data.set(Keys.SIGN_LINES, n);
    		event.setNewData(data);
    		
    	}
    	
    }
	
}
