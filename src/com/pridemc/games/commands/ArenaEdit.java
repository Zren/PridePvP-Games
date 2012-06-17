package com.pridemc.games.commands;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaEditManager;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaEdit {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;

		if (args.length == 1) {

			if (ArenaEditManager.isEdidting(player)) {
				Arena arena = ArenaEditManager.getArenaPlayerIsEditing(player);
				String arenaName = arena.getName(); // Finishing may reset Arena instance.

				ArenaEditManager.finishEditSession(player);

				// Msg
				String msg = "Finished editing arena %s.";
				MessageUtil.sendMsg(sender, msg, arenaName);
			} else {
				String msg = ChatColor.RED + "You are not currently editing an arena. If you wish to do so, type " + ChatColor.YELLOW + "/arena edit <name>";
				MessageUtil.sendMsg(sender, msg);
			}

		} else if (args.length == 2) {

			if (ArenaEditManager.isEdidting(player)) {
				String msg = ChatColor.RED + "You are already editing an arena! To quit editing this arena, type " + ChatColor.YELLOW + "/arena edit";
				MessageUtil.sendMsg(sender, msg);
			} else {
				String arenaName = args[1];
				Arena arena = ArenaManager.getArena(arenaName);

				if (arena == null) {
					String msg = ChatColor.RED + "The arena %s does not exist. Arena's are case sensitive.";
					MessageUtil.sendMsg(sender, msg, arenaName);
					return true;
				}

				ArenaEditManager.startEditSession(player, arena);

				String msg = "You are now editing the arena %s.";
				MessageUtil.sendMsg(sender, msg, arena.getName());
			}
		}

		return true;
	}
}
