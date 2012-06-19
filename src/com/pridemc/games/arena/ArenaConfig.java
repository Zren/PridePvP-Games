package com.pridemc.games.arena;

import ca.xshade.bukkit.util.config.Config;

import java.io.File;
import java.util.Set;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/2/12
 */
public class ArenaConfig extends Config {


	public ArenaConfig(File file) {
		super(file);
	}

	public Set<String> getArenaNames() {
		return getKeys(false);
	}

	@Override
	public void load() {
		super.load();

		loadArenas();
	}

	public void loadArenas() {
		for (String arenaName : getArenaNames()) {
			ArenaManager.addArena(new Arena(arenaName));
		}
	}
}
