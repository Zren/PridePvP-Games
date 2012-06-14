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
	List<PlayerClass> playerClasses = new ArrayList<PlayerClass>();


	ClassArcher archer;

	ClassScout scout;

	ClassSoldier soldier;

	public ClassCommandHandler() {
		archer = new ClassArcher();
		scout = new ClassScout();
		soldier = new ClassSoldier();

		playerClasses.add(new Archer());
	}

	List<String> classes = new ArrayList<String>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;

		if (ArenaManager.isInArena(player.getName()) && ArenaManager.getArenaPlayerIsIn(player.getName()).getState().canChangeClass()) {

			if (args.length > 0) {
				String className = args[0];
				boolean selectedAClass = selectClass(player, className);

				if (!selectedAClass) {
					// TODO Incorrect choice.
				}

			} else {

				classes.add("Archer");

				classes.add("Scout");

				classes.add("Soldier");

				if (sender.hasPermission("pg.class.heavy")) {

					classes.add("Heavy");

				}
				if (sender.hasPermission("pg.class.spy")) {

					classes.add("Spy");
				}

				if (!classes.isEmpty()) {

					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "Pride Games" + ChatColor.GOLD + "] " +
							ChatColor.YELLOW + "The following classes are available for you. Type " + ChatColor.GOLD + "/class <classname>" + ChatColor.YELLOW + " to select that class");

					sender.sendMessage(ChatColor.AQUA + "Classes: " + ChatColor.YELLOW + classes);

					classes.clear();

				} else {

					sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "Pride Games" + ChatColor.GOLD + "] " +
							ChatColor.YELLOW + ChatColor.RED + "Uh oh! You don't have any classes! Please contact an admin!");

				}
			}

		} else {

			sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "Pride Games" + ChatColor.GOLD + "] " +

					ChatColor.RED + "You can't use this command now!");
		}
		return true;
	}


	public boolean selectClass(Player player, String className) {
		for (PlayerClass playerClass : playerClasses) {
			if (playerClass.getName().equalsIgnoreCase(className)) {
				// Reset player's inventory and effects.
				PlayerClass.resetPlayer(player);

				// Equip the player with select equipment
				playerClass.equipPlayer(player);

				// Msg
				String msg = ChatColor.YELLOW + "You have selected the " + ChatColor.AQUA + "%s" + ChatColor.YELLOW + " class!";
				MessageUtil.sendMsg(player, msg, className);

				return true;
			}
		}
		return false;
	}
}
