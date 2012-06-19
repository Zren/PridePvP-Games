package com.pridemc.games.events;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.events.custom.PlayerMoveBlockEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMoveBlock implements Listener{
	@EventHandler
	public void onPlayerMoveBlock(PlayerMoveBlockEvent event) {
		Player player = event.getPlayer();
		if (ArenaManager.isInArena(player.getName())) {
			// Limit players to within the arena.
			Arena arena = ArenaManager.getArenaPlayerIsIn(player.getName());
			if (arena.hasRegion() && !arena.getRegion().isInside(event.getToBlock().getLocation())) {
				event.setCancelled(true);
			}
		} else {
			// Detect stepping inside an arena portal.
			Arena arena = ArenaManager.getArenaFromPortalBlock(event.getToBlock());
			if (arena != null) {
				try {
					ArenaManager.addPlayerToArena(event.getPlayer(), arena);
				} catch (Exception e) {
					//System.out.println(e.getMessage());
					//e.printStackTrace();
				}
			}
		}
	}
}
