package com.pridemc.games.arena;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class ArenaStartEditSessionTask implements Runnable {
	Arena arena;

	public ArenaStartEditSessionTask(Arena arena) {
		this.arena = arena;
	}

	@Override
	public void run() {
		String arenaName = arena.getName();
		ArenaManager.resetArena(arenaName);
		arena = ArenaManager.getArena(arenaName);

		//
		arena.setState(Arena.State.EDIT);
		arena.setStartTime(Long.MAX_VALUE); // Set time to the future so that revert code doesn't undo all our changes.

		//
		arena.update();

		// Msg
		MessageUtil.sendMsgToServer("[Arena - %s] Edit Mode", arena.getName());
	}
}
