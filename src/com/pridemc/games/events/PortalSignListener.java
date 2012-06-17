package com.pridemc.games.events;

import com.pridemc.games.Core;
import com.pridemc.games.arena.Arena;
import com.pridemc.games.arena.ArenaManager;
import com.pridemc.games.arena.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Author: Chris H (Zren / Shade)
 * Date: 6/15/12
 */
public class PortalSignListener implements Listener {
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();

		if (block.getType() == Material.WALL_SIGN) {
			if (player.hasPermission("pridegames.admin")) {
				org.bukkit.block.Sign sign = (org.bukkit.block.Sign)block.getState();
				if (event.getLine(0).equalsIgnoreCase("[PrideArena]")) {
					String arenaName = event.getLine(1);

					Arena arena = ArenaManager.getArena(arenaName);
					if (arena == null) {
						arena = new Arena(arenaName);
						ArenaManager.addArena(arena);
					}
					arena.setPortalBlockLocation(sign.getLocation());
					ArenaManager.updateArena(arena);

					Core.instance.getEditing().put(player, arenaName);

					String msg = "New arena " + ChatColor.AQUA + "%s" + ChatColor.YELLOW + " succesfully created! You are now editing this arena as well. To stop editting, type" + ChatColor.GOLD + " /arena edit";
					MessageUtil.sendMsg(player, msg, arenaName);
				}
			}
		}
	}
}
