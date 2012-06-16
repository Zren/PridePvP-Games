package com.pridemc.games.arena;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class UpdateArenaTask implements Runnable {
	Arena arena;

	public UpdateArenaTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		arena.update();
	}
}

