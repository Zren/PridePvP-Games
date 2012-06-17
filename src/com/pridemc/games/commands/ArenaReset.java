package com.pridemc.games.commands;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ArenaReset {
	public static boolean onCommand(CommandSender sender, String[] args) {

		if (args.length < 2) {
			MessageUtil.sendMsg(sender, ChatColor.RED + "Please specify an arena.");
		} else {
			String arenaName = args[1];
			Arena arena = ArenaManager.getArena(arenaName);

			if (arena == null) {
				String msg = ChatColor.RED + "There is no arena called '%s'.";
				MessageUtil.sendMsg(sender, msg, arenaName);
			} else {
				arenaName = arena.getName();
				ArenaManager.resetArena(arenaName);
				String msg = "You reset the %s arena.";
				MessageUtil.sendMsg(sender, msg, arenaName);
			}
		}

		return true;
	}
}
