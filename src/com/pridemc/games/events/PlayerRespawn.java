package com.pridemc.games.events;

import com.pridemc.games.arena.ArenaManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class PlayerRespawn implements Listener {
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		event.setRespawnLocation(ArenaManager.getGlobalSpawnPoint());
	}
}
