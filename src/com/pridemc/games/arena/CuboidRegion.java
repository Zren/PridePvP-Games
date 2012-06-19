package com.pridemc.games.arena;

import ca.xshade.bukkit.util.BukkitUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/16/12
 */
@SerializableAs("CuboidRegion")
public class CuboidRegion implements ConfigurationSerializable {
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
		return loc.getWorld().equals(world) && loc.toVector().isInAABB(min, max);
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

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("world", getWorld().getName());
		result.put("min", getMin());
		result.put("max", getMax());
		return result;
	}

	@SuppressWarnings(value = {"unchecked"})
	public static CuboidRegion deserialize(Map<String, Object> args) {
		World world = null;
		Vector min = null;
		Vector max = null;

		if (args.containsKey("world")) {
			world = BukkitUtil.getWorldFromName((String) args.get("world"));
		}
		if (args.containsKey("min")) {
			min = (Vector)args.get("min");
		}
		if (args.containsKey("max")) {
			max = (Vector)args.get("max");
		}
		return new CuboidRegion(world, min, max);
	}
}
