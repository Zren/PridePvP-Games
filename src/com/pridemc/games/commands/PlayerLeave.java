package com.pridemc.games.commands;

import com.pridemc.games.arena.ArenaManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerLeave {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		ArenaManager.cleanUpPlayer(player);
		player.teleport(ArenaManager.getGlobalSpawnPoint());
		
		return true;
	}
}
