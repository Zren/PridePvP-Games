package ca.xshade.bukkit.util.config;

import ca.xshade.bukkit.util.BukkitUtil;
import com.pridemc.games.arena.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/18/12
 */
public class Config extends YamlConfiguration {

	static {
		ConfigurationSerialization.registerClass(CuboidRegion.class);
		ConfigurationSerialization.registerClass(LocationSerializable.class);
		ConfigurationSerialization.registerClass(WorldVector.class);
	}

	private File file = new File("");

	public Config(File file) {
		if (!file.isFile())
			throw new IllegalArgumentException("Path must be a file.");

		this.file = file;
	}

	public void save() {
		try {
			save(file);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					String.format("Could not save config file '%s'.", file.getPath()),
					e);
		}
	}

	public void load() {
		try {
			load(file);
		} catch (IOException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					String.format("Could not load config file '%s'.", file.getPath()),
					e);
		} catch (InvalidConfigurationException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,
					e.getMessage(),
					e);
		}
	}

	public Location getLocation(String path) {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null");
		}

		Object def = getDefault(path);
		return getLocation(path, (def instanceof LocationSerializable) ? ((LocationSerializable)def).toLocation() : null);
	}

	public Location getLocation(String path, Location def) {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null");
		}

		Object val = get(path, def);
		return (val instanceof LocationSerializable) ? ((LocationSerializable)val).toLocation() : def;
	}

	public boolean isLocation(String path) {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null");
		}

		Object val = get(path);
		return val instanceof LocationSerializable;
	}

	public World getWorld(String path) {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null");
		}

		Object def = getDefault(path);
		return getWorld(path, (def instanceof World) ? (World) def : null);
	}

	public World getWorld(String path, World def) {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null");
		}

		Object val = get(path, def);
		return (val instanceof String) ? BukkitUtil.getWorldFromName((String)val) : def;
	}



	@Override
	public void set(String path, Object value) {
		set(path, value, true);
	}

	public void set(String path, Object value, boolean save) {
		if (value instanceof Location) {
			super.set(path, new LocationSerializable((Location)value));
		} else if (value instanceof World) {
			super.set(path, ((World)value).getName());
		} else {
			super.set(path, value);
		}

		if (save)
			save();
	}

	public WorldVector getWorldVector(String path) {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null");
		}

		Object def = getDefault(path);
		return getWorldVector(path, (def instanceof WorldVector) ? (WorldVector) def : null);
	}

	public WorldVector getWorldVector(String path, WorldVector def) {
		if (path == null) {
			throw new IllegalArgumentException("Path cannot be null");
		}

		Object val = get(path, def);
		return (val instanceof WorldVector) ? (WorldVector)val : def;
	}

	public List<WorldVector> getWorldVectorList(String path) {
		List<?> list = getList(path);

		if (list == null) {
			return new ArrayList<WorldVector>(0);
		}

		List<WorldVector> result = new ArrayList<WorldVector>();

		for (Object object : list) {
			if (object instanceof WorldVector) {
				result.add((WorldVector)object);
			}
		}

		return result;
	}

	public List<String> getStringList(String path) {
		List<?> list = getList(path);

		if (list == null) {
			return new ArrayList<String>(0);
		}

		List<String> result = new ArrayList<String>();

		for (Object object : list) {
			if ((object instanceof String) || (isPrimitiveWrapper(object))) {
				result.add(String.valueOf(object));
			}
		}

		return result;
	}
}
