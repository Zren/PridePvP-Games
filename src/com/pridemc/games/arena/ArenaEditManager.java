package com.pridemc.games.arena;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class ArenaEditManager {
	private static ArenaEditManager instance;

	public static ArenaEditManager getInstance() {
		return instance;
	}

	public static void setInstance(ArenaEditManager instance) {
		ArenaEditManager.instance = instance;
	}

	private Map<Player, String> editing = new WeakHashMap<Player, String>();

	public Map<Player, String> getEditing() {
		return editing;
	}

	public static Arena getArenaPlayerIsEditing(Player player) {
		String arenaName = getArenaNamePlayerIsEditing(player);
		return ArenaManager.getArena(arenaName);
	}

	public static String getArenaNamePlayerIsEditing(Player player) {
		return getInstance().getEditing().get(player);
	}

	public static void startEditSession(Player player, Arena arena) {
		if (isEdidting(player))
			finishEditSession(player);
		getInstance().getEditing().put(player, arena.getName());
	}

	public static boolean isEdidting(Player player) {
		return getInstance().getEditing().containsKey(player);
	}

	public static void startEditRegionSession(Player player, Arena arena) {
		arena.startTaskFor(Arena.State.EDIT);
	}

	public static void finishEditSession(Player player) {
		Arena arena = getArenaPlayerIsEditing(player);
		if (arena == null)
			return;
		finishEditSession(player, arena);
	}

	public static void finishEditSession(Player player, Arena arena) {
		if (arena == null)
			return;

		if (arena.getState() == Arena.State.EDIT) {
			//TODO: WorldGuard schematic?

			// Msg
			String msg = "Finished editing %s's region.";
			MessageUtil.sendMsg(player, msg, arena.getName());

			// Reset Arena
			ArenaManager.resetArena(arena.getName());
		}

		getInstance().getEditing().remove(player);
	}
}
