package com.pridemc.games.arena;

import com.pridemc.games.classes.PlayerClassManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

		// Teleport Players
		arena.setPlayerSpawnPoints();
		arena.teleportAllToGameSpawnPoint();

		//
		arena.setState(Arena.State.INITIAL_GRACE_PERIOD);

		//
		EffectUtil.explosionOverPlayers(arena.getBukkitPlayers());
		arena.update();

		// Msg.
		long delayMillis = ArenaCore.getInstance().getArenaGracePeriodDuration();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(delayMillis);
		MessageUtil.sendMsgToServer("[Arena - %s] Grace Period Started - Game Begins in %s minute(s).", arena.getName(), minutes);

		// Reminder Msg - Choose a Class
		for (ArenaPlayer arenaPlayer : arena.getArenaPlayers()) {
			if (!PlayerClassManager.hasAClass(arenaPlayer.getName())) {
				String msg = "Do " + ChatColor.AQUA + "/class" + ChatColor.YELLOW + " to pick your class.";
				MessageUtil.sendMsg(arenaPlayer.getPlayer(), msg);
			}
		}

		//
		int durationTicks = (int)(delayMillis / 1000 * 20);
		for (ArenaPlayer arenaPlayer : arena.getArenaPlayers()) {
			Player player = arenaPlayer.getPlayer();

			// Equip player (Unpack bag).
			arenaPlayer.equip();

			// Temp speed boost
			EffectUtil.temporarySpeed(player, durationTicks);
		}

		//
		arena.scheduleTaskFor(Arena.State.RUNNING_GAME, delayMillis);
	}
}
