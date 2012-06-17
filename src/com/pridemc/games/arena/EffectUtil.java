package com.pridemc.games.arena;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class EffectUtil {
	public static void explosionOverPlayer(Player player) {
		player.getWorld().createExplosion(player.getLocation().add(0, 20, 0), 2);
	}

	public static void explosionOverPlayers(List<Player> players) {
		for (Player player : players) {
			explosionOverPlayer(player);
		}
	}

	public static void temporarySpeed(Player player, int duration) {
		player.addPotionEffect(PotionEffectType.SPEED.createEffect(duration, 2));
	}

	public static void permanentEffect(Player player, PotionEffectType type) {
		player.addPotionEffect(type.createEffect(Integer.MAX_VALUE, 1));
	}
 }
