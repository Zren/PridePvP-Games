package com.pridemc.games.arena;

import org.bukkit.entity.Player;

import java.util.List;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class EffectUtil {
	public static void explosionOverPlayer(Player player) {
		player.getWorld().createExplosion(player.getLocation().add(0, 15, 0), 2);
	}

	public static void explosionOverPlayers(List<Player> players) {
		for (Player player : players) {
			explosionOverPlayer(player);
		}
	}
}
