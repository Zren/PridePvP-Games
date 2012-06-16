package com.pridemc.games.events;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class PlayerDamage implements Listener {
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity defender = event.getEntity();
		if (defender instanceof Player) {
			Player defenderPlayer = (Player)defender;
			if (ArenaManager.isInArena(defenderPlayer.getName())) {
				Arena arena = ArenaManager.getArenaPlayerIsIn(defenderPlayer.getName());
				if (!arena.getState().canPvP()) {
					event.setCancelled(true);
				}
			}
		}
	}
}
