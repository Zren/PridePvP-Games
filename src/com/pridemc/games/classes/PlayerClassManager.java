package com.pridemc.games.classes;

import ca.xshade.bukkit.util.ChatUtil;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

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
		playerClasses.add(new Tank());
		playerClasses.add(new Grenadier());
		playerClasses.add(new Diver());
	}

	public static PlayerClassManager getInstance() {
		return instance;
	}

	public static PlayerClass getPlayerClass(String playerName) {
		return getPlayerClassByName(getPlayerClassName(playerName));
	}

	public static String getPlayerClassName(String playerName) {
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

	public static List<PlayerClass> getPlayerClassesByPermissible(Permissible permissible) {
		List<PlayerClass> availableClasses = new ArrayList<PlayerClass>();
		for (PlayerClass playerClass : getInstance().playerClasses) {
			if (playerClass.canSelectAsClass(permissible)) {
				availableClasses.add(playerClass);
			}
		}
		return availableClasses;
	}

	public static List<PlayerClass> getPlayerClassesByRequirement(PlayerClass.Requirement requirement) {
		List<PlayerClass> availableClasses = new ArrayList<PlayerClass>();
		for (PlayerClass playerClass : getInstance().playerClasses) {
			if (playerClass.getRequirement() == requirement) {
				availableClasses.add(playerClass);
			}
		}
		return availableClasses;
	}



	public static void sendListOfAvailableClasses(CommandSender sender) {
		List<PlayerClass> availableClasses = PlayerClassManager.getPlayerClassesByPermissible(sender);
		if (availableClasses.isEmpty()) {
			MessageUtil.sendMsg(sender, ChatColor.RED + "Uh oh! You don't have any classes available! Please contact an admin!");
		} else {
			List<String> classNames = new ArrayList<String>();
			for (PlayerClass playerClass : availableClasses) {
				classNames.add(playerClass.getName());
			}

			//
			for (PlayerClass.Requirement requirement : PlayerClass.Requirement.values()) {
				MessageUtil.sendMsgNoPrefix(sender, ChatUtil.formatTitle(requirement.getDescription()));
				for (PlayerClass playerClass : getPlayerClassesByRequirement(requirement)) {
					String msg = ChatUtil.formatCommand(
							"",
							"/class",
							playerClass.getName(),
							playerClass.getDescription());
					MessageUtil.sendMsgNoPrefix(sender, msg);
				}
			}

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

		// Msg
		String msg = ChatColor.YELLOW + "You have selected the " + ChatColor.AQUA + "%s" + ChatColor.YELLOW + " class!";
		MessageUtil.sendMsg(player, msg, playerClass.getName());

		return true;
	}

}
