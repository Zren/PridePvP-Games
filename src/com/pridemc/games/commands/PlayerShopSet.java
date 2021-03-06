package com.pridemc.games.commands;

import com.pridemc.games.arena.ArenaCore;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerShopSet {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("pridegames.admin")) {
			Player player = (Player) sender;

			ArenaCore.getInstance().setShopLocation(player.getLocation());
			MessageUtil.sendMsg(sender, "The PrideGames shop has been set to your location.");
		}
		
		return true;
	}
}
