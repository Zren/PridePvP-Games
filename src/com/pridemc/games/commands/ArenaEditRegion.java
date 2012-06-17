package com.pridemc.games.commands;

import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaEditManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaEditRegion {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 1) {
			Player player = (Player)sender;

			if (ArenaEditManager.isEdidting(player)) {
				Arena arena = ArenaEditManager.getArenaPlayerIsEditing(player);
				if (arena == null)
					; // TODO

				if (args[1].equalsIgnoreCase("edit")) {
					ArenaEditManager.startEditRegionSession(player, arena);
				}
			} else {
				String msg = ChatColor.RED + "You are not currently editing an arena. If you wish to do so, type " + ChatColor.YELLOW + "/arena edit <name>";
				MessageUtil.sendMsg(sender, msg);
			}

		}

		return true;
	}


}
