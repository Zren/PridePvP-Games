package com.pridemc.games.commands;

import com.pridemc.games.arena.ArenaCore;
import com.pridemc.games.arena.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerLeave {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		ArenaManager.removePlayerFromArena(player);
		player.teleport(ArenaCore.getInstance().getGlobalSpawnPoint());
		
		return true;
	}
}
