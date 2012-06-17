package com.pridemc.games.arena;

import com.pridemc.games.Core;
import org.bukkit.entity.Player;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class ArenaEditManager {
	public static Arena getArenaPlayerIsEditing(Player player) {
		String arenaName = Core.instance.getEditing().get(player);
		return ArenaManager.getArena(arenaName);
	}

	public static void startEditSession(Player player, Arena arena) {
		Core.instance.getEditing().put(player, arena.getName());
	}

	public static boolean isEdidting(Player player) {
		return Core.instance.getEditing().containsKey(player);
	}

	public static void startEditRegionSession(Player player, Arena arena) {
		arena.startTaskFor(Arena.State.EDIT);
	}

	public static void finishEditSession(Player player) {
		finishEditSession(player, getArenaPlayerIsEditing(player));
	}

	public static void finishEditSession(Player player, Arena arena) {
		if (arena.getState() == Arena.State.EDIT) {
			//TODO: WorldGuard schematic?

			// Msg
			String msg = "Finished editing %s's region.";
			MessageUtil.sendMsg(player, msg, arena.getName());

			// Reset Arena
			ArenaManager.resetArena(arena.getName());
		}

		Core.instance.getEditing().remove(player);
	}
}
