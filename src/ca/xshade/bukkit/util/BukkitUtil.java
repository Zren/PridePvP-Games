package ca.xshade.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/18/12
 */
public class BukkitUtil {
	public static World getWorldFromName(String worldName) {
		if (worldName == null) {
			return null;
		} else {
			return Bukkit.getWorld(worldName);
		}
	}

	public static World getWorldFromName(String worldName, World def) {
		if (worldName == null) {
			return def;
		} else {
			return Bukkit.getWorld(worldName);
		}
	}

	public static World getDefautltWorld() {
		return Bukkit.getServer().getWorlds().get(0);
	}
}
