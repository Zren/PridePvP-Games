package com.pridemc.games.commands;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaTp {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;

		if (args.length < 2) {
			MessageUtil.sendMsg(sender, ChatColor.RED + "Please specify an arena to teleport to!");
		} else {

			String arenaName = args[1];
			Arena arena = ArenaManager.getArena(arenaName);

			if (arena == null) {
				MessageUtil.sendMsg(sender, ChatColor.RED + "No arena with the name %s.");
			} else {
				if (arena.hasSpawnPoint()) {
					player.teleport(arena.getSpawnPoint());
				} else {
					MessageUtil.sendMsg(sender, ChatColor.RED + "This arena has no spawn point.");
				}
			}
		}

		return true;
	}
}
