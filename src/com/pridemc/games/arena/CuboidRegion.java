package com.pridemc.games.arena;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
public class CuboidRegion {
	private World world;
	private Vector min;
	private Vector max;

	public CuboidRegion(World world, Vector min, Vector max) {
		if (world == null || min == null || max == null)
			throw new IllegalArgumentException();
		
		this.world = world;
		this.min = min;
		this.max = max;
	}

	public boolean isInside(Location loc) {
		if (!loc.getWorld().equals(world))
			return false;
		return loc.toVector().isInAABB(min, max);
	}

	public World getWorld() {
		return world;
	}

	public Vector getMin() {
		return min;
	}

	public Vector getMax() {
		return max;
	}
}
