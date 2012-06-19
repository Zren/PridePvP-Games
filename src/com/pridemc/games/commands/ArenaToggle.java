package com.pridemc.games.commands;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/18/12
 */
public class ArenaToggle {
	public static boolean onCommand(CommandSender sender, String[] args) {
		Arena arena = null;

		if (args.length < 2) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				arena = ArenaManager.getArenaPlayerIsIn(player.getName());
			}

			if (arena == null) {
				// Wasn't a player in an arena.
				MessageUtil.sendMsg(sender, ChatColor.RED + "Please specify an arena.");
				return true;
			}
		} else {
			String arenaName = args[1];
			arena = ArenaManager.getArena(arenaName);
			if (arena == null) {
				String msg = ChatColor.RED + "There is no arena called '%s'.";
				MessageUtil.sendMsg(sender, msg, arenaName);
				return true;
			}
		}

		toggleArena(sender, arena);

		return true;
	}

	public static void toggleArena(CommandSender sender, Arena arena) {
		if (arena.getState() == Arena.State.CLOSED) {
			// Reset Arena
			arena.startTaskFor(Arena.State.WAITING_FOR_PLAYERS);

			// Msg
			String msg = "You opened the %s arena.";
			MessageUtil.sendMsg(sender, msg, arena.getName());
		} else {
			// Reset Arena
			arena = ArenaManager.resetArena(arena.getName());
			arena.startTaskFor(Arena.State.CLOSED);

			// Msg
			String msg = "You closed the %s arena.";
			MessageUtil.sendMsg(sender, msg, arena.getName());
		}
	}
}
