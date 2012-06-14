package com.pridemc.games.events;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();

		if (player.hasPermission("pridegames.admin"))
			return;

		if (ArenaManager.isInArena(player.getName())) {
			Arena arena = ArenaManager.getArenaPlayerIsIn(player.getName());
			if (!arena.getState().canEditBlocks()) { // The arena is not allowing editing
				event.setCancelled(true);
			}
		}


	}
}
