package com.pridemc.games.events;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public class Drops implements Listener {
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		if (ArenaManager.isInArena(player.getName())) {
			Arena arena = ArenaManager.getArenaPlayerIsIn(player.getName());
			if (!arena.getState().canDropItems()) {
				event.setCancelled(true);
			}
		}
	}
}
