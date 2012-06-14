package com.pridemc.games.arena;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/2/12
 */
public class ArenaStartGameTask implements Runnable {
	Arena arena;

	public ArenaStartGameTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		if (ArenaManager.checkEndGameConditions(arena)) {
			ArenaManager.endGame(arena);
			return;
		}

		//
		arena.setState(Arena.State.RUNNING_GAME);

		// Msg
		MessageUtil.sendMsgToServer("[Arena - %s] Game Started", arena.getName());
	}
}
