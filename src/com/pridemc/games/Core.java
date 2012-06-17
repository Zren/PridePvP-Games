package com.pridemc.games;

import ca.xshade.bukkit.util.TaskInjector;
import com.pridemc.games.arena.ArenaConfig;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.classes.ClassCommandHandler;
import com.pridemc.games.commands.ArenaCommandHandler;
import com.pridemc.games.commands.PlayerCommandHandler;
import com.pridemc.games.events.*;
import com.pridemc.games.pluginevents.JoinArena;
import com.pridemc.games.pluginevents.PortalCreation;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Core extends JavaPlugin {

	//HashMaps----------------------------------------------------------

	private Map<Player, String> editing = new HashMap<Player, String>();

	public Map<Player, String> getEditing() {
		return editing;
	}

	//---------------------------------------------------------------------

	public static YamlConfiguration arenas;
	public static YamlConfiguration config;
	public static File arenaConfigFile;

	public static Core instance;

	public Core() {
		instance = this;
		TaskInjector.newInstance(this);
	}

	public void onEnable() {
		arenaConfigFile = new File(getDataFolder(), "arenas.yml");

		// Config
		config = (YamlConfiguration) getConfig();
		arenas = YamlConfiguration.loadConfiguration(arenaConfigFile);
		ArenaConfig.loadArenaConfig();

		//getConfig().options().copyDefaults(true);

		saveConfig();
		saveArenaConfig();

		getLogger().info("has been enabled");

		// Register Commands
		getCommand("arena").setExecutor(new ArenaCommandHandler());
		getCommand("pg").setExecutor(new PlayerCommandHandler());
		getCommand("class").setExecutor(new ClassCommandHandler());

		// Register listeners
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);

		getServer().getPluginManager().registerEvents(new JoinArena(), this);
		getServer().getPluginManager().registerEvents(new PortalCreation(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new BlockPlace(), this);
		getServer().getPluginManager().registerEvents(new Explosions(), this);
		getServer().getPluginManager().registerEvents(new Join(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new Quit(), this);
		getServer().getPluginManager().registerEvents(new Teleportation(), this);
		getServer().getPluginManager().registerEvents(new PvP(), this);
		getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
		getServer().getPluginManager().registerEvents(new Drops(), this);
		getServer().getPluginManager().registerEvents(new Food(), this);
		getServer().getPluginManager().registerEvents(new PortalSignListener(), this);


	}

	public void onDisable() {
		// Cleanup
		TaskInjector.getInstance().cancelAll();
		ArenaManager.cleanupAllArenas();

		// Save
		saveConfig();
		saveArenaConfig();

		//
		getLogger().info("is disabled");

	}

	public static void saveArenaConfig() {
		try {
			arenas.save(arenaConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
