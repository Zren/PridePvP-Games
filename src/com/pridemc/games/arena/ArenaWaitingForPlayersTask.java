package com.pridemc.games.arena;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/18/12
 */
public class ArenaWaitingForPlayersTask implements Runnable {
	Arena arena;

	public ArenaWaitingForPlayersTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		arena = ArenaManager.resetArena(arena.getName());
		arena.setState(Arena.State.WAITING_FOR_PLAYERS);
		arena.update();

		String msg = "[Arena - %s] Arena is now open.";
		MessageUtil.sendMsgToServer(msg, arena.getName());
	}
}
