package com.pridemc.games.arena;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/2/12
 */
public class ArenaGraceTask implements Runnable {
	Arena arena;

	public ArenaGraceTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		// Kill / Kick any offline players that left during countdown.
		for (ArenaPlayer arenaPlayer : new HashSet<ArenaPlayer>(arena.getArenaPlayers())) {
			String playerName = arenaPlayer.getName();
			Player player = Bukkit.getPlayer(playerName);
			if (player == null || !player.isOnline()) {
				// Is offline
				ArenaManager._removePlayerFromArena(playerName);
			}
		}

		// Check for auto win conditions
		if (ArenaManager.checkEndGameConditions(arena)) {
			ArenaManager.endGame(arena);
			return;
		}

		arena.startTime = System.currentTimeMillis();

		// Teleport Players
		arena.setPlayerSpawnPoints();
		arena.teleportAllToGameSpawnPoint();

		//
		arena.setState(Arena.State.INITIAL_GRACE_PERIOD);

		//
		arena.update();

		// Msg.
		long delayMillis = ArenaConfig.getGracePeriodDelay();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(delayMillis);
		MessageUtil.sendMsgToServer("[Arena - %s] Grace Period Started - Game Begins in %s minute(s).", arena.getName(), minutes);

		//
		arena.scheduleTaskFor(Arena.State.RUNNING_GAME, delayMillis);
	}
}
