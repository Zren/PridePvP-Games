package ca.xshade.bukkit.util.config;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

import java.util.Map;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/18/12
 */
@SerializableAs("Location")
public class LocationSerializable extends WorldVector {
	private float yaw;
	private float pitch;

	public LocationSerializable(WorldVector worldVector) {
		this(worldVector, 0, 0);
	}

	public LocationSerializable(WorldVector worldVector, float yaw, float pitch) {
		this(worldVector.getWorld(), worldVector.getX(), worldVector.getY(), worldVector.getZ(), yaw, pitch);
	}

	public LocationSerializable(Location location) {
		this(location.getWorld(), location.toVector(), location.getYaw(), location.getPitch());
	}

	public LocationSerializable(World world, Vector vector, float yaw, float pitch) {
		this(world, vector.getX(), vector.getY(), vector.getZ(), yaw, pitch);
	}

	public LocationSerializable(World world, double x, double y, double z, float yaw, float pitch) {
		super(world, x, y, z);
		setYaw(yaw);
		setPitch(pitch);
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public Location toLocation() {
		return toLocation(getWorld(), getYaw(), getPitch());
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> result = super.serialize();
		result.put("yaw", getYaw());
		result.put("pitch", getPitch());
		return result;
	}

	public static LocationSerializable deserialize(Map<String, Object> args) {
		float yaw = 0;
		float pitch = 0;

		if (args.containsKey("yaw")) {
			yaw = ((Double)args.get("yaw")).floatValue();
		}
		if (args.containsKey("pitch")) {
			pitch = ((Double)args.get("pitch")).floatValue();
		}

		return new LocationSerializable(WorldVector.deserialize(args), yaw, pitch);
	}
}
