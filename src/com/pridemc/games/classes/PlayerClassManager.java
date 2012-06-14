package com.pridemc.games.classes;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/14/12
 */
public class PlayerClassManager {
	private static PlayerClassManager instance = new PlayerClassManager();
	private List<PlayerClass> playerClasses = new ArrayList<PlayerClass>();
	private Map<String, String> playerToClassMap = new HashMap<String, String>();

	public PlayerClassManager() {
		playerClasses.add(new Archer());
		playerClasses.add(new Scout());
		playerClasses.add(new Soldier());
	}

	public static PlayerClassManager getInstance() {
		return instance;
	}

	public static String getPlayerClass(String playerName) {
		return getInstance().playerToClassMap.get(playerName);
	}

	public static String registerPlayerClass(String playerName, String className) {
		return getInstance().playerToClassMap.put(playerName, className);
	}

	public static String unregisterPlayerClass(String playerName) {
		return getInstance().playerToClassMap.remove(playerName);
	}

	public static boolean hasAClass(String playerName) {
		return getInstance().playerToClassMap.containsKey(playerName);
	}

	public static PlayerClass getPlayerClassByName(String className) {
		for (PlayerClass playerClass : getInstance().playerClasses) {
			if (playerClass.getName().equalsIgnoreCase(className)) {
				return playerClass;
			}
		}
		return null;
	}

	public static List<PlayerClass> getPlayerClassesAvaiableToPlayer(Player player) {
		List<PlayerClass> availableClasses = new ArrayList<PlayerClass>();
		for (PlayerClass playerClass : getInstance().playerClasses) {
			if (playerClass.canSelectAsClass(player)) {
				availableClasses.add(playerClass);
			}
		}
		return availableClasses;
	}

}
