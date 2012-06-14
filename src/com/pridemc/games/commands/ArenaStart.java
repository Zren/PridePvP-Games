package com.pridemc.games.commands;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaStart {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;

		if (args.length < 2) {
			MessageUtil.sendMsg(sender, ChatColor.RED + "Please specify an arena.");
		} else {
			String arenaName = args[1];

			Arena arena = ArenaManager.getArena(arenaName);

			if (arena == null) {
				String msg = ChatColor.RED + "There is no arena called '%s'.";
				MessageUtil.sendMsg(sender, msg, arenaName);
			} else {
				// Cleanup any currently running tasks
				arena.getTaskInjector().cancelAll();

				switch (arena.getState()) {
					case WAITING_FOR_PLAYERS:
					case COUNTING_DOWN:
						arena.startTaskFor(Arena.State.INITIAL_GRACE_PERIOD);
						break;

					case INITIAL_GRACE_PERIOD:
						arena.startTaskFor(Arena.State.RUNNING_GAME);
						break;
				}
			}
		}

		return true;
	}
}
