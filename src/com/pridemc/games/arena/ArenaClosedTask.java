package com.pridemc.games.arena;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/18/12
 */
public class ArenaClosedTask implements Runnable {
	Arena arena;

	public ArenaClosedTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		//
		arena = ArenaManager.resetArena(arena.getName());
		arena.setState(Arena.State.CLOSED);
		arena.update();

		String msg = "[Arena - %s] Arena is now closed.";
		MessageUtil.sendMsgToServer(msg, arena.getName());
	}
}
