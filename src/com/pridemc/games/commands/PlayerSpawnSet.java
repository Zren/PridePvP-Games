package com.pridemc.games.commands;

import com.pridemc.games.Core;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerSpawnSet {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("pridegames.admin")) {
			Player player = (Player) sender;

			Core.config.set("Spawn location", player.getLocation().toVector());
			Core.config.set("Spawn world", player.getWorld().getName());
			Core.instance.saveConfig();

			MessageUtil.sendMsg(sender, "The PrideGames spawn has been set to your location");
		}
		return true;
	}
}
