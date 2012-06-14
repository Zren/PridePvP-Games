package com.pridemc.games.classes;

import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
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



	public static void sendListOfAvailableClasses(Player player) {
		List<PlayerClass> availableClasses = PlayerClassManager.getPlayerClassesAvaiableToPlayer(player);
		if (availableClasses.isEmpty()) {
			MessageUtil.sendMsg(player, ChatColor.RED + "Uh oh! You don't have any classes available! Please contact an admin!");
		} else {
			List<String> classNames = new ArrayList<String>();
			for (PlayerClass playerClass : availableClasses) {
				classNames.add(playerClass.getName());
			}
			MessageUtil.sendMsg(player, "The following classes are available for you. Type " + ChatColor.GOLD + "/class <classname>" + ChatColor.YELLOW + " to select that class");
			MessageUtil.sendMsg(player, ChatColor.AQUA + "Classes: " + ChatColor.YELLOW + "%s", classNames);
		}
	}


	public static boolean selectClass(Player player, String className) {
		PlayerClass playerClass = PlayerClassManager.getPlayerClassByName(className);
		if (playerClass == null) {
			String msg = ChatColor.AQUA + "%s" + ChatColor.RED + " isn't a class!";
			MessageUtil.sendMsg(player, msg, className);
			return false;
		}

		if (!playerClass.canSelectAsClass(player)) {
			String msg = ChatColor.RED + "You don't have permission to select the " + ChatColor.AQUA + "%s" + ChatColor.RED + " class!";
			MessageUtil.sendMsg(player, msg, playerClass.getName());
			return false;
		}

		// Register the selected class so a player can't choose another.
		PlayerClassManager.registerPlayerClass(player.getName(), playerClass.getName());

		// Reset player's inventory and effects.
		PlayerClass.resetPlayer(player);

		// Equip the player with select equipment
		playerClass.equipPlayer(player);

		// Msg
		String msg = ChatColor.YELLOW + "You have selected the " + ChatColor.AQUA + "%s" + ChatColor.YELLOW + " class!";
		MessageUtil.sendMsg(player, msg, playerClass.getName());

		return true;
	}

}
