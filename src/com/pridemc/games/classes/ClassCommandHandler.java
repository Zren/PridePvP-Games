package com.pridemc.games.classes;

import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClassCommandHandler implements CommandExecutor {



	ClassArcher archer;

	ClassScout scout;

	ClassSoldier soldier;

	public ClassCommandHandler() {
		//TODO REMOVE
		archer = new ClassArcher();
		scout = new ClassScout();
		soldier = new ClassSoldier();
	}



	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		if (!ArenaManager.isInArena(player.getName()) || !ArenaManager.getArenaPlayerIsIn(player.getName()).getState().canChangeClass()) {
			MessageUtil.sendMsg(player, ChatColor.RED + "You can't use this command now!");
			return true;
		}

		if (PlayerClassManager.hasAClass(player.getName())) {
			MessageUtil.sendMsg(player, ChatColor.RED + "You have already selected a class!");
			return true;
		}

		if (args.length == 0) {
			sendListOfAvailableClasses(player);
		} else if (args.length > 0) { // If statement unnecessary
			String className = args[0];
			boolean selectedAClass = selectClass(player, className);
			if (!selectedAClass)
				sendListOfAvailableClasses(player);
		}
		return true;
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
