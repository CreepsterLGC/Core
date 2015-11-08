package main.java.me.creepsterlgc.core.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import main.java.me.creepsterlgc.core.utils.PermissionsUtils;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.TextMessageException;


public class EventSignChange {

    @Listener
    public void onSignChange(ChangeSignEvent event) {
    	
    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;
    	
    	Player player = optional.get();

    	Sign sign = event.getTargetTile();
    	
		SignData data = event.getText();
		List<Text> lines = data.get(Keys.SIGN_LINES).get();
		
    	if(PermissionsUtils.has(player, "core.signs.color")) {
    	
    		List<Text> n = new ArrayList<Text>();
    		
    		for(int i = 0; i <= 3; i++) {
    			if( lines.size() < i + 1) continue;
    			Text line =  lines.get(i);
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
    		sign.offer(data);
    		
    	}
    	
    }
    
    public void addShop(ChangeSignEvent event) {
    	
    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;
    	
    	Player player = optional.get();
    	
    	Sign sign = event.getTargetTile();
    	
		SignData data = event.getText();
		List<Text> lines = data.get(Keys.SIGN_LINES).get();
		
		
		
    }
	
}
