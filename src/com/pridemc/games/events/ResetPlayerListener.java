package com.pridemc.games.events;

import com.pridemc.games.arena.ArenaCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/19/12
 */
public class ResetPlayerListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		ArenaCore.getInstance().resetPlayer(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		ArenaCore.getInstance().resetPlayer(event.getPlayer());
	}


	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event){
		if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)){
			ArenaCore.getInstance().resetPlayerSession(event.getPlayer());
		}
	}
}
