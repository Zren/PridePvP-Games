package com.pridemc.games.commands;

import com.pridemc.games.arena.ArenaCore;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerShop {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		Location shopLocation = ArenaCore.getInstance().getShopLocation();

		if (shopLocation == null) {
			MessageUtil.sendMsg(sender, ChatColor.RED + "No shop has been set! Contact an admin about this issue.");
		} else {
			player.teleport(shopLocation);
		}

		return true;
	}
}
