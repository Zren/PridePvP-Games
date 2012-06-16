package com.pridemc.games.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class PlayerMoveListener implements Listener {
	public Map<Player, Block> playerLastBlockMap = new WeakHashMap<Player, Block>();

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		PlayerMoveBlockEvent playerMoveBlockEvent;

		Player player = event.getPlayer();
		Location toLocation = event.getTo();
		Block toBlock = toLocation.getBlock();

		if (playerLastBlockMap.containsKey(player)) {
			Block lastBlock = playerLastBlockMap.get(player);
			if (toBlock.equals(lastBlock))
				return;

			playerMoveBlockEvent = new PlayerMoveBlockEvent(player, lastBlock, toBlock, event.isCancelled());
		} else {
			playerMoveBlockEvent = new PlayerMoveBlockEvent(player, null, toBlock, event.isCancelled());
		}

		Bukkit.getPluginManager().callEvent(playerMoveBlockEvent);
		event.setCancelled(playerMoveBlockEvent.isCancelled());
	}
}
