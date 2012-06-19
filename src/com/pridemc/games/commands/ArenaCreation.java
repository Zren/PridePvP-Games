package com.pridemc.games.commands;

import com.pridemc.games.arena.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCreation implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;

			if (args.length < 2) {
				player.sendMessage(ChatColor.RED + "Incorrect syntax. Correct usage: /arena create <name>");
				String msg = ChatColor.RED + "Incorrect syntax. Correct usage: /arena create <name>";
				MessageUtil.sendMsg(sender, msg);
			} else if (args.length == 2) {

				String arenaName = args[1];

				Arena arena = ArenaManager.getArena(arenaName);

				if (arena == null) {
					// Arena doesn't exist already
					arena = new Arena(arenaName);
					ArenaManager.addArena(arena);
					ArenaEditManager.startEditSession(player, arena);

					String msg = "New arena %s succesfully created! You are now editing this arena as well. To stop editting, type" + ChatColor.GOLD + " /arena edit";
					MessageUtil.sendMsg(sender, msg, arena.getName());
				} else {
					String msg = ChatColor.RED + "There is already an arena called %1$s. If you'd like to remove this arena type /arena remove %1$s.";
					MessageUtil.sendMsg(sender, msg, arenaName);
				}
			}
		}
		return true;
	}
}
