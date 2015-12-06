package main.java.me.creepsterlgc.core.events;

import java.util.HashMap;
import java.util.Optional;

import main.java.me.creepsterlgc.core.Controller;
import main.java.me.creepsterlgc.core.customized.CoreDatabase;
import main.java.me.creepsterlgc.core.customized.CorePlayer;
import main.java.me.creepsterlgc.core.customized.CoreSelection;
import main.java.me.creepsterlgc.core.utils.PermissionsUtils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;


public class EventPlayerInteractBlock {

    @Listener
    public void onPlayerInteractBlock(InteractBlockEvent.Primary event) {

    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	isSelecting(event);

    	Player player = optional.get();
    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

    	if(player.getItemInHand().isPresent() && PermissionsUtils.has(player, "core.powertool")) {

    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");

    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandManager().process(player.getCommandSource().get(), powertools.get(id));
    		}

    	}

    }

    public void isSelecting(InteractBlockEvent.Primary event) {

    	Player player = event.getCause().first(Player.class).get();
    	Location<World> location = event.getTargetBlock().getLocation().get();

    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

    	if(!PermissionsUtils.has(player, "core.selection.select")) return;
    	if(!player.getItemInHand().isPresent()) return;
    	if(!player.getItemInHand().get().getItem().equals(ItemTypes.GOLDEN_AXE)) return;
    	if(location.getBlockPosition().equals(new Vector3i(0, 0, 0))) return;

		event.setCancelled(true);

		CoreSelection selection = p.getSelection();
		selection.setFirstPoint(location);
		p.setSelection(selection);

		CoreDatabase.addPlayer(p.getUUID(), p);

		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		player.sendMessage(Texts.of(TextColors.GRAY, "First point set at: ", TextColors.AQUA, "x:", x, " y:", y, " z:", z));

    }

    @Listener
    public void onPlayerInteractBlock(InteractBlockEvent.Secondary event) {

    	Optional<Player> optional = event.getCause().first(Player.class);
    	if(!optional.isPresent()) return;

    	isSelecting(event);

    	Player player = optional.get();
    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

    	if(player.getItemInHand().isPresent() && PermissionsUtils.has(player, "core.powertool")) {

    		ItemStack i = player.getItemInHand().get();
    		String id = i.getItem().getId().replaceAll("minecraft:", "");

    		HashMap<String, String> powertools = p.getPowertools();
    		if(powertools.containsKey(id)) {
    			Controller.getGame().getCommandManager().process(player.getCommandSource().get(), powertools.get(id));
    		}

    	}

    }

    public void isSelecting(InteractBlockEvent.Secondary event) {

    	Player player = event.getCause().first(Player.class).get();
    	Location<World> location = event.getTargetBlock().getLocation().get();

    	CorePlayer p = CoreDatabase.getPlayer(player.getUniqueId().toString());

    	if(!PermissionsUtils.has(player, "core.selection.select")) return;
    	if(!player.getItemInHand().isPresent()) return;
    	if(!player.getItemInHand().get().getItem().equals(ItemTypes.GOLDEN_AXE)) return;
    	if(location.getBlockPosition().equals(new Vector3i(0, 0, 0))) return;

		event.setCancelled(true);

		CoreSelection selection = p.getSelection();
		selection.setSecondPoint(location);
		p.setSelection(selection);

		CoreDatabase.addPlayer(p.getUUID(), p);

		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		player.sendMessage(Texts.of(TextColors.GRAY, "Second point set at: ", TextColors.AQUA, "x:", x, " y:", y, " z:", z));

    }

}
