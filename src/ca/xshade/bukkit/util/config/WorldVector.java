package ca.xshade.bukkit.util.config;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/18/12
 */

import ca.xshade.bukkit.util.BukkitUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import java.util.Map;

@SerializableAs("WorldVector")
public class WorldVector extends Vector {
	private World world;

	public WorldVector(Location location) {
		this(location.getWorld(), location.getX(), location.getY(), location.getZ());
	}

	public WorldVector(World world, Vector vector) {
		this(world, vector.getX(), vector.getY(), vector.getZ());
	}

	public WorldVector(World world, double x, double y, double z) {
		super(x, y, z);

		if (world == null)
			throw new IllegalArgumentException();

		this.world = world;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Vector toVector() {
		return super.copy(this);
	}

	public WorldVector floorVector() {
		return new WorldVector(getWorld(), getBlockX(), getBlockY(), getBlockZ());
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = super.serialize();
		result.put("world", getWorld().getName());
		return result;
	}

	public static WorldVector deserialize(Map<String, Object> args) {
		World world = null;
		Vector vector =  null;

		if (args.containsKey("world")) {
			world = BukkitUtil.getWorldFromName((String) args.get("world"));
		}
		vector = Vector.deserialize(args);
		return new WorldVector(world, vector);
	}

	public Location toLocation() {
		return toLocation(getWorld());
	}

	@Override
	public String toString() {
		return getWorld().getName() + "," + super.toString();
	}
}
