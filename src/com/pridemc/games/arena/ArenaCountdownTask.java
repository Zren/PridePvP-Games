package com.pridemc.games.arena;

import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/3/12
 */
public class ArenaCountdownTask implements Runnable {
	Arena arena;

	public ArenaCountdownTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		//
		arena.setState(Arena.State.COUNTING_DOWN);

		//
		arena.update();

		// Msg.
		long delayMillis = ArenaConfig.getCountdownDelay();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(delayMillis);
		MessageUtil.sendMsgToServer("[Arena - %s] Game starts in %d minute(s).", arena.getName(), minutes);

		for (ArenaPlayer arenaPlayer : arena.getArenaPlayers()) {
			if (!arenaPlayer.hasAClass()) {
				String msg = "Do " + ChatColor.AQUA + "/class" + ChatColor.YELLOW + " to pick your class.";
				MessageUtil.sendMsg(arenaPlayer.getPlayer(), msg, arena.getName());
			}
		}

		//
		arena.scheduleTaskFor(Arena.State.INITIAL_GRACE_PERIOD, delayMillis);
	}
}
