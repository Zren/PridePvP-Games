package com.pridemc.games.commands;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ArenaRemoval implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 2) {
			String msg = ChatColor.RED + "Incorrect syntax. Correct usage: /arena remove <name>";
			MessageUtil.sendMsg(sender, msg);
		} else if (args.length == 2) {

			String arenaName = args[1];
			Arena arena = ArenaManager.getArena(arenaName);
			if (arena == null) {
				String msg = ChatColor.RED + "There is no arena called %s. Type /arena list to view a list of current arenas.";
				MessageUtil.sendMsg(sender, msg, arenaName);
			} else {
				ArenaManager.deleteArena(arena);

				String msg = "Arena %s has been succesfully removed.";
				MessageUtil.sendMsg(sender, msg, arenaName);
			}
		}
		return true;
	}
}
