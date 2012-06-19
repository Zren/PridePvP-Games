package com.pridemc.games;

import ca.xshade.bukkit.util.TaskInjector;
import com.pridemc.games.arena.ArenaCore;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.classes.ClassCommandHandler;
import com.pridemc.games.commands.ArenaCommandHandler;
import com.pridemc.games.commands.PlayerCommandHandler;
import com.pridemc.games.events.*;
import com.pridemc.games.events.custom.PlayerMoveListener;
import com.pridemc.games.events.PlayerMoveBlock;
import org.bukkit.plugin.java.JavaPlugin;


public class Core extends JavaPlugin {
	public static Core instance;

	public Core() {
		instance = this;
		TaskInjector.newInstance(this);
	}

	public void onEnable() {
		ArenaCore.setInstance(new ArenaCore(getDataFolder()));


		// Config
		ArenaCore.getInstance().load();
		ArenaCore.getInstance().save();

		getLogger().info("has been enabled");

		// Register Commands
		getCommand("arena").setExecutor(new ArenaCommandHandler());
		getCommand("pg").setExecutor(new PlayerCommandHandler());
		getCommand("class").setExecutor(new ClassCommandHandler());

		// Register listeners
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);

		getServer().getPluginManager().registerEvents(new PlayerMoveBlock(), this);
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new BlockPlace(), this);
		getServer().getPluginManager().registerEvents(new Explosions(), this);
		getServer().getPluginManager().registerEvents(new ResetPlayerListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
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
		ArenaCore.getInstance().save();

		//
		getLogger().info("is disabled");

	}
}
