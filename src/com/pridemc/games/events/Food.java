package com.pridemc.games.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class Food implements Listener {
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
}
