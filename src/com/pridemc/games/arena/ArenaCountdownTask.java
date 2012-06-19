package com.pridemc.games.arena;

import com.pridemc.games.classes.PlayerClassManager;
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
		EffectUtil.explosionOverPlayers(arena.getBukkitPlayers());
		arena.update();

		// Msg.
		String msg;
		long delayMillis = ArenaCore.getInstance().getArenaCountdownDuration();
		long minutes = TimeUnit.MILLISECONDS.toMinutes(delayMillis);
		msg = "[Arena - %s] Arena will start in " + ChatColor.AQUA + "%d" +ChatColor.YELLOW + " minute(s). Do " + ChatColor.AQUA + "/pg votestart" + ChatColor.YELLOW + " to start now.";
		MessageUtil.sendMsgToServer(msg, arena.getName(), minutes);

		// Reminder Msg - Choose a Class
		for (ArenaPlayer arenaPlayer : arena.getArenaPlayers()) {
			if (!PlayerClassManager.hasAClass(arenaPlayer.getName())) {
				msg = "Do " + ChatColor.AQUA + "/class" + ChatColor.YELLOW + " to pick your class.";
				MessageUtil.sendMsg(arenaPlayer.getPlayer(), msg);
			}
		}

		//
		arena.scheduleTaskFor(Arena.State.INITIAL_GRACE_PERIOD, delayMillis);
	}
}
