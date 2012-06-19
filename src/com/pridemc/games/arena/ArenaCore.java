package com.pridemc.games.arena;

import ca.xshade.bukkit.util.config.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/18/12
 */
public class ArenaCore {
	private static ArenaCore instance;

	public static void setInstance(ArenaCore instance) {
		ArenaCore.instance = instance;
		ArenaManager.setInstance(new ArenaManager());
		ArenaEditManager.setInstance(new ArenaEditManager());
	}

	public static ArenaCore getInstance() {
		return instance;
	}

	private File dataFolder;
	private Config config;
	private ArenaConfig arenaConfig;

	public ArenaCore(File dataFolder) {
		setDataFolder(dataFolder);


	}

	public void setDataFolder(File dataFolder) {
		this.dataFolder = dataFolder;
		this.config = new Config(new File(dataFolder, "config.yml"));
		this.arenaConfig = new ArenaConfig(new File(dataFolder, "arenas.yml"));
	}

	public Config getConfig() {
		return config;
	}

	public ArenaConfig getArenaConfig() {
		return arenaConfig;
	}

	public void load() {
		getConfig().load();
		getArenaConfig().load();

		//TODO: Create a MainConfig class.
		setGlobalSpawnPoint(getGlobalSpawnPoint());
		setArenaCountdownDuration(getArenaCountdownDuration());
		setArenaGracePeriodDuration(getArenaGracePeriodDuration());
	}

	public void save() {
		getConfig().save();
		getArenaConfig().save();
	}

	//
	Location globalSpawnPoint;
	Location shopLocation;
	long arenaGracePeriodDuration;
	long arenaCountdownDuration;

	//
	public static final long DEFAULT_GRACE_PERIOD = TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);
	public static final long DEFAULT_COUNTDOWN = TimeUnit.SECONDS.convert(3, TimeUnit.MINUTES);

	//
	public static final String NODE_GRACE_PERIOD = "arena.gamestate.grace_period.delay";
	public static final String NODE_COUNTDOWN = "arena.gamestate.countdown.delay";
	public static final String NODE_GLOBAL_SPAWNPOINT = "global_spawnpoint";
	public static final String NODE_SHOP_LOCATION = "shop.location";

	public Location getGlobalSpawnPoint() {
		return getConfig().getLocation(NODE_GLOBAL_SPAWNPOINT);
	}

	public void setGlobalSpawnPoint(Location globalSpawnPoint) {
		getConfig().set(NODE_GLOBAL_SPAWNPOINT, globalSpawnPoint);
	}

	public Location getShopLocation() {
		return getConfig().getLocation(NODE_SHOP_LOCATION);
	}

	public void setShopLocation(Location shopLocation) {
		getConfig().set(NODE_SHOP_LOCATION, shopLocation);
	}

	public long getArenaCountdownDuration() {
		return TimeUnit.MILLISECONDS.convert(getConfig().getLong(NODE_COUNTDOWN, DEFAULT_COUNTDOWN), TimeUnit.SECONDS);
	}

	public long getArenaGracePeriodDuration() {
		return TimeUnit.MILLISECONDS.convert(getConfig().getLong(NODE_GRACE_PERIOD, DEFAULT_GRACE_PERIOD), TimeUnit.SECONDS);
	}

	public void setArenaGracePeriodDuration(long arenaGracePeriodDuration) {
		getConfig().set(NODE_GRACE_PERIOD, TimeUnit.MILLISECONDS.toSeconds(arenaGracePeriodDuration));
	}

	public void setArenaCountdownDuration(long arenaCountdownDuration) {
		getConfig().set(NODE_COUNTDOWN, TimeUnit.MILLISECONDS.toSeconds(arenaCountdownDuration));
	}

	public void resetPlayer(Player player) {
		Location spawnPoint = getGlobalSpawnPoint();
		if (spawnPoint != null)
			player.teleport(spawnPoint);

		resetPlayerSession(player);
	}

	public void resetPlayerSession(Player player) {
		ArenaManager.removePlayerFromArena(player);
		ArenaEditManager.finishEditSession(player);
	}
}
